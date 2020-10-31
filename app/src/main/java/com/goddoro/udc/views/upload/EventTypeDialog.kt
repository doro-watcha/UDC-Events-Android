package com.goddoro.udc.views.upload

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.CommonConst
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.databinding.DialogCommonBinding
import com.goddoro.udc.R
import com.goddoro.udc.databinding.DialogEventTypeBinding
import com.goddoro.udc.util.ScreenUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject


/**
 * created By DORO 2020/09/26
 */

class EventTypeDialog (  private val listener: onClickTypeListener): DialogFragment(){


    interface onClickTypeListener {
        fun onClickType( type : String)
    }

    private val TAG = EventTypeDialog::class.java.simpleName


    override fun getTheme(): Int = R.style.BottomSheetDialog

    private val compositeDisposable = CompositeDisposable()

    private var mBinding: DialogEventTypeBinding by AutoClearedValue()

    /**
     * Binding Instance
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogEventTypeBinding.inflate(inflater, container, false)

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

        mBinding.txtTypeBattle.setOnDebounceClickListener {
            listener.onClickType(CommonConst.eventType.Battle.name)
            dismiss()
        }
        mBinding.txtTypeParty.setOnDebounceClickListener {
            listener.onClickType(CommonConst.eventType.Party.name)
            dismiss()
        }

        mBinding.txtTypePerformance.setOnDebounceClickListener {
            listener.onClickType(CommonConst.eventType.Performance.name)
            dismiss()
        }

        mBinding.txtTypeWorkshop.setOnDebounceClickListener {
            listener.onClickType(CommonConst.eventType.Workshop.name)
            dismiss()
        }
    }

}