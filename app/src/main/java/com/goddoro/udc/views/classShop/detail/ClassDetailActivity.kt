package com.goddoro.udc.views.classShop.detail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.DanceClass
import com.goddoro.udc.databinding.ActivityClassDetailBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ClassDetailActivity : AppCompatActivity() {

    private val TAG = ClassDetailActivity::class.java.simpleName

    private lateinit var mBinding : ActivityClassDetailBinding

    private lateinit var mViewModel : ClassDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityClassDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel{
            parametersOf(intent?.getParcelableExtra(ARG_CLASS))
        }

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initView() {

        mBinding.youtubePlayer.play("mrP30UnUQjY")
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {


            adapter = ArtistProfileAdapter()
        }
    }
    private fun observeViewModel() {


        mViewModel.apply {


            clickInstagram.observeOnce(this@ClassDetailActivity){
                val insta_intent = packageManager.getLaunchIntentForPackage("com.instagram.android")

                if ( insta_intent != null) {

                    debugE(TAG, "Insta 설치 되어있지롱")

//                    insta_intent.component = ComponentName(
//                        "com.instagram.android",
//                        "com.instagram.android.activity.UrlHandlerActivity"
//                    )
                    insta_intent.data = Uri.parse("http://instagram.com/d__panic")

                    startActivity(insta_intent)
                } else {

                    debugE(TAG, "설치 안되있지롱")

                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=com.instagram.android")
                            )
                        )
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android")
                            )
                        )
                    }
                }
            }

            clickYoutube.observeOnce(this@ClassDetailActivity){
                val youtube_intent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")

                if ( youtube_intent != null ) {

                    val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=mrP30UnUQjY&feature=youtu.be"));
                    startActivity(browserIntent);
                }
                else {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=com.google.android.youtube")
                            )
                        )
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube")
                            )
                        )
                    }


                }
            }

            clickAskButton.observeOnce(this@ClassDetailActivity){
                val dialog = ClassAskBottomSheet()
                dialog.show(supportFragmentManager,dialog.tag)
            }

            clickBackArrow.observeOnce(this@ClassDetailActivity){
                finish()
            }
        }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()


    }

    override fun onBackPressed() {
        super.onBackPressed()

        supportFinishAfterTransition()
    }

    companion object {

        private const val ARG_CLASS = "ARG_CLASS"

        fun newIntent(activity: Activity, danceClass: DanceClass ) : Intent  {

            val intent = Intent(activity, ClassDetailActivity::class.java)
            intent.putExtra(ARG_CLASS, danceClass)
            return intent
        }
    }
}