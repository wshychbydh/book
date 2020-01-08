package com.eye.cool.book.params

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.SparseArray
import androidx.annotation.ColorInt

/**
 *Created by ycb on 2020/1/7 0007
 */
class QuickStickyParams private constructor() {
  internal val keys: SparseArray<String> = SparseArray()
  internal var showFirstGroup = false
  internal var showStickyHeader = true
  internal var divider: Drawable = ColorDrawable(Color.parseColor("#f5f5f5"))
  internal var textSize: Float = 16f
  internal var textColor: Int = Color.WHITE
  internal var textPaddingLeft: Float = 10f
  internal var backgroundColor: Int = Color.BLUE
  internal var titleHeight: Float = 40f

  class Builder {

    private val params = QuickStickyParams()

    /**
     * Weather to show the first sticky title
     *
     * @param showFirstGroup default true
     */
    fun isShowFirstGroup(showFirstGroup: Boolean): Builder {
      params.showFirstGroup = showFirstGroup
      return this
    }

    /**
     * Weather to show sticky title
     *
     * @param showStickyHeader default true
     */
    fun isShowStickyHeader(showStickyHeader: Boolean): Builder {
      params.showStickyHeader = showStickyHeader
      return this
    }

    /**
     * Add an divider{@link ItemDecoration} to RecyclerView.
     *
     * @param divider divider to add
     */
    fun setDivider(divider: Drawable): Builder {
      params.divider = divider
      return this
    }

    /**
     * Set the default text size to the given value, interpreted as "scaled
     * pixel" units.  This size is adjusted based on the current density and
     * user font size preference.
     *
     * @param textSize The scaled pixel size.
     *
     */
    fun setTextSize(textSize: Float): Builder {
      params.textSize = textSize
      return this
    }

    /**
     * Sets the text color for all the sticky title
     *
     * @param textColor A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     */
    fun setTextColor(@ColorInt textColor: Int): Builder {
      params.textColor = textColor
      return this
    }

    /**
     * Set the default text padding to the given value, interpreted as "scaled
     * pixel" units.
     *
     * @param paddingLeft  The scaled pixel size.
     */
    fun setTextPaddingLeft(paddingLeft: Float): Builder {
      params.textPaddingLeft = paddingLeft
      return this
    }

    /**
     * Set the background to a given color
     *
     * @param backgroundColor A color value in the form 0xAARRGGBB.
     */
    fun setBackgroundColor(@ColorInt backgroundColor: Int): Builder {
      params.backgroundColor = backgroundColor
      return this
    }

    /**
     * Set the sticky title height, interpreted as "scaled pixel" units.
     *
     * @param titleHeight The scaled pixel size.
     */
    fun setTitleHeight(titleHeight: Float): Builder {
      params.titleHeight = titleHeight
      return this
    }

    fun build(): QuickStickyParams = params
  }
}