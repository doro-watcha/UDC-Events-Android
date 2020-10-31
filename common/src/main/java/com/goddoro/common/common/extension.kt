package com.goddoro.common.common

import android.content.Context
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


/**
 * created By DORO 2020/10/10
 */

@Suppress("UNCHECKED_CAST")
fun HashMap<String, out Any?>.filterValueNotNull(): HashMap<String, Any> {
    return this.filterNot { it.value == null } as HashMap<String, Any>
}

fun String.toUri(): Uri = Uri.parse(this)
fun String.toFile(): File = File(this)

infix fun Fragment.getString(@StringRes id: Int): String = requireContext().resources.getString(id)
infix fun Fragment.getColor(@ColorRes id: Int): Int =
    ResourcesCompat.getColor(resources, id, this.activity?.theme)

infix fun AppCompatActivity.getString(@StringRes id: Int): String = resources.getString(id)
infix fun AppCompatActivity.getColor(@ColorRes id: Int): Int =
    ResourcesCompat.getColor(resources, id, this.theme)

fun AppCompatActivity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}

fun BottomSheetDialogFragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}