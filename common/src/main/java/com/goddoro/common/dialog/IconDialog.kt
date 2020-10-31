package com.goddoro.common.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.R
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.databinding.DialogIconBinding
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


/**
 * created By DORO 2020/09/26
 */



fun AppCompatActivity.showErrorDialog(
    title: Int,
    dialogIcon : Int? = R.drawable.ic_back_arrow,
    listener: OnDismissListener? = null
) {
    CommonSingleDialog.show(
        supportFragmentManager,dialogIcon, title
    )
}

fun Fragment.showErrorDialog(
    title: Int,
    dialogIcon : Int? = R.drawable.ic_back_arrow,
    listener: OnDismissListener? = null
) {
    CommonSingleDialog.show(
        childFragmentManager,dialogIcon, title
    )
}


fun AppCompatActivity.showIconDialog(
    title : Int,
    dialogIcon : Int? = R.drawable.ic_camera
) {
    CommonSingleDialog.show(
        supportFragmentManager,dialogIcon,title
    )
}
fun Fragment.showIconDialog(
    title : Int,
    dialogIcon : Int? = R.drawable.ic_camera
) {
    CommonSingleDialog.show(
        childFragmentManager,dialogIcon,title
    )
}


class CommonSingleDialog (
    private val iconResource : Int?,
    private val textResource : Int?,
    private val onPositive: (() -> Unit)? = null,
    private val onNegative: (() -> Unit)? = null,
    private val onDismiss : (() -> Unit)? = null

): DialogFragment() {

    class Builder(private val fm: FragmentManager) {

        private var onPositive: (() -> Unit)? = null
        private var onNegative: (() -> Unit)? = null

        fun setOnPositive(onPositive: () -> Unit) = apply { this.onPositive = onPositive }
        fun setOnNegative(onNegative: () -> Unit) = apply { this.onNegative = onNegative }
    }

    companion object {
        fun show(
            fm: FragmentManager,
            iconResource: Int?,
            textResource: Int?,
            onPositive: (() -> Unit)? = null) {
            val dialog = CommonSingleDialog(iconResource, textResource, onPositive)
            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = CommonSingleDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogIconBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogIconBinding.inflate(inflater, container, false)

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

    private fun initView () {
        mBinding.apply {

            if ( onNegative == null ) {
                btnNegative.visibility = View.GONE
            }

            dialogIcon.setImageDrawable(resources.getDrawable(iconResource ?: 0))
            dialogTxt.text = resources.getText(textResource ?: 0)

            btnPositive.setOnDebounceClickListener{
                onPositive?.invoke()
                dismiss()
            }
            btnNegative.setOnDebounceClickListener{
                onNegative?.invoke()
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
        val height = toPixel(160).roundToInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun toPixel(dp: Int): Float = requireContext().resources.displayMetrics.density * dp

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

}