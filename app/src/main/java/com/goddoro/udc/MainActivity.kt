package com.goddoro.udc

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.tv.TvContract.Programs.Genres.encode
import android.os.Build
import android.os.Bundle
import android.util.Base64.encode
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.navigation.MainMenu
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.dialog.CommonSingleDialog
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.AppPreference
import com.goddoro.common.util.Navigator
import com.goddoro.common.util.ToastUtil
import com.goddoro.map.EventMapFragment
import com.goddoro.udc.databinding.ActivityMainBinding
import com.goddoro.udc.views.PopupDialog
import com.goddoro.udc.views.classShop.ClassShopFragment
import com.goddoro.udc.views.event.EventFragment
import com.goddoro.udc.views.profile.ProfileFragment
import com.goddoro.udc.views.upload.UploadCompleteDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val eventUploadDisposable = CompositeDisposable()

    private lateinit var mBinding: ActivityMainBinding

    private val appPreference: AppPreference by inject()

    private val navigator: Navigator by inject()

    private val toastUtil: ToastUtil by inject()

    private val authRepository: AuthRepository by inject()
    private val mViewModel: MainViewModel by viewModel()

    private val fragment1 = ClassShopFragment.newInstance()
    private val fragment2 = EventMapFragment.newInstance()
    private val fragment3 = EventFragment.newInstance()
    private val fragment4 = ProfileFragment.newInstance(authRepository.curUser.value?.id ?: 0)
    private var curFragment: Fragment = fragment1


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel



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
                            mViewModel.registerDevice(newFCMToken)
                        } else {
                            appPreference.curFCMToken = newFCMToken
                            debugE(TAG, "Main IN device2")

                            debugE(TAG, newFCMToken)
                            mViewModel.registerDevice(newFCMToken)
                        }
                    }
                })
        }

        initFragments(savedInstanceState == null)
        setupBottomNavigationView()
        setupBroadcast()
        showPopup()
    }

    private fun setupBottomNavigationView() {

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {

            val selectedMenu = MainMenu.parseIdToMainMenu(it.itemId)

            when (selectedMenu) {
                MainMenu.CLASS -> {
                    changeFragment(selectedMenu)
                }
                MainMenu.MAP -> {
                    changeFragment(selectedMenu)
                }
                MainMenu.EVENT -> {
                    changeFragment(selectedMenu)
                }
                MainMenu.PROFILE -> {
                    if (authRepository.isSignedIn()) {
                        changeFragment(selectedMenu)
                    } else {
                        navigator.startLoginActivity(this)
                        return@setOnNavigationItemSelectedListener false
                    }
                }

            }
            true
        }
    }

    private fun initFragments(isFirstCreation: Boolean) {

        if (isFirstCreation) {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment4, "4")
                .hide(fragment4).commit()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment3, "3")
                .hide(fragment3).commit()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment2, "2")
                .hide(fragment2).commit()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment1, "1").show(fragment1)
                .commit()
        }
    }


    private fun changeFragment(menu: MainMenu) {

        val willShow = when (menu) {
            MainMenu.CLASS -> fragment1
            MainMenu.MAP -> fragment2
            MainMenu.EVENT -> fragment3
            MainMenu.PROFILE -> fragment4
        }

        Broadcast.bottomIndexChangeBroadcast.onNext(menu.idx)

        supportFragmentManager.beginTransaction()
            .hide(fragment1)
            .hide(fragment2)
            .hide(fragment3)
            .hide(fragment4)
            .show(willShow)
            .commit()
        curFragment = willShow
    }


    private fun setupBroadcast() {

        Broadcast.eventUploadBroadcast.subscribe({
            val dialog =
                UploadCompleteDialog.newInstance(authRepository.curUser.value?.username ?: "", it)
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(dialog, dialog.tag).commitAllowingStateLoss()
        }, {
            debugE(TAG, it)
        }).disposedBy(eventUploadDisposable)

        Broadcast.eventUploadBroadcast.subscribe {
            CommonSingleDialog(R.drawable.ic_camera, R.string.dialog_upload_completed)
        }.disposedBy(eventUploadDisposable)
    }

    private fun showPopup() {

        val dt = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.format(dt).toString()
        if (date != appPreference.popUpDate) {
            val dialog = PopupDialog()
            dialog.show(supportFragmentManager, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        eventUploadDisposable.dispose()
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        window.navigationBarColor = Color.parseColor("#000000")
    }



}