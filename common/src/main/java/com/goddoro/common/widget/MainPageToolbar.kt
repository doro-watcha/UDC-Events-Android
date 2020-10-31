package com.goddoro.common.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.goddoro.common.R
import com.goddoro.common.common.setVisibility
import com.goddoro.common.common.widget.setOnDebounceClickListener
import com.goddoro.common.databinding.ViewMainPageToolbarBinding


/**
 * created By DORO 2020/10/17
 */

class MainPageToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val mBinding: ViewMainPageToolbarBinding

    private var title: String = ""
    private var leftIconDrawable: Drawable? = null
    private var rightIconDrawable: Drawable? = null
    private var rightText: String = ""


    init {
        // Clear Background
        this.setBackgroundResource(0)

        val inflater = LayoutInflater.from(context)

        mBinding = ViewMainPageToolbarBinding.inflate(inflater, this, true)

        enableLeftIcon(false)
        enableRightIcon(false)

        context.theme.obtainStyledAttributes(attrs, R.styleable.MainPageToolbar, 0, 0)?.apply {
            getString(R.styleable.MainPageToolbar_toolbar_title)?.run {
                setTitle(this)
            }
            getResourceId(R.styleable.MainPageToolbar_toolbar_left_icon, 0)?.run {
                if (this == 0) return@run

                setLeftIcon(AppCompatResources.getDrawable(context, this))
            }
            getResourceId(R.styleable.MainPageToolbar_toolbar_right_icon, 0)?.run {
                if (this == 0) return@run

                setRightIcon(AppCompatResources.getDrawable(context, this))
            }
            getString(R.styleable.MainPageToolbar_toolbar_right_text)?.run {
                setRightText(this)
            }

            recycle()
        }

        ViewCompat.setElevation(this, getContext().resources.getDimension(R.dimen.toolbar_elevation))
    }

    fun setTitle(title: String?) {
        if (title == null) return

        this.title = title
        mBinding.titleText.text = title
    }

    fun setTitle(@StringRes id: Int?) {
        if (id == null) return

        context.getString(id).run {
            this@MainPageToolbar.title = this
            mBinding.titleText.text = this
        }
    }

    //region Left Icon
    fun setLeftIcon(drawable: Drawable?) {
        if (drawable == null) return

        enableLeftIcon(true)

        this.leftIconDrawable = drawable
        mBinding.leftButton.setImageDrawable(drawable)
    }

    fun setLeftIcon(@DrawableRes id: Int) {
        enableLeftIcon(true)

        this.leftIconDrawable = AppCompatResources.getDrawable(context, id)
        mBinding.leftButton.setImageDrawable(leftIconDrawable)
    }

    fun enableLeftIcon(enable: Boolean) {
        mBinding.leftButton.setVisibility(enable)
    }

    fun setLeftIconClickListener(listener: () -> Unit) {
        setLeftIconClickListener(OnClickListener { listener() })
    }

    fun setLeftIconClickListener(listener: OnClickListener) {
        mBinding.leftButton.setOnDebounceClickListener(listener)
    }
    //endregion

    //region Right Icon
    fun setRightIcon(drawable: Drawable?) {
        enableRightIcon(true)
        enableRightText(false)

        this.rightIconDrawable = drawable
        mBinding.rightButton.setImageDrawable(drawable)
    }

    fun setRightIcon(@DrawableRes id: Int) {
        enableRightIcon(true)
        enableRightText(false)

        this.rightIconDrawable = AppCompatResources.getDrawable(context, id)
        mBinding.rightButton.setImageDrawable(rightIconDrawable)
    }

    fun enableRightIcon(enable: Boolean) {
        mBinding.rightButton.setVisibility(enable)
    }

    fun setRightIconClickListener(listener: OnClickListener) {
        mBinding.rightButton.setOnDebounceClickListener(listener)
    }
    //endregion

    //region Right Text

    fun setRightText(@StringRes strRes: Int) {
        setRightText(context.getString(strRes))
    }

    fun setRightText(text: String) {
        enableRightIcon(false)
        enableRightText(true)
        rightText = text
        mBinding.rightText.text = rightText
    }

    fun setRightTextClickListener(listener: OnClickListener) {
        mBinding.rightText.setOnDebounceClickListener(listener)
    }

    fun enableRightText(enable: Boolean) {
        mBinding.rightText.setVisibility(enable)
    }

    //endregion
}

@BindingAdapter("app:toolbar_title_binding")
fun MainPageToolbar.setTitleTextBinding(text: String) {
    setTitle(text)
}

@BindingAdapter("app:toolbar_title_binding")
fun MainPageToolbar.setTitleTextBinding(@StringRes id: Int) {
    setTitle(id)
}

@BindingAdapter("app:toolbar_right_icon_binding")
fun MainPageToolbar.setRightIconBinding(drawable: Drawable) {
    setRightIcon(drawable)
}

@BindingAdapter("app:toolbar_left_icon_binding")
fun MainPageToolbar.setLeftIconBinding(drawable: Drawable) {
    setLeftIcon(drawable)
}

@BindingAdapter("app:toolbar_right_icon_visibility")
fun MainPageToolbar.setRightIconBinding(enable: Boolean) {
    enableRightIcon(enable)
}

@BindingAdapter("app:toolbar_left_icon_visibility")
fun MainPageToolbar.setLeftIconBinding(enable: Boolean) {
    enableLeftIcon(enable)
}

