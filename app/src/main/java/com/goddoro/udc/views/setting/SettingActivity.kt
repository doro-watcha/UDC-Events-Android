package com.goddoro.udc.views.setting

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.util.Navigator
import com.goddoro.udc.databinding.ActivitySettingBinding
import dagger.android.AndroidInjection.inject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivitySettingBinding
    private val mViewModel : SettingViewModel by viewModel()

    private val authRepository : AuthRepository by inject()
    private val navigator : Navigator by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySettingBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        observeViewModel()

    }

    private fun initView() {


        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        mBinding.txtVersion.text = info.versionName

    }

    private fun observeViewModel() {
        mViewModel.apply {


            clickLogOut.observeOnce(this@SettingActivity){
                authRepository.signOut()
                navigator.startMainActivity(this@SettingActivity, true)
            }

            clickTagDetailDialog.observeOnce(this@SettingActivity){
                navigator.startTagDetailActivity(this@SettingActivity,it)
            }
        }

    }


}