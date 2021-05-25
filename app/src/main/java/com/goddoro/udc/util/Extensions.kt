package com.goddoro.udc.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass


/**
 * created By DORO 5/26/21
 */

inline fun <reified T> AppCompatActivity.startActivity(clazz: KClass<out T>, enterAnim : Int? = null, exitAnim : Int? = null, flags: Int? = null) where T : AppCompatActivity {
    val intent = Intent(this, clazz.java).apply {
        flags?.let { this.flags = it }
    }

    startActivity(intent)

    if ( exitAnim != null && enterAnim != null) {
        this.overridePendingTransition(enterAnim, exitAnim)
    }
}

inline fun <reified T> Fragment.startActivity(clazz: KClass<out T>, flags: Int? = null) where T : AppCompatActivity {
    val intent = Intent(requireActivity(), clazz.java).apply {
        flags?.let { this.flags = it }
    }
    startActivity(intent)
}
