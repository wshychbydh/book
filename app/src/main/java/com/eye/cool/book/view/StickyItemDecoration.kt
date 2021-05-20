package com.eye.cool.book.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eye.cool.book.params.QuickStickyParams

/**
 * Created by cool on 2017/4/19.
 * Only support LinearLayoutManager.Vertical
 */

internal class StickyItemDecoration(
    private val context: Context,
    private val params: QuickStickyParams
) : RecyclerView.ItemDecoration() {

  private val textPaint = Paint()
  private val backgroundPaint = Paint()
  private val textHeight: Float
  private val textBaselineOffset: Float
  private val titleHeight: Float

  init {
    textPaint.isAntiAlias = true
    textPaint.textSize = getSpValue(context, params.textSize)
    textPaint.color = params.textColor
    val fm = textPaint.fontMetrics
    textHeight = fm.bottom - fm.top //measure text height
    textBaselineOffset = fm.bottom

    titleHeight = getDipValue(context, params.titleHeight)

    backgroundPaint.isAntiAlias = true
    backgroundPaint.color = params.backgroundColor
  }

  override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDraw(c, parent, state)
    drawVertical(c, parent)
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    super.onDrawOver(c, parent, state)

    if (!params.showStickyHeader) {
      return
    }

    if (params.keys.size() == 0) return

    val firstVisiblePos = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    if (!params.showFirstGroup && firstVisiblePos < params.keys.valueAt(0).length) return

    if (firstVisiblePos == RecyclerView.NO_POSITION) return

    val title = getTitle(firstVisiblePos)
    if (title.isNullOrEmpty()) return

    var flag = false
    if (getTitle(firstVisiblePos + 1) != null
        && title != getTitle(firstVisiblePos + 1)) {
      val child = parent.findViewHolderForAdapterPosition(firstVisiblePos)!!.itemView
      if (child.top + child.measuredHeight < titleHeight) {
        c.save()
        flag = true
        c.translate(0f, (child.top + child.measuredHeight - titleHeight))
      }
    }

    val left = parent.paddingLeft
    val right = parent.width - parent.paddingRight
    val top = parent.paddingTop
    val bottom = top + titleHeight
    c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom, backgroundPaint)
    val x = getDipValue(context, params.textPaddingLeft)
    val y = bottom - (titleHeight - textHeight) / 2f - textBaselineOffset
    c.drawText(title!!, x, y, textPaint!!)

    if (flag) {
      c.restore()
    }
  }

  override fun getItemOffsets(
      outRect: Rect,
      view: View,
      parent: RecyclerView,
      state: RecyclerView.State
  ) {
    super.getItemOffsets(outRect, view, parent, state)
    val pos = parent.getChildAdapterPosition(view)
    if (!params.showFirstGroup && pos == 0) {
      outRect.set(0, 0, 0, 0)
    } else {
      if (params.keys.indexOfKey(pos) > -1) {
        outRect.set(0, titleHeight.toInt(), 0, 0)
      } else {
        outRect.set(0, getDividerHeight(), 0, 0)
      }
    }
  }

  private fun getTitle(position: Int): String? {
    var tempPos = position
    while (findPosition(tempPos)) {
      if (params.keys.indexOfKey(tempPos) > -1) {
        return params.keys.get(tempPos)
      }
      tempPos--
    }
    return null
  }

  private fun findPosition(position: Int): Boolean {
    return if (params.showFirstGroup) {
      position >= 0
    } else {
      position > 0
    }
  }

  private fun drawVertical(c: Canvas, parent: RecyclerView) {
    val left = parent.paddingLeft
    val right = parent.width - parent.paddingRight
    var top: Int
    var bottom: Int
    for (i in 1 until parent.childCount) {
      val child = parent.getChildAt(i)
      val params = child.layoutParams as RecyclerView.LayoutParams
      val position = params.viewLayoutPosition
      if (this.params.keys.indexOfKey(position) > -1) {
        top = child.top - params.topMargin - titleHeight.toInt()
        bottom = top + titleHeight.toInt()
        c.drawRect(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            backgroundPaint
        )
        val x = getDipValue(context, this.params.textPaddingLeft)
        val y = bottom.toFloat() - (titleHeight - textHeight) / 2f - textBaselineOffset
        c.drawText(this.params.keys.get(position), x, y, textPaint)
      } else {
        top = child.top - params.topMargin - getDividerHeight()
        bottom = top + getDividerHeight()
        this.params.divider.setBounds(left, top, right, bottom)
        this.params.divider.draw(c)
      }
    }
  }

  private fun getDividerHeight(): Int {
    if (params.divider is ColorDrawable) return 1
    return params.divider.intrinsicHeight
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
}
