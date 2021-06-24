package com.goddoro.common.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.goddoro.common.R
import com.goddoro.common.databinding.ViewCustomToastBinding

class ToastUtil ( val context : Context) {

    fun createToast( message: String): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ViewCustomToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_custom_toast, null, false)

        binding.tvSample.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 16.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}