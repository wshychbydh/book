package com.eye.cool.book.params

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 *Created by ycb on 2020/1/7 0007
 */
class QuickToastParams private constructor() {

  internal var toastTextSize: Float = 24f
  internal var toastTextColor: Int = Color.WHITE
  internal var toastBackgroundColor: Int = 0
  internal var toastBackgroundDrawable: Drawable? = null
  internal var toastViewWidth: Int = 120
  internal var toastViewHeight: Int = 120

  class Builder {

    private val params = QuickToastParams()

    fun setToastTextSize(textSize: Float): Builder {
      params.toastTextSize = textSize
      return this
    }

    fun setToastTextColor(@ColorInt textColor: Int): Builder {
      params.toastTextColor = textColor
      return this
    }

    fun setToastBackgroundColor(@ColorInt backgroundColor: Int): Builder {
      params.toastBackgroundColor = backgroundColor
      return this
    }

    fun setToastBackgroundDrawable(drawable: Drawable): Builder {
      params.toastBackgroundDrawable = drawable
      return this
    }

    fun setToastViewWidth(width: Int): Builder {
      params.toastViewWidth = width
      return this
    }

    fun setToastViewHeight(height: Int): Builder {
      params.toastViewHeight = height
      return this
    }

    fun build() = params
  }
}