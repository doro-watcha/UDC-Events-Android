package com.goddoro.common.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.R
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.databinding.DialogCommonBinding
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


/**
 * created By DORO 2020/09/26
 */


fun Fragment.showCommonDialog(
    @StringRes title: Int,
    @StringRes body: Int? = null,
    @StringRes okButtonText: Int? = null,
    @ColorRes okButtonColorResId: Int? = null,
    @StringRes cancelButtonText: Int? = null,
    listener: CommonDialog.OnButtonClickedListener? = null
) {
    showCommonDialog(
        getString(title),
        if (body != null) getString(body) else null,
        if (okButtonText != null) getString(okButtonText) else null,
        if (okButtonColorResId != null) resources.getColor(okButtonColorResId) else null,
        if (cancelButtonText != null) getString(cancelButtonText) else null,
        listener = listener
    )
}

fun Fragment.showCommonDialog(
    title: String,
    body: String? = null,
    okButtonText: String? = null,
    okButtonColor: Int? = null,
    cancelButtonText: String? = null,
    listener: CommonDialog.OnButtonClickedListener? = null
) {
    CommonDialog.showCommonDialog(
        childFragmentManager,
        title,
        body,
        okButtonText,
        okButtonColor,
        cancelButtonText,
        listener
    )
}

fun AppCompatActivity.showCommonDialog(
    @StringRes title: Int,
    @StringRes body: Int? = null,
    @StringRes okButtonText: Int? = null,
    @ColorRes okButtonColorResId: Int? = null,
    @StringRes cancelButtonText: Int? = null,
    listener: CommonDialog.OnButtonClickedListener? = null
) {
    showCommonDialog(
        getString(title),
        if (body != null) getString(body) else null,
        if (okButtonText != null) getString(okButtonText) else null,
        if (okButtonColorResId != null) getColor(okButtonColorResId) else null,
        if (cancelButtonText != null) getString(cancelButtonText) else null,
        listener = listener
    )
}

fun AppCompatActivity.showCommonDialog(
    title: String,
    body: String? = null,
    okButtonText: String? = null,
    okButtonColor: Int? = null,
    cancelButtonText: String? = null,
    listener: CommonDialog.OnButtonClickedListener? = null
) {
    CommonDialog.showCommonDialog(
        supportFragmentManager,
        title,
        body,
        okButtonText,
        okButtonColor,
        cancelButtonText,
        listener
    )
}


class CommonDialog private constructor(
    private val title: String,
    private val body: String? = null,
    private val okButtonText: String? = null,
    private val okButtonColor: Int? = null,
    private val buttonCancelText: String? = null,
    private val clickListener: OnButtonClickedListener? = null
) : DialogFragment() {

    interface OnButtonClickedListener {
        fun onNegativeButtonClicked() {}
        fun onPositiveButtonClicked() {}
        fun onDismiss() {}
    }

    companion object {
        fun showCommonDialog(
            fm: FragmentManager,
            title: String,
            body: String? = null,
            okButtonText: String? = null,
            okButtonColor: Int? = null,
            cancelButtonText: String? = null,
            listener: OnButtonClickedListener? = null
        ) {
            val dialog =
                CommonDialog(title, body, okButtonText, okButtonColor, cancelButtonText, listener)
            dialog.show(fm, dialog.tag)
        }
    }


    private val TAG = CommonDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogCommonBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogCommonBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner

        mBinding.title.text = title
        if (body != null)
            mBinding.body.text = body
        mBinding.button.text = okButtonText ?: getString(R.string.common_ok)
        mBinding.button.backgroundTintList =
            ColorStateList.valueOf(okButtonColor ?: resources.getColor(R.color.purpleDark))

        mBinding.button.setOnDebounceClickListener {
            clickListener?.onPositiveButtonClicked()
            this.dismiss()
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
        val width = (point.x * 0.9f).roundToInt()
        val height = toPixel(160).roundToInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        clickListener?.onDismiss()
    }

    val screenWidth: Int
        get() {
            return requireContext().resources.displayMetrics.widthPixels
        }

    val screenHeight: Int
        get() = requireContext().resources.displayMetrics.heightPixels

    fun toPixel(dp: Int): Float = requireContext().resources.displayMetrics.density * dp
}