package com.goddoro.udc.views.classShop.detail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.goddoro.common.Broadcast
import com.goddoro.common.common.StrPatternChecker.YoutubeUrlTypeOk
import com.goddoro.common.common.StrPatternChecker.extractVideoIdFromUrl
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.udc.databinding.ActivityClassDetailBinding
import com.goddoro.udc.util.startActivity
import com.goddoro.udc.views.auth.LoginActivity
import com.goddoro.udc.views.event.detail.SketchImageAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class ClassDetailActivity : AppCompatActivity() {

    private val TAG = ClassDetailActivity::class.java.simpleName

    private lateinit var mBinding: ActivityClassDetailBinding

    private lateinit var mViewModel: ClassDetailViewModel

    private val ratingDisposable = CompositeDisposable()

    private val toastUtil : ToastUtil by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityClassDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel {
            parametersOf(intent?.getIntExtra(ARG_CLASS_ID, 0))
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
            addTransition(ChangeBounds().apply {
                pathMotion = ArcMotion()
            })
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
        }
    }

    private fun initView() {

    }

    private fun setupRecyclerView() {

        mBinding.recyclerViewSketchImages.apply {


            adapter = SketchImageAdapter()
        }
    }

    private fun observeViewModel() {


        mViewModel.apply {

            onLoadCompleted.observe(this@ClassDetailActivity) {
                if (it == true) {
                    val url = mViewModel.danceClass.value?.youtubeUrl ?: ""
                    if (YoutubeUrlTypeOk(url)) {
                        debugE(TAG, url)
                        debugE(TAG, extractVideoIdFromUrl(url))
                        mBinding.youtubeView.play(
                            extractVideoIdFromUrl(url) ?: ""
                        )
                    } else {
                        mBinding.txtYoutubeView.visibility = View.GONE
                        mBinding.youtubeView.visibility = View.GONE
                    }
                }
            }

            clickInstagram.observeOnce(this@ClassDetailActivity) {
                val instagram_intent =
                    packageManager.getLaunchIntentForPackage("com.instagram.android")

                if (instagram_intent != null) {

                    val uri = Uri.parse("https://instagram.com/_u/" + danceClass.value?.artist?.instagramUrl)
                    val likeIng = Intent(Intent.ACTION_VIEW, uri)

                    likeIng.setPackage("com.instagram.android")
                    //instagram_intent.data = Uri.parse(danceClass.value?.artist?.instagramUrl)
                    startActivity(likeIng)
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

            clickYoutube.observeOnce(this@ClassDetailActivity) {
                val youtube_intent =
                    packageManager.getLaunchIntentForPackage("com.google.android.youtube")

                if (youtube_intent != null) {

                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(danceClass.value?.youtubeUrl));
                    startActivity(browserIntent);
                } else {
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

            clickAverageButton.observeOnce(this@ClassDetailActivity){
                val number = danceClass.value?.ratingCount
                toastUtil.createToast("총 ${number}명의 수강생들의 별점입니다").show()
            }

            clickAskButton.observeOnce(this@ClassDetailActivity) {
                val dialog = ClassAskBottomSheet()
                dialog.show(supportFragmentManager, dialog.tag)
            }

            clickBackArrow.observeOnce(this@ClassDetailActivity) {
                finish()
            }
            clickRatingButton.observeOnce(this@ClassDetailActivity) {
                val dialog = RatingClassDialog(danceClass.value!!)
                dialog.show(supportFragmentManager, dialog.tag)
            }

            needLogin.observeOnce(this@ClassDetailActivity) {
                startActivity(LoginActivity::class)
            }
        }
    }

    private fun setupBroadcast() {

        Broadcast.apply {

            starClassBroadcast.subscribe({
                mViewModel.star.value = it
            }, {

            }).disposedBy(ratingDisposable)

            starDeleteBroadcast.subscribe({
                mViewModel.star.value = null
            }, {
            }).disposedBy(ratingDisposable)

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

        private const val ARG_CLASS_ID = "ARG_CLASS_ID"

        fun newIntent(activity: Activity, classId: Int): Intent {

            val intent = Intent(activity, ClassDetailActivity::class.java)
            intent.putExtra(ARG_CLASS_ID, classId)
            return intent
        }
    }
}