package com.eye.cool.book.params

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.SparseArray
import androidx.annotation.ColorInt

/**
 *Created by ycb on 2020/1/7 0007
 */
class QuickStickyParams private constructor(
    internal val keys: SparseArray<String>,
    internal val showFirstGroup: Boolean,
    internal val showStickyHeader: Boolean,
    internal val divider: Drawable,
    internal val textSize: Float,
    @ColorInt
    internal val textColor: Int,
    internal val textPaddingLeft: Float,
    @ColorInt
    internal val backgroundColor: Int,
    internal val titleHeight: Float
) {

  companion object {
    inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
  }

  data class Builder(
      var keys: SparseArray<String>? = null,
      var showFirstGroup: Boolean = false,
      var showStickyHeader: Boolean = true,
      var divider: Drawable = ColorDrawable(Color.parseColor("#f5f5f5")),
      var textSize: Float = 16f,
      @ColorInt
      var textColor: Int = Color.WHITE,
      var textPaddingLeft: Float = 10f,
      @ColorInt
      var backgroundColor: Int = Color.BLUE,
      var titleHeight: Float = 40f
  ) {

    fun keys(keys: SparseArray<String>) = apply { this.keys = keys }

    /**
     * Weather to show the first sticky title
     *
     * @param [shown] default true
     */
    fun showFirstGroup(shown: Boolean) = apply { this.showFirstGroup = shown }

    /**
     * Weather to show sticky title
     *
     * @param [shown] default true
     */
    fun showStickyHeader(shown: Boolean) = apply { this.showStickyHeader = shown }

    /**
     * Add an divider{@link ItemDecoration} to RecyclerView.
     *
     * @param [divider] divider to add
     */
    fun divider(divider: Drawable) = apply { this.divider = divider }

    /**
     * Set the default text size to the given value, interpreted as "scaled
     * pixel" units.  This size is adjusted based on the current density and
     * user font size preference.
     *
     * @param [textSize] The scaled pixel size.
     *
     */
    fun textSize(textSize: Float) = apply { this.textSize = textSize }

    /**
     * Sets the text color for all the sticky title
     *
     * @param [textColor] A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     */
    fun setTextColor(@ColorInt textColor: Int) = apply { this.textColor = textColor }

    /**
     * Set the default text padding to the given value, interpreted as "scaled
     * pixel" units.
     *
     * @param [paddingLeft]  The scaled pixel size.
     */
    fun textPaddingLeft(paddingLeft: Float) = apply { this.textPaddingLeft = paddingLeft }

    /**
     * Set the background to a given color
     *
     * @param [color] A color value in the form 0xAARRGGBB.
     */
    fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

    /**
     * Set the sticky title height, interpreted as "scaled pixel" units.
     *
     * @param [titleHeight] The scaled pixel size.
     */
    fun titleHeight(titleHeight: Float) = apply { this.titleHeight = titleHeight }

    fun build() = QuickStickyParams(
        keys = keys ?: SparseArray(),
        showFirstGroup = showFirstGroup,
        showStickyHeader = showStickyHeader,
        divider = divider,
        textSize = textSize,
        textColor = textColor,
        textPaddingLeft = textPaddingLeft,
        backgroundColor = backgroundColor,
        titleHeight = titleHeight
    )
  }
}