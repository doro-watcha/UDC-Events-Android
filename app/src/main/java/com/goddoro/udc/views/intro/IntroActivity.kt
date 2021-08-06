package com.goddoro.udc.views.intro

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.dialog.showTextDialog
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.extension.rxSingleTimer
import com.goddoro.common.util.AppPreference
import com.goddoro.udc.MainActivity
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivityIntroBinding
import com.goddoro.udc.util.startActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity() {

    private val TAG = IntroActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivityIntroBinding
    private val mViewModel : IntroViewModel by viewModel()

    private val appPreference : AppPreference by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityIntroBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        //observeViewModel()


        startActivity(MainActivity::class)
        finish()
    }

    private fun observeViewModel() {

        mViewModel.apply {
            onLoadCompleted.observeOnce(this@IntroActivity){

                val info = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                if ( it > info.versionCode){
                    startActivity(VersionUpdateActivity::class)
                }
                else {
                    startActivity(MainActivity::class)
                }

                finish()
            }
            onErrorInvoked.observe(this@IntroActivity){
                debugE(TAG, it.message)
                showTextDialog(
                    resources.getString(R.string.dialog_error_unknown),
                    "네트워크 및 서버가 불안정합니다")
            }


        }


    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        window.navigationBarColor = Color.parseColor("#000000")
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}