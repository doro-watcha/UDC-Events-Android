package com.goddoro.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.NaverItem
import com.goddoro.map.databinding.FragmentEventMapBinding
import com.goddoro.map.dialog.MapDetailDialog
import com.naver.maps.map.*
import dagger.android.support.DaggerFragment
import ted.gun0912.clustering.naver.TedNaverClustering
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.goddoro.common.util.ToastUtil
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.tedpark.tedpermission.rx1.TedRxPermission
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ted.gun0912.clustering.clustering.Cluster


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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentEventMapBinding.inflate(inflater, container, false).also { mBinding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initSetting()
        observeViewModel()

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
                if (result.isGranted) {
                    mBinding.mapView.getMapAsync(this)
                }

            }, {
                toastUtil.createToast(it.message ?: "")?.show()
            })

//        mBinding.mapView.getMapAsync(this)

    }

    private fun observeViewModel() {

        mViewModel.apply {

            events.observe(viewLifecycleOwner,Observer {

                if (it.isNotEmpty()) {

                    val mapItems : List<NaverItem> = it.map{ event ->
                        NaverItem(lat = event.latitude ?: 0.0, lng = event.longitude ?: 0.0)
                    }

                    debugE(TAG, mapItems)

                    TedNaverClustering.with<NaverItem>(requireContext(), naverMap)
                        .items(getItems())
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
                            MapDetailDialog.show(requireActivity().supportFragmentManager, it)
                        }
                        .make()


                }
            })


            errorInvoked.observe(viewLifecycleOwner, Observer {
                debugE(TAG,it)
            })
        }
    }


    override fun onMapReady(naverMap: NaverMap) {
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



        mViewModel.listEvents()

    }

    private fun getItems(): List<NaverItem> {
        val bounds = naverMap.contentBounds
        return ArrayList<NaverItem>().apply {
            repeat(50) {
                val temp = NaverItem(
                    (bounds.northLatitude - bounds.southLatitude) * Math.random() + bounds.southLatitude,
                    (bounds.eastLongitude - bounds.westLongitude) * Math.random() + bounds.westLongitude
                )
                add(temp)
            }
        }

    }



    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

        fun newInstance() = EventMapFragment()
    }

}