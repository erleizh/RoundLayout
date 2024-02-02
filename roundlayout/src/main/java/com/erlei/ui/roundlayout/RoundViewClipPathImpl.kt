package com.erlei.ui.roundlayout

import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Size
import com.fenzotech.jimu.ui.round.BaseRoundViewImpl
import com.fenzotech.jimu.ui.round.RoundViewImpl

/**
 * Create by erlei on 4/16/21
 *
 * Email : erleizh@gmail.com
 *
 * Describe : 通过裁剪画板来实现圆角布局,锯齿比较明显,性能较高
 * 在华为荣耀x7机型上卡牌左右滑动的时候裁剪的有问题(系统bug)
 */
class RoundViewClipPathImpl(
    view: View, attrs: AttributeSet?
) : BaseRoundViewImpl(view, attrs) {

    private val path: Path by lazy {
        Path()
    }
    private var save = -1

    override fun drawBefore(canvas: Canvas) {
        if (!hasRoundCorner())return
        save = canvas.save()
        canvas.clipPath(path)
    }

    override fun drawAfter(canvas: Canvas) {
        if (!hasRoundCorner())return
        if (save > 0) {
            canvas.restoreToCount(save)
        }
    }

    override fun updateRoundPath(w: Float, h: Float) {
        path.reset()
        path.addRoundRect(0F, 0F, w, h, getRadius(), Path.Direction.CW)
    }
}