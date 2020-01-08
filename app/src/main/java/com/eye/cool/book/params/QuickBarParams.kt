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
class BarParams private constructor() {
  internal var letters: Array<String>? = null
  internal var minWidth: Int = 20
  internal var letterSpace: Int = 6
  internal var isTiled: Boolean = true
  internal var normalTextColor: Int = Color.BLACK
  internal var selectedTextColor: Int = Color.RED
  internal var textSize: Float = 14f
  internal var showIndicator: Boolean = true
  internal var indicatorColor: Int = Color.parseColor("#FFA500")
  internal var indicatorRadius: Float = 8f
  internal var pressedBackgroundDrawable: Drawable? = null
  internal var normalBackgroundDrawable: Drawable? = null
  internal var showSelectedLetterAlways: Boolean = false
  internal var showToast: Boolean = true
  internal var toastTextView: TextView? = null
  internal var toastDismissAnim: Int = R.animator.book_fade_out
  internal var toastDuration: Long = 1500L
  internal var onLetterSelectedListener: OnLetterSelectedListener? = null
  internal var lettersBackgroundColors: IntArray? = null

  class Builder {

    private val params = BarParams()

    /**
     * A quick look at the letters
     *
     * @param letters
     */
    internal fun setLetters(letters: Array<String>): Builder {
      params.letters = letters
      return this
    }

    /**
     * Sets the minimum width of the QuickBar. It is not guaranteed the view will
     * be able to achieve this minimum width (for example, if its parent layout
     * constrains it with less available width).
     *
     * @param minWidth The minimum width the view will try to be, in pixels
     *
     */
    fun setMinWidth(minWidth: Int): Builder {
      params.minWidth = minWidth
      return this
    }

    /**
     * Whether to spread the parent control
     *
     * @param isTiled spread ,default true
     */
    fun isTiled(isTiled: Boolean): Builder {
      params.isTiled = isTiled
      return this
    }

    /**
     * The space width for each letter. If covered full screen, use for top and bottom spacing.
     *
     * @param letterSpace The space width for each letter, in pixels
     *
     */
    fun setLetterSpace(letterSpace: Int): Builder {
      params.letterSpace = letterSpace
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
    fun setLetterTextSize(textSize: Float): Builder {
      params.textSize = textSize
      return this
    }

    /**
     * Sets the text color for all the letters
     *
     * @param textColor A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     */
    fun setLetterTextColor(@ColorInt textColor: Int): Builder {
      params.normalTextColor = textColor
      return this
    }

    /**
     * Sets the text color for selected the letter
     *
     * @param selectedTextColor A color value in the form 0xAARRGGBB.
     * Do not pass a resource ID. To get a color value from a resource ID, call
     * {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     *
     */
    fun setLetterSelectedTextColor(@ColorInt selectedTextColor: Int): Builder {
      params.selectedTextColor = selectedTextColor
      return this
    }

    /**
     * Whether to display indicator when touching
     *
     * @param showIndicator default true
     */
    fun showIndicator(showIndicator: Boolean): Builder {
      params.showIndicator = showIndicator
      return this
    }

    /**
     * Sets the color for indicator
     *
     *@param indicatorColor A color value in the form 0xAARRGGBB.
     */
    fun setIndicatorColor(@ColorInt indicatorColor: Int): Builder {
      params.indicatorColor = indicatorColor
      return this
    }

    /**
     * Sets the radius for indicator
     *
     *@param indicatorRadius The radius for indicator, in pixels
     */
    fun setIndicatorRadius(indicatorRadius: Float): Builder {
      params.indicatorRadius = indicatorRadius
      return this
    }

    /**
     * Set the background to a given Drawable, or remove the background.
     *
     * @param background The Drawable to use as the background, or null to remove the
     *        background
     */
    fun setBackgroundDrawable(background: Drawable?): Builder {
      params.normalBackgroundDrawable = background
      return this
    }

    /**
     * Set the background when pressed to a given Drawable, or remove the background.
     *
     * @param background The Drawable to use as the background, or null to remove the
     *        background
     */
    fun setPressedBackgroundDrawable(background: Drawable?): Builder {
      params.pressedBackgroundDrawable = background
      return this
    }

    /**
     * Sets the background color for each letters
     *
     * @param colors The colors used will be reused
     */
    fun setLetterBackgroundColors(@ColorInt vararg colors: Int): Builder {
      params.lettersBackgroundColors = colors
      return this
    }

    /**
     * Whether to mark the selected letter
     *
     * @param showSelectedAlways default true
     */
    fun isShowSelectedLetterAlways(showSelectedAlways: Boolean): Builder {
      params.showSelectedLetterAlways = showSelectedAlways
      return this
    }

    /**
     * Whether to show the toast view when selected letter
     *
     * @param showToast default true
     */
    fun showToastView(showToast: Boolean): Builder {
      params.showToast = showToast
      return this
    }

    /**
     * Set the toast view to be shown when selected letter
     *
     * @param toastView the toast view to be shown
     */
    fun setToastView(toastView: TextView): Builder {
      params.toastTextView = toastView
      return this
    }

    /**
     * Set the toast dismiss anim when shown
     *
     * @param resId the anim to be played, default fade out in 1500L
     */
    fun setToastViewDismissAnim(@AnimatorRes resId: Int): Builder {
      params.toastDismissAnim = resId
      return this
    }

    /**
     * Set the toast showing duration
     *
     * @param duration unit is millisecond, default 1500L
     */
    fun setToastDuration(duration: Long): Builder {
      params.toastDuration = duration
      return this
    }

    /**
     * Register a callback to be invoked when letter is selected
     *
     * @param onLetterChangedListener The callback that will run
     */
    fun setOnLetterSelectedListener(onLetterChangedListener: OnLetterSelectedListener): Builder {
      params.onLetterSelectedListener = onLetterChangedListener
      return this
    }

    fun build() = params
  }

  companion object {
    var DEFAULTS = arrayOf("热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
  }
}