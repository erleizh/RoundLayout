package com.erlei.ui.roundlayout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.fenzotech.jimu.ui.round.RoundView
import com.fenzotech.jimu.ui.round.RoundViewImpl

@Suppress("LeakingThis")
open class RoundConstraintLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RoundView {

    private val impl = RoundViewImpl.createImpl(this, attrs)

    override fun dispatchDraw(canvas: Canvas) {
        impl.drawBefore(canvas)
        super.dispatchDraw(canvas)
        impl.drawAfter(canvas)
    }
    override fun getRadius(): FloatArray {
        return impl.getRadius()
    }
    override fun setRadius(radius: FloatArray) {
        impl.setRadius(radius)
    }

    override fun setRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        impl.setRadius(topLeft, topRight, bottomRight, bottomLeft)
    }

    override fun setRadius(radius: Float) {
        impl.setRadius(radius)
    }

    override fun setTopLeftRadius(radius: FloatArray) {
        impl.setTopLeftRadius(radius)
    }

    override fun setTopLeftRadius(radius: Float) {
        impl.setTopLeftRadius(radius)
    }

    override fun setTopRightRadius(radius: FloatArray) {
        impl.setTopRightRadius(radius)
    }

    override fun setTopRightRadius(radius: Float) {
        impl.setTopRightRadius(radius)
    }

    override fun setBottomLeftRadius(radius: FloatArray) {
        impl.setBottomLeftRadius(radius)
    }

    override fun setBottomLeftRadius(radius: Float) {
        impl.setBottomLeftRadius(radius)
    }

    override fun setBottomRightRadius(radius: FloatArray) {
        impl.setBottomRightRadius(radius)
    }

    override fun setBottomRightRadius(radius: Float) {
        impl.setBottomRightRadius(radius)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        impl.onSizeChanged(w, h, oldw, oldh)
    }
}