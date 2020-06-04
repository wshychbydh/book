package com.eye.cool.book.view

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.eye.cool.book.params.BarParams
import kotlin.math.abs

/**
 * Created by cool on 2018/4/18.
 */

class QuickBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

  private var selectedNode: Node? = null
  private val paint = Paint()
  private var animator: Animator? = null

  private var indicatorY: Float = -1f

  private var params: BarParams? = null

  private var indicatorRadius: Float = 0f

  private val nodes = arrayListOf<Node>()

  init {
    paint.typeface = Typeface.DEFAULT
    paint.isAntiAlias = true
  }

  fun setBarParams(params: BarParams) {
    val letters = params?.letters ?: return
    this.params = params
    nodes.clear()
    letters.forEach {
      nodes.add(Node(text = it))
    }
    if (params.toastTextView != null) {
      animator = AnimatorInflater.loadAnimator(context, params.toastDismissAnim)
      animator!!.setTarget(params.toastTextView)
      animator!!.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          params.toastTextView?.visibility = GONE
        }
      })
      params.toastTextView!!.visibility = GONE
    }
    paint.textSize = getSpValue(context, params.textSize)
    indicatorRadius = getDipValue(context, params.indicatorRadius)
    requestLayout()
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    val measureWidth = measureViewWidth(widthMeasureSpec)

    val measuredHeight = measureViewHeight(measureWidth, heightMeasureSpec)

    setMeasuredDimension(measureWidth.toInt(), measuredHeight.toInt())
  }

  private fun measureViewWidth(widthMeasureSpec: Int): Float {
    val params = this.params ?: return 0f
    var minWidth = 0f
    val textRect = Rect()
    nodes.forEach { node ->
      paint.getTextBounds(node.text, 0, node.text.length, textRect)
      val textWidth = textRect.width().toFloat()
      val textHeight = textRect.height().toFloat()
      node.width = textWidth
      node.height = textHeight
      minWidth = minWidth.coerceAtLeast(textWidth)
    }
    minWidth = minWidth.coerceAtLeast(MeasureSpec.getSize(widthMeasureSpec).toFloat())

    val configWidth = getDipValue(context, params.minWidth.toFloat())

    return minWidth.coerceAtMost(configWidth)
  }

  private fun measureViewHeight(measureWidth: Float, heightMeasureSpec: Int): Float {
    val params = this.params ?: return 0f
    val rowSpace = getDipValue(context, params.letterSpace.toFloat())
    val fontMetrics = paint.fontMetrics
    val baseline = (abs(fontMetrics.ascent) - fontMetrics.descent) / 2f
    return if (params.isTiled) {
      val viewHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
      val cellHeight = (viewHeight - rowSpace * 2f) / nodes.size
      val offset = cellHeight / 2f + baseline

      nodes.forEachIndexed { index, node ->
        node.x = (measureWidth - node.width) / 2f
        node.top = rowSpace + index * cellHeight
        node.y = node.top + offset
        node.bottom = node.top + cellHeight
      }
      viewHeight
    } else {
      var totalHeight = rowSpace / 2f
      nodes.forEach { node ->
        val cellHeight = node.height + rowSpace
        node.x = (measureWidth - node.width) / 2f
        node.y = totalHeight + cellHeight / 2f + baseline
        node.top = totalHeight
        node.bottom = totalHeight + cellHeight - rowSpace
        totalHeight += cellHeight
      }
      totalHeight + rowSpace / 2f
    }
  }

  private var rect: RectF? = null

  override fun onDraw(canvas: Canvas) {
    val params = this.params ?: return
    super.onDraw(canvas)
    drawLettersBg(canvas, params.lettersBackgroundColors)
    drawIndicator(canvas, params)
    drawLetters(canvas, params)
  }

  private fun drawLetters(canvas: Canvas, params: BarParams) {
    nodes.forEach {
      if (params.showSelectedLetterAlways && it.isSelected) {
        paint.color = params.normalTextColor
      } else {
        paint.color = params.selectedTextColor
      }
      canvas.drawText(it.text, it.x, it.y, paint)
    }
  }

  private fun drawIndicator(canvas: Canvas, params: BarParams) {
    if (params.showIndicator && indicatorY > -1) {
      paint.color = params.indicatorColor
      canvas.drawCircle(measuredWidth / 2f, indicatorY, indicatorRadius, paint)
    }
  }

  private fun drawLettersBg(canvas: Canvas, bgColors: IntArray?) {
    if (bgColors != null && bgColors.isNotEmpty()) {
      var rect = this.rect ?: RectF()
      this.rect = rect
      nodes.forEachIndexed { index, node ->
        rect.top = node.top
        rect.bottom = node.bottom
        rect.left = 0f
        rect.right = measuredWidth.toFloat()
        paint.color = bgColors[index % bgColors.size]
        canvas.drawRect(rect, paint)
      }
    }
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    val params = this.params ?: return false
    when (event.action) {

      MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
        indicatorY = -1f
        if (params.normalBackgroundDrawable != null) {
          setBackgroundDrawable(params.normalBackgroundDrawable)
        }
        selectedNode = null
        invalidate()
      }

      else -> {
        indicatorY = event.y
        if (params.pressedBackgroundDrawable != null) {
          setBackgroundDrawable(params.pressedBackgroundDrawable)
        }
        nodes.find { it.isInNode(event.y) }?.let {
          showToast(it.text)

          if (it != selectedNode) {
            selectedNode?.isSelected = false
            it.isSelected = true
            selectedNode = it
            params.onLetterSelectedListener?.onSelected(it.text)
          }
        }
        invalidate()
      }
    }
    return true
  }

  override fun onDetachedFromWindow() {
    cancelToast()
    super.onDetachedFromWindow()
  }

  override fun onVisibilityChanged(changedView: View, visibility: Int) {
    if (visibility != VISIBLE) {
      cancelToast()
    }
    super.onVisibilityChanged(changedView, visibility)
  }

  private fun showToast(text: String) {
    if (params?.showToast != true) return
    params?.toastTextView?.apply {
      removeCallbacks(runnable)
      animator?.cancel()
      alpha = 1.0f
      this.text = text
      if (visibility != VISIBLE) {
        visibility = VISIBLE
      }
      postDelayed(runnable, params!!.toastDuration)
    }
  }

  private fun cancelToast() {
    if (params?.showToast != true) return
    params?.toastTextView?.apply {
      removeCallbacks(runnable)
      visibility = GONE
    }
  }

  private val runnable = Runnable {
    params?.toastTextView?.apply {
      animation?.cancel()
      animator!!.start()
    }
  }

  companion object {

    private fun getDipValue(context: Context, value: Float): Float {
      return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
          value, context.resources.displayMetrics)
    }

    private fun getSpValue(context: Context, value: Float): Float {
      return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
          value, context.resources.displayMetrics)
    }
  }

  private data class Node(
      var text: String,
      var x: Float = 0f,
      var y: Float = 0f,
      var isSelected: Boolean = false,
      var width: Float = 0f,
      var height: Float = 0f,
      var top: Float = 0f,
      var bottom: Float = 0f
  ) {

    fun isInNode(y: Float): Boolean {
      return y >= this.top && y <= this.bottom
    }
  }
}
