package com.goddoro.udc.views.upload.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.util.ScreenUtil
import com.goddoro.udc.R
import com.goddoro.udc.databinding.DialogCalendarBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*
import kotlin.math.roundToInt


/**
 * created By DORO 11/28/20
 */


class CalendarDialog (  private val listener: onClickDateListener): DialogFragment(){


    private val screenUtil : ScreenUtil by inject()

    interface onClickDateListener {
        fun onClickDate( date : String)
    }

    private val TAG = CalendarDialog::class.java.simpleName


    override fun getTheme(): Int = R.style.BottomSheetDialog

    private val compositeDisposable = CompositeDisposable()

    private var mBinding: DialogCalendarBinding by AutoClearedValue()

    /**
     * Binding Instance
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogCalendarBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
    }

    private fun initView() {
        mBinding.apply {

            btnConfirm.setOnDebounceClickListener {

                val eventTime = (if ( timePicker.hour < 12 ) "오전 " else "오후 ") + if ( timePicker.hour <= 12 ) timePicker.hour.toString() else (timePicker.hour-12).toString() + "시 " + timePicker.minute.toString() + "분"
                val eventDate = datePicker.year.toString() + "년 " + datePicker.month.toString() + "월 " + datePicker.dayOfMonth.toString() + "일 "

                listener.onClickDate(eventDate + eventTime)
                dismiss()

            }


        }


    }


    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}