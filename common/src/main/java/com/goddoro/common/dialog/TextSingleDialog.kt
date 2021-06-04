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
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.databinding.DialogTextSingleBinding
import com.goddoro.common.util.ScreenUtil
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

fun AppCompatActivity.showTextDialog(
    title: String,
    body : String
) {
    TextSingleDialog.show(
        supportFragmentManager,title, body
    )
}

fun Fragment.showTextDialog(
    title: String,
    body : String
) {
    TextSingleDialog.show(
        childFragmentManager, title, body
    )
}

class TextSingleDialog(
    private val _title : String,
    private val _body : String

): DialogFragment() {

    companion object {
        fun show(
            fm: FragmentManager,
            title: String,
            body: String) {
            val dialog = TextSingleDialog(title, body)
            dialog.show(fm, dialog.tag)
        }
    }


    private val screenUtil: ScreenUtil by inject()

    private val TAG = TextSingleDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogTextSingleBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogTextSingleBinding.inflate(inflater, container, false)

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

            title.text = _title
            body.text = _body

            txtConfirm.setOnClickListener{
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
        val height = (point.y * 0.2f).roundToInt()
        dialog?.window?.setLayout(width, height)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


}