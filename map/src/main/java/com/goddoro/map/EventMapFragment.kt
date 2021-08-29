package com.goddoro.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goddoro.common.common.debugE
import com.goddoro.common.common.loadUrlAsync
import com.goddoro.common.data.model.Event
import com.goddoro.common.data.model.NaverItem
import com.goddoro.common.extension.addSchedulers
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.map.databinding.FragmentEventMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.tedpark.tedpermission.rx1.TedRxPermission
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ted.gun0912.clustering.naver.TedNaverClustering
import java.util.concurrent.TimeUnit


/**
 * created By DORO 2020/09/12
 */

class EventMapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = EventMapFragment::class.java.simpleName
    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentEventMapBinding


    /**
     * ViewModel Instance
     */

    private val mViewModel: EventMapViewModel by viewModel()

    lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private val toastUtil : ToastUtil by inject()

    private var mBound = false

    private val queryChanged: BehaviorSubject<String> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEventMapBinding.inflate(inflater, container, false).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
        listenQueryChangeEvent()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {

//        mBinding.mRecyclerView.apply {
//            adapter = EventSearchAdapter().apply {
//
//                clickEvent.subscribe{
//                    mViewModel.searchedAcademies.value = listOf()
//                    mViewModel.query.value = ""
//                    changeCamera(it.first)
//                    MapDetailDialog.show(requireActivity().supportFragmentManager, it.first )
//                }.disposedBy(compositeDisposable)
//            }
//        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)


        if (!hidden) {
            initSetting()
        }
    }

    private fun changeCamera ( event : Event) {

        if ( event.longitude != null && event.latitude != null ) {

            val cameraUpdate = CameraUpdate.scrollTo(LatLng(event.latitude ?: 0.0, event.longitude ?: 0.0))
                .animate(CameraAnimation.Easing)
            naverMap.moveCamera(cameraUpdate)


        }

    }



    private fun initSetting() {
        TedRxPermission.with(requireContext())
            .setDeniedMessage("위치 권한이 거부되었습니다")
            .setPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request()
            .subscribe({ result ->
                debugE(TAG, result.isGranted)
                if (result.isGranted && !mBound) {
                    mBinding.mapView.getMapAsync(this)
                }

            }, {
                toastUtil.createToast(it.message ?: "").show()
            })
    }

    private fun observeViewModel() {

        mViewModel.apply {

            query.observe(viewLifecycleOwner, Observer {
                if (it.isEmpty()) mViewModel.searchedAcademies.value = listOf()
                else queryChanged.onNext(it)
            })

            academies.observe(viewLifecycleOwner, {

                if (it.isNotEmpty()) {

                    val mapItems : List<NaverItem> = it.map{ academy ->
                        NaverItem(lat = academy.latitude, lng = academy.longitude, _academy = academy )
                    }

                    debugE(TAG, mapItems)

                    TedNaverClustering.with<NaverItem>(requireContext(), naverMap)
                        .items(mapItems)
                        .customCluster {
                            ImageView(requireActivity()).apply {
                                setImageResource(R.drawable.ic_udc_blue)
                            }

                        }
                        .customMarker { clusterItem ->
                            Marker(clusterItem.position).apply {
                                icon = OverlayImage.fromResource(R.drawable.ic_udc)
                            }
                        }
                        .clusterClickListener {

                        }
                        .markerClickListener {
                            //MapDetailDialog.show(requireActivity().supportFragmentManager, it.academy!!)
                        }
                        .make()

                    //mBinding.progress.visibility = View.GONE

                    toastUtil.createToast("${it.size}개의 학원이 있습니다").show()

                }
            })


            errorInvoked.observe(viewLifecycleOwner, Observer {
                debugE(TAG,it)
            })
        }
    }


    override fun onMapReady(naverMap: NaverMap) {
        mBound = true
        this.naverMap = naverMap
        naverMap.moveCamera(
            CameraUpdate.toCameraPosition(
                CameraPosition(
                    NaverMap.DEFAULT_CAMERA_POSITION.target,
                    NaverMap.DEFAULT_CAMERA_POSITION.zoom
                )
            )
        )

        this.naverMap.uiSettings.isLocationButtonEnabled = true

        naverMap.locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow



        mViewModel.listAcademies()

    }


    private fun listenQueryChangeEvent() {
        queryChanged
            .distinctUntilChanged()
            .debounce(200L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe {
                mViewModel.onQueryChanged(it)
            }.disposedBy(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }


    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        fun newInstance() = EventMapFragment()
    }

}