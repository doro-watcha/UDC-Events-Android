package com.goddoro.common.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.databinding.DialogTextDoubleBinding
import com.goddoro.common.util.ScreenUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


fun AppCompatActivity.showTextDoubleDialog(
    titleResource: Int,
    bodyResource: Int,
    onPositive: (() -> Unit),
    onNegative: (() -> Unit)
) {
    TextDoubleDialog.show(
        supportFragmentManager, titleResource, bodyResource, onPositive, onNegative
    )
}

fun Fragment.showTextDoubleDialog(
    titleResource: Int,
    bodyResource: Int,
    onPositive: () -> Unit,
    onNegative: () -> Unit
) {
    TextDoubleDialog.show(
        childFragmentManager, titleResource, bodyResource, onPositive, onNegative
    )
}

fun DialogFragment.showTextDoubleDialog(
    titleResource: Int,
    bodyResource: Int,
    onPositive: () -> Unit,
    onNegative: () -> Unit
) {
    TextDoubleDialog.show(
        childFragmentManager, titleResource, bodyResource, onPositive, onNegative
    )
}

fun BottomSheetDialogFragment.showTextDoubleDialog(
    titleResource: Int,
    bodyResource: Int,
    onPositive: () -> Unit,
    onNegative: () -> Unit
) {
    TextDoubleDialog.show(
        childFragmentManager, titleResource, bodyResource, onPositive, onNegative
    )
}

class TextDoubleDialog(
    private val titleResource: Int,
    private val bodyResource: Int,
    private val onPositive: () -> Unit,
    private val onNegative: () -> Unit

) : DialogFragment() {

    class Builder(private val fm: FragmentManager) {

        private var onPositive: (() -> Unit)? = null
        private var onNegative: (() -> Unit)? = null

        fun setOnPositive(onPositive: () -> Unit) = apply { this.onPositive = onPositive }
        fun setOnNegative(onNegative: () -> Unit) = apply { this.onNegative = onNegative }
    }


    companion object {
        fun show(
            fm: FragmentManager,
            titleResource: Int,
            bodyResource: Int,
            onPositive: () -> Unit,
            onNegative: () -> Unit
        ) {
            val dialog = TextDoubleDialog(titleResource, bodyResource, onPositive, onNegative)
            dialog.show(fm, dialog.tag)
        }
    }


    private val screenUtil: ScreenUtil by inject()

    private val TAG = TextDoubleDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogTextDoubleBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogTextDoubleBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
    }

    private fun initView() {
        mBinding.apply {

            title.text = (resources.getString(titleResource))
            body.text = (resources.getString(bodyResource))

            txtConfirm.setOnDebounceClickListener {
                onPositive.invoke()
                dismiss()
            }
            txtCancel.setOnDebounceClickListener {
                onNegative.invoke()
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
        val width = (point.x * 0.9f).roundToInt()
        val height = screenUtil.toPixel(160).roundToInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


}