package com.erlei.ui.roundlayout

import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.fenzotech.jimu.ui.R


/**
 * Create by erlei on 4/16/21
 *
 * Email : erleizh@gmail.com
 *
 * Describe : 通过使用Xfermode混合模式来实现圆角布局,通过添加一个透明边框解决锯齿严重的问题
 */
class RoundViewXfermodeImpl(
        view: View, attrs: AttributeSet?
) : BaseRoundViewImpl(view, attrs) {

    private var transparentBorderWidth = 1F

    init {
        if (attrs != null) {
            val attributes = view.context.obtainStyledAttributes(attrs, R.styleable.RoundView)
            transparentBorderWidth =
                    attributes.getDimension(R.styleable.RoundView_transparent_border_width, 1F)
            attributes.recycle()
        }
    }

    private val roundPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = Paint.Style.FILL
            isDither = true
            isFilterBitmap = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        }
    }

    private val roundPath: Path by lazy {
        Path()
    }
    private val imagePaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    }
    private val mBounds = RectF()

    override fun drawBefore(canvas: Canvas) {
        if (hasRoundCorner()) {
            mBounds.set(canvas.clipBounds)
            canvas.saveLayer(mBounds, imagePaint)
        }
    }

    override fun drawAfter(canvas: Canvas) {
        if (hasRoundCorner()) {
            canvas.drawPath(roundPath, roundPaint)
            canvas.restore()
        }
    }

    override fun updateRoundPath(w: Float, h: Float) {
        roundPath.reset()
        roundPath.fillType = Path.FillType.INVERSE_EVEN_ODD
        roundPath.addRoundRect(
                transparentBorderWidth,
                transparentBorderWidth,
                w - transparentBorderWidth,
                h - transparentBorderWidth,
                getRadius(),
                Path.Direction.CW
        )
    }
}