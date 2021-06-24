package com.goddoro.udc.views.upload.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.addSchedulers
import com.goddoro.common.extension.disposedBy
import com.goddoro.map.EventMapFragment
import com.goddoro.udc.databinding.ActivitySearchAddressBinding
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchAddressActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mapView: MapView

    private lateinit var naverMap : NaverMap

    private lateinit var mBinding : ActivitySearchAddressBinding
    private val mViewModel : SearchAddressViewModel by viewModel()

    private val cameraChanged: BehaviorSubject<Pair<Double, Double>> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchAddressBinding.inflate(LayoutInflater.from(this))

        mapView = mBinding.addressMap
        mapView.onCreate(savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        mapView.getMapAsync(this)

        observeViewModel()

        setContentView(mBinding.root)

        listenChangeCamera()



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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mapView.onSaveInstanceState(outState)
    }

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

            cameraChanged.onNext(Pair(cameraPosition.target.longitude, cameraPosition.target.latitude))


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

            clickConfirm.observeOnce(this@SearchAddressActivity){

                Broadcast.findAddressBroadcast.onNext(Triple(currentAddress.value ?: "", naverMap.cameraPosition.target.longitude, naverMap.cameraPosition.target.latitude))

                finish()
            }

            errorInvoked.observeOnce(this@SearchAddressActivity){
                debugE("NAVER",it.message)
            }
        }





    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        fun newIntent ( context : Context) : Intent {
            return Intent(context, SearchAddressActivity::class.java)
        }
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0

        this.naverMap.uiSettings.isLocationButtonEnabled = true

        naverMap.locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        initMap( )
    }
}