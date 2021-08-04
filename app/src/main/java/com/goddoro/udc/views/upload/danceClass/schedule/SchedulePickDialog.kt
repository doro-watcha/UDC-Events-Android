package com.goddoro.udc.views.upload.danceClass.schedule

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.Broadcast
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.ToastUtil
import com.goddoro.map.R
import com.goddoro.udc.databinding.DialogGenrePickBinding
import com.goddoro.udc.databinding.DialogSchedulePickBinding
import com.goddoro.udc.views.upload.danceClass.genre.GenreListAdapter
import com.goddoro.udc.views.upload.danceClass.genre.GenrePickViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class SchedulePickDialog  : DialogFragment(){

    private val TAG = SchedulePickDialog::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()


    private var mBinding : DialogSchedulePickBinding by AutoClearedValue()
    private val viewModel : SchedulePickViewModel by viewModel()

    private val toastUtil : ToastUtil by inject()

    override fun getTheme(): Int = R.style.BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return DialogSchedulePickBinding.inflate(inflater, container, false).run {
            mBinding = this
            this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = viewModel

        observeViewModel()


    }



    private fun observeViewModel() {

        viewModel.apply {

            clickPickSchedule.observeOnce(this@SchedulePickDialog){
                var schedule : String = "매주 "

                if ( isMondayClicked.value != null) {
                    schedule += "월 "
                }
                if ( isTuesdayClicked.value != null) {
                    schedule += "화 "
                }
                if ( isWednesdayClicked.value != null) {
                    schedule += "수 "
                }
                if ( isThursdayClicked.value != null) {
                    schedule += "목 "
                }
                if ( isFridayClicked.value != null) {
                    schedule += "금 "
                }
                if ( isSaturdayClicked.value != null) {
                    schedule += "토 "
                }
                if ( isSundayClicked.value != null) {
                    schedule += "일 "
                }
                val scheduleTime = (if ( mBinding.timePicker.hour < 12 ) "AM " else "PM ") + mBinding.timePicker.hour.toString() + "시 " + mBinding.timePicker.minute.toString() + "분"
                schedule += scheduleTime

                Broadcast.pickScheduleBroadcast.onNext(schedule)

                dismiss()
            }
        }
    }

    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val width = (point.x * 0.8f).roundToInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    companion object {
        fun show(fm: FragmentManager) {
            val dialog = SchedulePickDialog()
            dialog.show(fm, dialog.tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

}