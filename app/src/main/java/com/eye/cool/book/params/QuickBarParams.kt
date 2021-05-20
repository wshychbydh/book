package com.eye.cool.book.params

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.AnimatorRes
import androidx.annotation.ColorInt
import com.eye.cool.book.R
import com.eye.cool.book.support.OnLetterSelectedListener

/**
 *Created by ycb on 2020/1/2 0002
 */
class QuickBarParams private constructor(
    internal val minWidth: Int,
    internal val letterSpace: Int,
    internal val isTiled: Boolean,
    @ColorInt
    internal val normalTextColor: Int,
    @ColorInt
    internal val selectedTextColor: Int,
    internal val textSize: Float,
    internal val showIndicator: Boolean,
    internal val indicatorColor: Int,
    internal val indicatorRadius: Float,
    internal val pressedBackgroundDrawable: Drawable?,
    internal val normalBackgroundDrawable: Drawable?,
    internal val showSelectedLetterAlways: Boolean,
    internal val showToast: Boolean,
    internal var toastTextView: TextView?,
    internal val toastDismissAnim: Int,
    internal val toastDuration: Long,
    internal var onLetterSelectedListener: OnLetterSelectedListener?,
    internal val lettersBackgroundColors: List<Int>?
) {

  companion object {
    inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()

    var DEFAULTS = arrayOf("热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
  }

  data class Builder(
      var minWidth: Int = 20,
      var letterSpace: Int = 6,
      var isTiled: Boolean = true,
      var normalTextColor: Int = Color.BLACK,
      var selectedTextColor: Int = Color.RED,
      var textSize: Float = 14f,
      var showIndicator: Boolean = true,
      var indicatorColor: Int = Color.parseColor("#FFA500"),
      var indicatorRadius: Float = 8f,
      var pressedBackgroundDrawable: Drawable? = null,
      var normalBackgroundDrawable: Drawable? = null,
      var showSelectedLetterAlways: Boolean = false,
      var showToast: Boolean = true,
      var toastTextView: TextView? = null,
      var toastDismissAnim: Int = R.animator.book_fade_out,
      var toastDuration: Long = 1500L,
      var lettersBackgroundColors: List<Int>? = null,
      var onLetterSelectedListener: OnLetterSelectedListener? = null,
  ) {

    /**
     * Sets the minimum width of the QuickBar. It is not guaranteed the view will
     * be able to achieve this minimum width (for example, if its parent layout
     * constrains it with less available width).
     *
     * @param [minWidth] The minimum width the view will try to be, in pixels
     *
     */
    fun minWidth(minWidth: Int) = apply { this.minWidth = minWidth }

    /**
     * Whether to spread the parent control
     *
     * @param [isTiled] spread ,default true
     */
    fun isTiled(isTiled: Boolean) = apply { this.isTiled = isTiled }

    /**
     * The space width for each letter. If covered full screen, use for top and bottom spacing.
     *
     * @param [letterSpace] The space width for each letter, in pixels
     *
     */
    fun letterSpace(letterSpace: Int) = apply {
      this.letterSpace = letterSpace
    }

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
     * Sets the text color for all the letters
     *
     * @param [textColor] A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     */
    fun textColor(@ColorInt textColor: Int) = apply { this.normalTextColor = textColor }

    /**
     * Sets the text color for selected the letter
     *
     * @param [selectedTextColor] A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     */
    fun selectedTextColor(@ColorInt selectedTextColor: Int) = apply {
      this.selectedTextColor = selectedTextColor
    }

    /**
     * Whether to display indicator when touching
     *
     * @param [showIndicator] default true
     */
    fun showIndicator(showIndicator: Boolean) = apply { this.showIndicator = showIndicator }

    /**
     * Sets the color for indicator
     *
     *@param [indicatorColor] A color value in the form 0xAARRGGBB.
     */
    fun indicatorColor(@ColorInt indicatorColor: Int) = apply {
      this.indicatorColor = indicatorColor
    }

    /**
     * Sets the radius for indicator
     *
     *@param indicatorRadius The radius for indicator, in pixels
     */
    fun indicatorRadius(indicatorRadius: Float) = apply {
      this.indicatorRadius = indicatorRadius
    }

    /**
     * Set the background to a given Drawable, or remove the background.
     *
     * @param [background] The Drawable to use as the background, or null to remove the
     *        background
     */
    fun backgroundDrawable(background: Drawable?) = apply {
      this.normalBackgroundDrawable = background
    }

    /**
     * Set the background when pressed to a given Drawable, or remove the background.
     *
     * @param [background] The Drawable to use as the background, or null to remove the
     *        background
     */
    fun pressedBackgroundDrawable(background: Drawable?) = apply {
      this.pressedBackgroundDrawable = background
    }

    /**
     * Sets the background color for each letters
     *
     * @param [colors] The colors used will be reused
     */
    fun letterBackgroundColors(@ColorInt colors: List<Int>) = apply {
      this.lettersBackgroundColors = colors
    }

    /**
     * Whether to mark the selected letter
     *
     * @param [showSelectedAlways] default true
     */
    fun showSelectedLetterAlways(showSelectedAlways: Boolean) = apply {
      this.showSelectedLetterAlways = showSelectedAlways
    }

    /**
     * Whether to show the toast view when selected letter
     *
     * @param [showToast] default true
     */
    fun showToastView(showToast: Boolean) = apply { this.showToast = showToast }

    /**
     * Set the toast view to be shown when selected letter
     *
     * @param [toastView] the toast view to be shown
     */
    fun toastView(toastView: TextView) = apply { this.toastTextView = toastView }

    /**
     * Set the toast dismiss anim when shown
     *
     * @param [resId] the anim to be played, default fade out in 1500L
     */
    fun toastViewDismissAnim(@AnimatorRes resId: Int) = apply { this.toastDismissAnim = resId }

    /**
     * Set the toast showing duration
     *
     * @param [duration] unit is millisecond, default 1500L
     */
    fun toastDuration(duration: Long) = apply { this.toastDuration = duration }

    /**
     * Register a callback to be invoked when letter is selected
     *
     * @param [onLetterChangedListener] The callback that will run
     */
    fun onLetterSelectedListener(onLetterChangedListener: OnLetterSelectedListener) = apply {
      this.onLetterSelectedListener = onLetterChangedListener
    }

    fun build() = QuickBarParams(
        minWidth = minWidth,
        letterSpace = letterSpace,
        isTiled = isTiled,
        normalTextColor = normalTextColor,
        selectedTextColor = selectedTextColor,
        textSize = textSize,
        showIndicator = showIndicator,
        indicatorColor = indicatorColor,
        indicatorRadius = indicatorRadius,
        pressedBackgroundDrawable = pressedBackgroundDrawable,
        normalBackgroundDrawable = normalBackgroundDrawable,
        showSelectedLetterAlways = showSelectedLetterAlways,
        showToast = showToast,
        toastTextView = toastTextView,
        toastDismissAnim = toastDismissAnim,
        toastDuration = toastDuration,
        lettersBackgroundColors = lettersBackgroundColors,
        onLetterSelectedListener = onLetterSelectedListener
    )
  }
}