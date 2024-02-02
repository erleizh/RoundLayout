package com.erlei.ui.roundlayout

import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Size
import java.util.*

interface RoundView {

    fun getRadius(): FloatArray
    fun setRadius(radius: FloatArray)

    fun setRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float)

    fun setRadius(radius: Float)

    fun setTopLeftRadius(@Size(2) radius: FloatArray)
    fun setTopLeftRadius(radius: Float)

    fun setTopRightRadius(@Size(2) radius: FloatArray)
    fun setTopRightRadius(radius: Float)

    fun setBottomLeftRadius(@Size(2) radius: FloatArray)
    fun setBottomLeftRadius(radius: Float)

    fun setBottomRightRadius(@Size(2) radius: FloatArray)
    fun setBottomRightRadius(radius: Float)
}

abstract class BaseRoundViewImpl(
    val view: View,
    attrs: AttributeSet?
) : RoundViewImpl {

    companion object {
        private const val TAG = "RoundView"
        const val DEBUG = true
    }

    private var radius = FloatArray(8)

    init {
        if (attrs != null) {
            val attributes = view.context.obtainStyledAttributes(attrs, R.styleable.RoundView)
            if (attributes.hasValue(R.styleable.RoundView_topLeftRadius)) {
                radius.fill(attributes.getDimension(R.styleable.RoundView_topLeftRadius,0.0F),0,2)
            }
            if (attributes.hasValue(R.styleable.RoundView_topRightRadius)) {
                radius.fill(attributes.getDimension(R.styleable.RoundView_topRightRadius,0.0F),2,4)
            }
            if (attributes.hasValue(R.styleable.RoundView_bottomRightRadius)) {
                radius.fill(attributes.getDimension(R.styleable.RoundView_bottomRightRadius,0.0F),4,6)
            }
            if (attributes.hasValue(R.styleable.RoundView_bottomLeftRadius)) {
                radius.fill(attributes.getDimension(R.styleable.RoundView_bottomLeftRadius,0.0F),6,8)
            }
            if (attributes.hasValue(R.styleable.RoundView_radius_dimension)) {
                radius.fill(attributes.getDimension(R.styleable.RoundView_radius_dimension,0.0F))
            }
            if (attributes.hasValue(R.styleable.RoundView_radius)) {
                radius = parserAttrs(attributes.getString(R.styleable.RoundView_radius))
            }
            attributes.recycle()
        }
    }


    fun dip2px(dip: Float): Float {
        return dip * view.context.resources.displayMetrics.density + 0.5f
    }

    override fun getRadius(): FloatArray {
        return radius.clone()
    }

    /**
     *  0 topLeft
     *  2 topRight
     *  4 bottomRight
     *  6 bottomLeft
     */
    fun hasRoundCorner(index: Int): Boolean {
        return radius[index] > 0F && radius[index + 1] > 0
    }

    /**
     * 是否有圆角
     */
    fun hasRoundCorner(): Boolean {
        return hasRoundCorner(0) || hasRoundCorner(2) || hasRoundCorner(4) || hasRoundCorner(6)
    }

    @Suppress("ConstantConditionIf")
    protected fun log(format: String, vararg args: Any) {
        if (DEBUG) Log.d(TAG, String.format(Locale.getDefault(), format, *args))
    }

    /**
     * 解析字符串类型的圆角参数
     * xy
     * xy,xy,xy,xy
     * x,y,x,y,x,y,x,y,x,y,x,y,x,y,x,y
     */
    private fun parserAttrs(attr: String?): FloatArray {
        if (attr.isNullOrEmpty()) return FloatArray(8)
        val arr = FloatArray(8)
        try {
            val split = attr.split(",").toTypedArray()
            if (split.isEmpty()) return arr
            when (split.size) {
                1 -> {
                    arr.fill(dip2px(split.firstOrNull()?.toFloatOrNull() ?: 0.0F))
                }
                4 -> {
                    split.map { it.toFloatOrNull() ?: 0F }
                        .map { dip2px(it) }
                        .forEachIndexed { index, radius ->
                            arr[index * 2] = radius
                            arr[index * 2 + 1] = radius
                        }
                }
                8 -> {
                    split.map { it.toFloatOrNull() ?: 0F }
                        .map { dip2px(it) }
                        .forEachIndexed { index, radius ->
                            arr[index] = radius
                        }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return arr
    }

    override fun setRadius(radius: FloatArray) {
        if (radius.size == 4) {
            setRadius(radius[0], radius[1], radius[2], radius[3])
        } else if (radius.size == 8) {
            this.radius = radius
            updateRoundPath(view.width.toFloat(), view.height.toFloat())
            invalidate()
        }
    }

    open fun updateRoundPath(w: Float, h: Float) {
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        updateRoundPath(w.toFloat(), h.toFloat())
    }

    override fun setRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        setRadius(
            floatArrayOf(
                topLeft,
                topLeft,
                topRight,
                topRight,
                bottomRight,
                bottomRight,
                bottomLeft,
                bottomLeft
            )
        )
    }

    override fun setRadius(radius: Float) {
        setRadius(floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius))
    }

    override fun setTopLeftRadius(@Size(2) radius: FloatArray) {
        this.radius[0] = radius[0]
        this.radius[1] = radius[1]
        updateRoundPath(view.width.toFloat(), view.height.toFloat())
        invalidate()
    }

    override fun setTopLeftRadius(radius: Float) {
        setTopLeftRadius(floatArrayOf(radius, radius))
    }

    override fun setTopRightRadius(@Size(2) radius: FloatArray) {
        this.radius[2] = radius[0]
        this.radius[3] = radius[1]
        updateRoundPath(view.width.toFloat(), view.height.toFloat())
        invalidate()
    }

    open fun invalidate() {
        view.invalidate()
    }

    override fun setTopRightRadius(radius: Float) {
        setTopRightRadius(floatArrayOf(radius, radius))
    }

    override fun setBottomLeftRadius(@Size(2) radius: FloatArray) {
        this.radius[6] = radius[0]
        this.radius[7] = radius[1]
        updateRoundPath(view.width.toFloat(), view.height.toFloat())
        invalidate()
    }

    override fun setBottomLeftRadius(radius: Float) {
        setBottomLeftRadius(floatArrayOf(radius, radius))
    }

    override fun setBottomRightRadius(@Size(2) radius: FloatArray) {
        this.radius[4] = radius[0]
        this.radius[5] = radius[1]
        updateRoundPath(view.width.toFloat(), view.height.toFloat())
        invalidate()
    }

    override fun setBottomRightRadius(radius: Float) {
        setBottomRightRadius(floatArrayOf(radius, radius))
    }

}

interface RoundViewImpl : RoundView {
    companion object {

        fun createImpl(
            view: View,
            attrs: AttributeSet?
        ): RoundViewImpl {
//            return NoneRoundView()
            if (attrs != null) {
                val attributes = view.context.obtainStyledAttributes(attrs, R.styleable.RoundView)
                val impl =
                    when (attributes.getInt(R.styleable.RoundView_implementation_type, -1)) {
                        0 -> {
                            RoundViewOutlineImpl(view, attrs)
                        }
                        1 -> {
                            RoundViewXfermodeImpl(view, attrs)
                        }
                        2 -> {
                            RoundViewClipPathImpl(view, attrs)
                        }
                        3 -> {
                            RoundViewBitmapShaderImpl(view, attrs)
                        }
                        else -> RoundViewXfermodeImpl(view, attrs)
                    }
                attributes.recycle()
                return impl
            }
            return RoundViewXfermodeImpl(view, attrs)
        }
    }

    fun drawBefore(canvas: Canvas)
    fun drawAfter(canvas: Canvas)
    fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    fun replaceCanvas(canvas: Canvas): Canvas {
        return canvas
    }
}

class NoneRoundView:RoundViewImpl{
    private var radius = FloatArray(8)
    override fun drawBefore(canvas: Canvas) {
    }

    override fun drawAfter(canvas: Canvas) {
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }

    override fun getRadius(): FloatArray {
        return radius
    }

    override fun setRadius(radius: FloatArray) {
    }

    override fun setRadius(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
    }

    override fun setRadius(radius: Float) {
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