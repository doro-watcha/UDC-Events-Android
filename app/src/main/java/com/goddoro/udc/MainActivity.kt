package com.goddoro.udc

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.navigation.MainMenu
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.dialog.CommonSingleDialog
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.AppPreference
import com.goddoro.common.util.Navigator
import com.goddoro.common.util.ToastUtil
import com.goddoro.map.EventMapFragment
import com.goddoro.udc.databinding.ActivityMainBinding
import com.goddoro.udc.views.classShop.ClassShopFragment
import com.goddoro.udc.views.home.HomeFragment
import com.goddoro.udc.views.profile.ProfileFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.util.FusedLocationSource
import com.tedpark.tedpermission.rx1.TedRxPermission
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val eventUploadDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivityMainBinding

    private val appPreference : AppPreference by inject()

    private val navigator : Navigator by inject()

    private val toastUtil : ToastUtil by inject()

    private val authRepository : AuthRepository by inject()
    private val mViewModel : MainViewModel by viewModel()

    private lateinit var fragment1 : HomeFragment
    private lateinit var fragment2 : EventMapFragment
    private lateinit var fragment3 : ClassShopFragment
    private lateinit var fragment4 : ProfileFragment
    private lateinit var curFragment: Fragment

    private lateinit var locationSource: FusedLocationSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setupBroadcast()

        setContentView(mBinding.root)




        if (authRepository.isSignedIn()) {
            // FCM token check
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val newFCMToken = task.result?.token
                    val savedFCMToken = appPreference.curFCMToken
                    if (newFCMToken != null) {
                        var uuid = appPreference.curDeviceUUID
                        if (uuid.isEmpty()) {
                            uuid = UUID.randomUUID().toString() // randon UUID 생성
                        }

                        if (savedFCMToken.isNotEmpty() && newFCMToken != savedFCMToken) {
                            // fcm token이 변경되었으면 preference update하고 서버도 update한다.
                            appPreference.curFCMToken = newFCMToken
                            debugE(TAG, "Main IN devide")
                            mViewModel.registerDevice( newFCMToken)
                        } else {
                            appPreference.curFCMToken = newFCMToken
                            debugE(TAG, "Main IN device2")

                            debugE(TAG, newFCMToken)
                            mViewModel.registerDevice(newFCMToken)
                        }
                    }
                })
        }



        initView()

        initFragments(savedInstanceState == null)
        observeViewModel()
        setupBottomNavigationView()
    }

    private fun initView() {

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            _menu.value = MainMenu.parseIdToMainMenu(it.itemId)
            true
        }
    }

    private fun setupBottomNavigationView() {

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {

            val selectedMenu = MainMenu.parseIdToMainMenu(it.itemId)

//            if (selectedMenu != MainMenu.UPLOAD)
//                changeBottomIcon(it.itemId)

            when (selectedMenu) {
                MainMenu.HOME -> {
                    debugE(TAG, "Home Tab Selected")
                    changeFragment(selectedMenu)
                }
                MainMenu.EVENT -> {

                    changeFragment(selectedMenu)
                }
                MainMenu.CLASS -> {
                    changeFragment(selectedMenu)
                }
                MainMenu.PROFILE -> {
                    if (authRepository.isSignedIn()) {
                        changeFragment(selectedMenu)
                    }
                    else {
                        navigator.startNeedLoginActivity(this)
                        return@setOnNavigationItemSelectedListener false
                    }
                }

            }
//
//            navigator.curMainMenu.value = selectedMenu

            true
        }

        mBinding.bottomNavigation.setOnNavigationItemReselectedListener {

            // 이미 선택되어있는 탭을 다시 선택하면 목록 제일 위로 이동시킨다.
            // Broadcast 날림
            when (MainMenu.parseIdToMainMenu(it.itemId)) {

//                MainMenu.FEED -> {
//                    debugE(TAG, "Video Tab ReSelected")
//                    Broadcast.videoListGoTop.onNext(Unit)
//                }
//                MainMenu.EXPLORE -> {
//                    debugE(TAG, "Explore Tab Reselected")
//                    Broadcast.exploreListGoTop.onNext(Unit)
//                }
//                MainMenu.UPLOAD -> {
//
//                }
//                MainMenu.NOTIFICATION -> {
//                    clearBadge()
//                    Broadcast.notificationGoTopBroadcast.onNext(Unit)
//                }
                MainMenu.PROFILE -> {
                    Broadcast.profileGoTopBroadcast.onNext(Unit)


                }
            }

            true


        }

    }

    private fun initFragments(isFirstCreation : Boolean) {

        fragment1 = supportFragmentManager.findFragmentByTag("0") as? HomeFragment ?: HomeFragment.newInstance()
        fragment2 = supportFragmentManager.findFragmentByTag("1") as? EventMapFragment ?: EventMapFragment.newInstance()
        fragment3 = supportFragmentManager.findFragmentByTag("2") as? ClassShopFragment ?: ClassShopFragment.newInstance()
        fragment4 = supportFragmentManager.findFragmentByTag("3") as? ProfileFragment ?: ProfileFragment.newInstance(authRepository.curUser.value?.id ?: -1)
        curFragment = when(menu.value) {
            MainMenu.HOME->fragment1
            MainMenu.EVENT->fragment2
            MainMenu.CLASS->fragment3
            MainMenu.PROFILE->fragment4
            else -> throw IllegalStateException()
        }

        if(isFirstCreation) {
            val fm = supportFragmentManager
            fm.beginTransaction().add(R.id.fragmentContainer, fragment4, "0").hide(fragment4).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment3, "1").hide(fragment3).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment2, "2").hide(fragment2).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment1, "3").commit()
        }
    }


    private fun changeFragment(menu : MainMenu) {

        val willShow = when (menu) {
            MainMenu.HOME -> fragment1
            MainMenu.EVENT -> fragment2
            MainMenu.CLASS -> fragment3
            MainMenu.PROFILE -> fragment4
        }

        Broadcast.bottomIndexChangeBroadcast.onNext(menu.idx)


        supportFragmentManager.beginTransaction().hide(curFragment).show(willShow).commit()
        curFragment = willShow
    }


    private fun observeViewModel() {

        menu.observe(this@MainActivity) { menu->
            changeFragment(menu)

            if(mBinding.bottomNavigation.selectedItemId != menu.menuId)
                mBinding.bottomNavigation.selectedItemId = menu.menuId
        }
        

        Broadcast.eventUploadBroadcast.subscribe{
            CommonSingleDialog(R.drawable.ic_camera, R.string.dialog_upload_completed)
        }.disposedBy(eventUploadDisposable)
    }

    private fun setupBroadcast() {

        Broadcast.eventUploadBroadcast.subscribe({
            debugE(TAG, "Upload Complete!")
            //showUploadCompleteDialog(authRepository.curUser.value?.username ?: "유저")
            toastUtil.createToast("행사를 업로드하였습니다")?.show()
        },{
            debugE(TAG,it)
        }).disposedBy(eventUploadDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()

        eventUploadDisposable.clear()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        window.navigationBarColor = Color.parseColor("#000000")
    }

    companion object {


        private val _menu : MutableLiveData<MainMenu> = MutableLiveData(MainMenu.HOME)
        val menu : LiveData<MainMenu> = _menu
    }

}