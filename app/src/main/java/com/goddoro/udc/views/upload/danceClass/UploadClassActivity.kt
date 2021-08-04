package com.goddoro.udc.views.upload.danceClass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goddoro.common.Broadcast
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.disposedBy
import com.goddoro.udc.databinding.ActivityUploadClassBinding
import com.goddoro.udc.views.upload.EventPreviewFragment
import com.goddoro.udc.views.upload.UploadEventFragment
import com.goddoro.udc.views.upload.academy.AcademyPickDialog
import com.goddoro.udc.views.upload.danceClass.genre.GenrePickDialog
import com.goddoro.udc.views.upload.danceClass.level.LevelPickDialog
import com.goddoro.udc.views.upload.danceClass.schedule.SchedulePickDialog
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class UploadClassActivity : AppCompatActivity() {

    private val TAG = UploadClassActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding: ActivityUploadClassBinding

    private val viewModel: UploadClassViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadClassBinding.inflate(LayoutInflater.from(this))

        binding.lifecycleOwner = this
        binding.vm = viewModel

        setContentView(binding.root)

        observeViewModel()
        initViewPager()
        setupBroadcast()
    }

    private fun initViewPager() {

        binding.viewPager.apply {

            adapter = UploadClassPager(supportFragmentManager, 3)

            isUserInputEnabled = false
        }
    }

    private fun observeViewModel() {

        viewModel.apply {

            clickBackArrow.observeOnce(this@UploadClassActivity) {

                if (binding.viewPager.currentItem == 0) {
                    finish()
                } else {
                    binding.viewPager.currentItem = binding.viewPager.currentItem - 1
                }
            }

            clickToImageStep.observeOnce(this@UploadClassActivity) {

                binding.viewPager.currentItem = 1
            }

            clickToSpecificStep.observeOnce(this@UploadClassActivity) {

                binding.viewPager.currentItem = 2
            }


            clickPickAcademy.observeOnce(this@UploadClassActivity) {
                val dialog = AcademyPickDialog()
                dialog.show(supportFragmentManager, null)
            }

            clickPickGenre.observeOnce(this@UploadClassActivity) {
                val dialog = GenrePickDialog()
                dialog.show(supportFragmentManager, null)
            }

            clickPickLevel.observeOnce(this@UploadClassActivity) {
                val dialog = LevelPickDialog()
                dialog.show(supportFragmentManager, null)
            }
            clickPickSchedule.observeOnce(this@UploadClassActivity){
                val dialog = SchedulePickDialog()
                dialog.show(supportFragmentManager,null)
            }
        }


    }

    private fun setupBroadcast() {

        Broadcast.apply {

            pickAcademyBroadcast.subscribe {
                viewModel.academy.value = it
            }.disposedBy(compositeDisposable)

            pickGenreBroadcast.subscribe {
                viewModel.genre.value = it
            }.disposedBy(compositeDisposable)

            pickLevelBroadcast.subscribe {
                viewModel.level.value = it
            }.disposedBy(compositeDisposable)

            pickScheduleBroadcast.subscribe{
                viewModel.schedule.value = it
            }.disposedBy(compositeDisposable)

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    inner class UploadClassPager(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {

                0 -> UploadClassBasicFragment.newInstance()
                1 -> UploadClassImageFragment.newInstance()
                else -> UploadClassSpecificFragment.newInstance()
            }
        }

    }

    override fun onBackPressed() {

        if (binding.viewPager.currentItem == 0) super.onBackPressed()
        else {
            binding.viewPager.currentItem = binding.viewPager.currentItem - 1
        }
    }

}