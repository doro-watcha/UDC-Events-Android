package com.goddoro.udc.views.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.debugE
import com.goddoro.common.common.setImageSrcGlide
import com.goddoro.common.util.ScreenUtil
import com.goddoro.udc.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt
import com.goddoro.udc.databinding.DialogImageBinding as DialogImageBinding


/**
 * created By DORO 11/21/20
 */


class ImageDialog(
    private val imageUrl: String
) : DialogFragment() {


    companion object {
        fun show(
            fm: FragmentManager,
            imageUrl : String
        ) {
            val dialog = ImageDialog(imageUrl)
            dialog.show(fm, dialog.tag)
        }
    }


    private val screenUtil: ScreenUtil by inject()

    private val TAG = ImageDialog::class.java.simpleName

    /**
     * Binding Instance
     */
    private var mBinding: DialogImageBinding by AutoClearedValue()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogImageBinding.inflate(inflater, container, false)

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }


        return mBinding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireActivity(), theme)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
    }

    private fun initView() {

        mBinding.image.apply {

            Glide.with(this).load(imageUrl).into(this)

            setOnClickListener {
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
        val height = (point.y * 0.9f).roundToInt()
        dialog?.window?.setLayout(width, height)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


}