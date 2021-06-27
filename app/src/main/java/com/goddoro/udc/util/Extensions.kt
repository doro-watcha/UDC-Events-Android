package com.goddoro.udc.util

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Intent
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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


fun ViewPager2.setCurrentItem(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width, // Default value taken from getWidth() from ViewPager2 view
    pagePxHeight:Int = height
) {
    val pxToDrag: Int = if (orientation == ViewPager2.ORIENTATION_HORIZONTAL)
    {
        pagePxWidth * (item - currentItem)
    }
    else

    {
        pagePxHeight * (item - currentItem)
    }


    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
        override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
        override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}
