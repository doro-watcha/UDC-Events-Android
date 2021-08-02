package com.goddoro.udc.views.upload.academy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.addSchedulers
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivityUploadAcademyBinding
import com.goddoro.udc.views.upload.map.SearchAddressActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.tedpark.tedpermission.rx1.TedRxPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class UploadAcademyActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = UploadAcademyActivity::class.java.simpleName

    private lateinit var mapView: MapView

    private lateinit var naverMap : NaverMap

    private val toastUtil : ToastUtil by inject()
    private var mBound = false

    private lateinit var binding : ActivityUploadAcademyBinding
    private val viewModel : UploadAcademyViewModel by viewModel()

    private val cameraChanged: BehaviorSubject<Pair<Double, Double>> = BehaviorSubject.create()
    private val queryChanged: BehaviorSubject<String> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadAcademyBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        initSetting()
        observeViewModel()
        listenChangeCamera()
        listenQueryChanged()
    }
    private fun initSetting() {
        TedRxPermission.with(this)
            .setDeniedMessage("위치 권한이 거부되었습니다")
            .setPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request()
            .subscribe({ result ->
                debugE(TAG, result.isGranted)
                if (result.isGranted && !mBound) {
                    binding.mapView.getMapAsync(this)
                }

            }, {
                toastUtil.createToast(it.message ?: "").show()
            })
    }

    private fun observeViewModel() {

        viewModel.apply {

            location.observe(this@UploadAcademyActivity){
                if ( it != null) {
                    queryChanged.onNext(it)
                }
            }

            isValidAddress.observe(this@UploadAcademyActivity){
                if ( it == true ) {
                    //binding.editLocation.setSelection(binding.editLocation.length())
                    changeCamera(latitude,longitude)
                }
            }
            onRegisterComplete.observeOnce(this@UploadAcademyActivity){
                toastUtil.createToast("${name.value} 등록을 완료하였습니다").show()
                finish()
            }

            clickGalleryButton.observeOnce(this@UploadAcademyActivity){
                TedImagePicker.with(this@UploadAcademyActivity)
                    .title(resources.getString(R.string.txt_pick_image))
                    .showCameraTile(false)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .start {
                        logoImage.value = it
                    }

            }
        }
    }

    private fun listenChangeCamera () {
//        cameraChanged
//            .debounce(100L, TimeUnit.MILLISECONDS)
//            .addSchedulers()
//            .subscribe {
//                viewModel.onCameraChange(it.first, it.second)
//            }.disposedBy(compositeDisposable)

    }

    private fun listenQueryChanged () {

        queryChanged
            .distinctUntilChanged()
            .debounce(1000L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe {
                viewModel.findLocation(it)
            }.disposedBy(compositeDisposable)
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0


        this.naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    private fun changeCamera( x : Double, y : Double) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(x, y))
        naverMap.moveCamera(cameraUpdate)
    }

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1009
    }
}