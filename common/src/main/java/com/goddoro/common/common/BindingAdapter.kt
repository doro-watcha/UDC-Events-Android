package com.goddoro.common.common



/**
 * created By DORO 2020/07/28
 */


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.goddoro.common.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlin.math.roundToInt

@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
@BindingAdapter("android:visibility_invisible")
fun View.setInvisibleVisibility(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("android:visibility_tag")
fun View.setVisibility(tag: String?) {
    this.visibility = if (tag == null) View.GONE else View.VISIBLE
}

@BindingAdapter("app:swiperefreshlayout_refreshing")
fun SwipeRefreshLayout.setRefreshingBinding(isRefreshing: Boolean) {
    this.isRefreshing = isRefreshing
}

@BindingAdapter("app:selected_binding")
fun TextView.setSelectedBinding(select: Boolean) {
    isSelected = select
}

@BindingAdapter("android:text_animate_alpha")
fun TextView.setTextWithAlphaAnimation(str: String?) {
    ObjectAnimator.ofFloat(this@setTextWithAlphaAnimation, "alpha", 1f, 0f).apply {
        repeatCount = 1
        repeatMode = ObjectAnimator.REVERSE

        duration = 100L

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationRepeat(animation: Animator?) {
                this@setTextWithAlphaAnimation.text = str ?: ""
            }
        })

        start()
    }
}

@BindingAdapter("android:texta")
fun TextView.setTextBinding(@StringRes id: Int?) {
    if (id == 0x00 || id == null)
        this.text = ""
    else {
        this.setText(id)
    }
}

@BindingAdapter("imageSrc", "fadeOn", requireAll = false)
fun ImageView.setImageSrcGlide(src: Uri?, fadeOn: Boolean? = true) {
    if (fadeOn == true) {
        Glide.with(this).load(src).transition(DrawableTransitionOptions.withCrossFade()).into(this)
    } else {
        Glide.with(this).load(src).into(this)
    }
}

@BindingAdapter("srcUrl", "placeholder", requireAll = false)
fun ImageView.loadUrlAsync(url: String?, placeholder: Drawable? = null) {
    if (url == null) {
        Glide.with(this).load(placeholder).into(this)
    } else {
        Glide.with(this).load(url)
            .apply {
                if (placeholder != null)
                    (placeholder)
            }
            .into(this)
    }
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}


@BindingAdapter("imageSrc", "fadeOn", requireAll = false)
fun ImageView.setImageSrcGlide(src: String?, fadeOn: Boolean? = true) {
    if (fadeOn == true) {
        Glide.with(this).load(src).transition(DrawableTransitionOptions.withCrossFade()).into(this)
    } else {
        Glide.with(this).load(src).into(this)
    }
}

@BindingAdapter("app:maxWidth_screen_padding")
fun View.setWidthExceptPaddingBinding(dp: Int) {
    val padding = (resources.displayMetrics.density * dp).roundToInt()
    val screenWidth = context.resources.displayMetrics.widthPixels
    this.layoutParams = this.layoutParams.apply { width = screenWidth - padding }
}

@BindingAdapter("imageSrc")
fun ImageView.setImageSrcGlide(src: String?) {

    Glide.with(this).load(src).transition(DrawableTransitionOptions.withCrossFade()).into(this)
}

@BindingAdapter("blurred")
fun ImageView.setBlurImage( src : String?) {

    Glide.with(this).load(src)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
        .into(this)
}

@BindingAdapter("android:inputType")
fun EditText.setInputType(editable : Boolean) {
    if ( !editable) this.inputType = InputType.TYPE_NULL
    else this.inputType = InputType.TYPE_CLASS_TEXT
}
