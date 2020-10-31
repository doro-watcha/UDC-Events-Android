package com.goddoro.map.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.data.model.NaverItem
import com.goddoro.map.R
import com.goddoro.map.databinding.DialogMapDetailBinding
import kotlin.math.roundToInt


/**
 * created By DORO 2020/09/14
 */

class MapDetailDialog ( private val item : NaverItem) : DialogFragment(){

    companion object {
        fun show(fm: FragmentManager , item : NaverItem) {
            val dialog = MapDetailDialog(item)
            dialog.show(fm, dialog.tag)
        }
    }

    private var mBinding : DialogMapDetailBinding by AutoClearedValue()

    override fun getTheme(): Int = R.style.Theme_UDC_MapDetailDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return DialogMapDetailBinding.inflate(inflater, container, false).run {
            mBinding = this
            this.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.txtLocation.text = item.position.toString()
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
        val height = (point.y * 0.5f).roundToInt()
        dialog?.window?.setLayout(width, height)
    }
}