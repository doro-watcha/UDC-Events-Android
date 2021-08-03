package com.goddoro.udc.views.upload.danceClass.level

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import com.goddoro.common.Broadcast
import com.goddoro.common.CommonConst
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.udc.R
import com.goddoro.udc.databinding.DialogEventTypeBinding
import com.goddoro.udc.databinding.DialogLevelPickBinding
import com.goddoro.udc.views.upload.EventTypeDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable


class LevelPickDialog (  ): BottomSheetDialogFragment(){




    private val TAG = LevelPickDialog::class.java.simpleName


    override fun getTheme(): Int = R.style.BottomSheetDialog

    private val compositeDisposable = CompositeDisposable()

    private var mBinding: DialogLevelPickBinding by AutoClearedValue()

    /**
     * Binding Instance
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogLevelPickBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.statusBarColor =  (ContextCompat.getColor(requireActivity(), R.color.black))

        }


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.dialog = this



        initView()

    }

    private fun initView() {



    }

    fun onClickLevel( level : Int ) {

        val classLevel = when ( level) {
            1 -> "초보"
            2 -> "중급"
            3 -> "고급"
            else -> throw Error()
        }

        Broadcast.pickLevelBroadcast.onNext(classLevel)
        dismiss()
    }

}