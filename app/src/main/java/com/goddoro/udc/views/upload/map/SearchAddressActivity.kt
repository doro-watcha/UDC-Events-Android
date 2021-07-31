package com.goddoro.udc.views.upload.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.hideKeyboard
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.Location
import com.goddoro.common.extension.addSchedulers
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.udc.databinding.ActivitySearchAddressBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchAddressActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = SearchAddressActivity::class.java.simpleName

    private lateinit var mapView: MapView

    private lateinit var naverMap : NaverMap

    private val toastUtil : ToastUtil by inject()

    var isFirstMove = false

    private lateinit var mBinding : ActivitySearchAddressBinding
    private val mViewModel : SearchAddressViewModel by viewModel()

    private val cameraChanged: BehaviorSubject<Pair<Double, Double>> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()

    private val queryChanged: BehaviorSubject<String> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchAddressBinding.inflate(LayoutInflater.from(this))

        mapView = mBinding.addressMap
        mapView.onCreate(savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        mapView.getMapAsync(this)

        observeViewModel()

        listenQueryChangeEvent()
        setContentView(mBinding.root)

        listenChangeCamera()

        debugE( TAG, "FUCK +" + intent?.getParcelableExtra(ARG_LOCATION) as Location?)






    }
    override fun onStart() {
        super.onStart()

        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()

        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()

        mapView.onPause()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        mapView.onSaveInstanceState(outState)

    override fun onStop() {
        super.onStop()

        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()

        mapView.onLowMemory()
    }
    private fun initMap( ) {

        naverMap.addOnCameraChangeListener { reason: Int, animated: Boolean ->
            val cameraPosition = naverMap.cameraPosition
            debugE(
                "NaverMap",
                "카메라 변경 - 대상 지점 위도: " + cameraPosition.target.latitude.toString() + ", " +
                        "대상 지점 경도: " + cameraPosition.target.longitude.toString() + ", " +
                        "줌 레벨: " + cameraPosition.zoom.toString() + ", " +
                        "기울임 각도: " + cameraPosition.tilt.toString() + ", " +
                        "베어링 각도: " + cameraPosition.bearing
            )
            if ( !isFirstMove) {
                isFirstMove = true
            } else {
                cameraChanged.onNext(
                    Pair(
                        cameraPosition.target.longitude,
                        cameraPosition.target.latitude
                    )
                )
            }


        }

    }

    private fun listenChangeCamera () {
        cameraChanged
            .debounce(100L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe {
                mViewModel.onCameraChange(it.first, it.second)
            }.disposedBy(compositeDisposable)

    }


    private fun observeViewModel() {

        mViewModel.apply {

            query.observe(this@SearchAddressActivity) {
                if (it.isNotEmpty()) queryChanged.onNext(it)

            }
            clickConfirm.observeOnce(this@SearchAddressActivity) {

                Broadcast.findAddressBroadcast.onNext(
                    Location(
                        currentAddress.value ?: "",
                        naverMap.cameraPosition.target.longitude,
                        naverMap.cameraPosition.target.latitude
                    )
                )

                finish()
            }

            clickAddress.observeOnce(this@SearchAddressActivity){
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(query_x, query_y))
                naverMap.moveCamera(cameraUpdate)
                query_x = 0.0
                query_y = 0.0
            }


            errorInvoked.observeOnce(this@SearchAddressActivity){
                debugE("NAVER",it.message)
            }


        }






    }



    override fun onMapReady(p0: NaverMap) {
        naverMap = p0

        this.naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)


        initMap()

        if ( (intent?.getParcelableExtra(ARG_LOCATION) as Location? )?.address != "") {
            val location : Location = intent?.getParcelableExtra(ARG_LOCATION) as Location
            mViewModel.currentAddress.value = location.address
            changeCamera(location.y, location.x)
        } else {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }

    }
    private fun listenQueryChangeEvent () {

        queryChanged
            .distinctUntilChanged()
            .debounce(300L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe {
                mViewModel.findLocation(it)
            }.disposedBy(compositeDisposable)
    }


    private fun changeCamera( x : Double, y : Double) {

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(x, y))
        naverMap.moveCamera(cameraUpdate)
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        const val ARG_LOCATION = "ARG_LOCATION"

        fun newIntent ( context : Context, location : Location) : Intent {
            val intent = Intent ( context, SearchAddressActivity::class.java)
            intent.putExtra(ARG_LOCATION, location)
            return intent
        }
    }
}