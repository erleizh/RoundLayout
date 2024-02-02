package com.erlei.ui.roundlayout

import android.graphics.Canvas
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import com.fenzotech.jimu.ui.round.BaseRoundViewImpl

/**
 * Create by erlei on 4/16/21
 *
 * Email : erleizh@gmail.com
 *
 * Describe : 使用Outline的方式实现圆角,性能较高.锯齿明显.无法单独设置某个圆角
 */
class RoundViewOutlineImpl(
        view: View,
        attrs: AttributeSet?
) : BaseRoundViewImpl(view, attrs) {


    init {
        view.clipToOutline = true
        view.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, getRadius().first())
            }
        }
    }

    override fun invalidate() {
        view.invalidateOutline()
        super.invalidate()
    }

    override fun drawBefore(canvas: Canvas) {
    }

    override fun drawAfter(canvas: Canvas) {
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }

    override fun setTopLeftRadius(radius: FloatArray) {
    }

    override fun setTopLeftRadius(radius: Float) {
    }

    override fun setTopRightRadius(radius: FloatArray) {
    }

    override fun setTopRightRadius(radius: Float) {
    }

    override fun setBottomLeftRadius(radius: FloatArray) {
    }

    override fun setBottomLeftRadius(radius: Float) {
    }

    override fun setBottomRightRadius(radius: FloatArray) {
    }

    override fun setBottomRightRadius(radius: Float) {
    }
}