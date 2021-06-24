package com.goddoro.udc.views.event.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.util.EventLog
import android.view.LayoutInflater
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.observeOnce
import com.goddoro.common.data.model.Event
import com.goddoro.udc.databinding.ActivityEventDetailBinding
import com.goddoro.udc.views.common.ImageDialog
import com.goddoro.udc.views.upload.UploadEventViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class EventDetailActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityEventDetailBinding

    private lateinit var  mViewModel : EventDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityEventDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel{ parametersOf( intent?.getParcelableExtra(ARG_EVENT)) }

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initView() {


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

    private fun setupRecyclerView() {

        mBinding.imgRecyclerView.apply {

            adapter = SketchImageAdapter()
        }
    }

    private fun observeViewModel() {


        mViewModel.apply {

            clickBackArrow.observeOnce(this@EventDetailActivity){
                onBackPressed()
            }

            clickPoster.observeOnce(this@EventDetailActivity){
                val dialog = ImageDialog(curEvent.value?.posterUrl ?: "")
                dialog.show(supportFragmentManager,dialog.tag)
            }

        }
    }


    companion object {
        private const val ARG_EVENT = "ARG_EVENT"

        fun newIntent (activity : Activity, event : Event) : Intent {

            val intent = Intent ( activity, EventDetailActivity::class.java )
            intent.putExtra(ARG_EVENT,event)

            return intent
        }

    }
}