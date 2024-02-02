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
 * Describe : 使用BitmapShader创建圆角布局,通过添加透明边框来实现
 * 在复杂布局的情况下可能会显示异常.实测在积目发现卡牌场景下显示异常
 * https://stackoverflow.com/questions/26074784/how-to-make-a-view-with-rounded-corners?noredirect=1&lq=1
 */
class RoundViewBitmapShaderImpl(
        view: View,
        attrs: AttributeSet?
) : BaseRoundViewImpl(view, attrs) {

    private val roundPath: Path by lazy {
        Path()
    }

    private lateinit var offscreenCanvas: Canvas

    private val roundPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            isDither = true
            isFilterBitmap = true
        }
    }
    private var transparentBorderWidth = 1F

    init {
        if (attrs != null) {
            val attributes = view.context.obtainStyledAttributes(attrs, R.styleable.RoundView)
            this.transparentBorderWidth =
                    attributes.getDimension(R.styleable.RoundView_transparent_border_width, 1F)
            attributes.recycle()
        }
    }


    override fun drawBefore(canvas: Canvas) {
        if (!hasRoundCorner()) return
        if (!::offscreenCanvas.isInitialized
                || view.width != offscreenCanvas.width || view.height != offscreenCanvas.height
        ) {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            offscreenCanvas = Canvas(bitmap)
            roundPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val nw = canvas.width - this.transparentBorderWidth
            val nh = canvas.height - this.transparentBorderWidth
            offscreenCanvas.scale(
                    nw / canvas.width.toFloat(),
                    nh / canvas.height.toFloat(),
                    canvas.width / 2F,
                    canvas.height / 2F
            )
        }
//        scrollView 需要添加平移
//        val scrollX: Float = view.scrollX.toFloat()
//        val scrollY: Float = view.scrollY.toFloat()
//        offscreenCanvas.translate(-scrollX, -scrollY)
    }

    override fun drawAfter(canvas: Canvas) {
        if (!hasRoundCorner()) return
//        scrollView 需要添加平移
//        val scrollX: Float = view.scrollX.toFloat()
//        val scrollY: Float = view.scrollY.toFloat()
//        offscreenCanvas.translate(scrollX, scrollY)
//        canvas.translate(scrollX, scrollY)
        canvas.drawPath(roundPath, roundPaint)
    }

    override fun replaceCanvas(canvas: Canvas): Canvas {
        return if (hasRoundCorner()) {
            offscreenCanvas
        } else {
            canvas
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        updateRoundPath(w.toFloat(), h.toFloat())
    }

    override fun updateRoundPath(w: Float, h: Float) {
        roundPath.reset()
        roundPath.addRoundRect(
                0F + this.transparentBorderWidth,
                0F + this.transparentBorderWidth,
                w - this.transparentBorderWidth,
                h - this.transparentBorderWidth,
                getRadius(),
                Path.Direction.CW
        )
    }
}