package com.eye.cool.book.params

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 *Created by ycb on 2020/1/7 0007
 */
class QuickToastParams private constructor(
    internal val textSize: Float,
    @ColorInt
    internal val textColor: Int,
    internal val backgroundColor: Int?,
    internal val backgroundDrawable: Drawable?,
    internal val viewWidth: Int,
    internal val viewHeight: Int
) {

  companion object {
    inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
  }

  data class Builder(
      var textSize: Float = 24f,
      @ColorInt
      var textColor: Int = Color.WHITE,
      var backgroundColor: Int? = null,
      var backgroundDrawable: Drawable? = null,
      var viewWidth: Int = 120,
      var viewHeight: Int = 120
  ) {

    fun textSize(textSize: Float) = apply { this.textSize = textSize }

    fun textColor(@ColorInt textColor: Int) = apply { this.textColor = textColor }

    fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

    fun backgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

    fun viewWidth(width: Int) = apply { this.viewWidth = width }

    fun viewHeight(height: Int) = apply { this.viewHeight = height }

    fun build() = QuickToastParams(
        textSize = textSize,
        textColor = textColor,
        backgroundColor = backgroundColor,
        backgroundDrawable = backgroundDrawable,
        viewWidth = viewWidth,
        viewHeight = viewHeight
    )
  }
}