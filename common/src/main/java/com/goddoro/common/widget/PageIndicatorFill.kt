package com.goddoro.common.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.databinding.BindingAdapter
import com.goddoro.common.R


class PageIndicatorFill (context : Context, attributeSet : AttributeSet?= null) : View(context, attributeSet){

    private var eventCount = 0
    private var position = 0
    private var length = 0f

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.style = Paint.Style.FILL_AND_STROKE
        this.color = resources.getColor(R.color.white)
    }


    init {
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        length = width.toFloat() / eventCount
        canvas?.drawRect( 0f ,0f, (position+1) * length, height.toFloat(), paint )

    }

    fun setEventCount( _eventCount : Int? = 0) {
        this.eventCount = _eventCount ?: 0
    }

    fun refresh( _position : Int ) {
        this.position = _position
        invalidate()
    }
}

@BindingAdapter("app:page_fill_count")
fun PageIndicatorFill.setPageFillCount( count : Int?= 0) {
    setEventCount(count)
}