package com.goddoro.udc.views.classShop.detail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.goddoro.common.Broadcast
import com.goddoro.common.common.StrPatternChecker
import com.goddoro.common.common.StrPatternChecker.YoutubeUrlTypeOk
import com.goddoro.common.common.StrPatternChecker.extractVideoIdFromUrl
import com.goddoro.common.common.StrPatternChecker.getYoutubeIdFromUrl
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.DanceClass
import com.goddoro.common.extension.disposedBy
import com.goddoro.udc.databinding.ActivityClassDetailBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ClassDetailActivity : AppCompatActivity() {

    private val TAG = ClassDetailActivity::class.java.simpleName

    private lateinit var mBinding : ActivityClassDetailBinding

    private lateinit var mViewModel : ClassDetailViewModel

    private val ratingDisposable = CompositeDisposable()

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
        setupBroadcast()

        window.sharedElementEnterTransition = TransitionSet().apply {
            interpolator = OvershootInterpolator(0.7f)
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(ChangeBounds().apply{
                pathMotion = ArcMotion()
            })
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
        }
    }

    private fun initView() {
        debugE(TAG, mViewModel.danceClass.youtubeUrl)
        if ( YoutubeUrlTypeOk(mViewModel.danceClass.youtubeUrl ?: "")) {
            mBinding.youtubeView.play(
                extractVideoIdFromUrl(mViewModel.danceClass.youtubeUrl ?: "") ?: ""
            )
        }
    }

    private fun setupRecyclerView() {

//        mBinding.mRecyclerView.apply {
//
//
//            adapter = ArtistProfileAdapter()
//        }
    }
    private fun observeViewModel() {


        mViewModel.apply {


            clickInstagram.observeOnce(this@ClassDetailActivity){
                val instagram_intent = packageManager.getLaunchIntentForPackage("com.instagram.android")

                if ( instagram_intent != null) {
                    instagram_intent.data = Uri.parse(danceClass.artist.instagramUrl)
                    startActivity(instagram_intent)
                } else {
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

                    val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(danceClass.youtubeUrl));
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
            clickRatingButton.observeOnce(this@ClassDetailActivity){
                val dialog = RatingClassDialog(danceClass)
                dialog.show(supportFragmentManager, dialog.tag)
            }
        }
    }

    private fun setupBroadcast () {

        Broadcast.apply {

            starClassBroadcast.subscribe{
                mViewModel.star.value = it
            }.disposedBy(ratingDisposable)
        }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()


    }

    override fun onBackPressed() {
        super.onBackPressed()

        supportFinishAfterTransition()
    }

    override fun onDestroy() {
        super.onDestroy()

        ratingDisposable.dispose()
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