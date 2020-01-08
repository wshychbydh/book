package com.eye.cool.book.params

/**
 *Created by ycb on 2020/1/7 0007
 */
class QuickViewParams private constructor() {

  internal var barParams: BarParams = BarParams.Builder().build()

  internal var stickyParams: QuickStickyParams = QuickStickyParams.Builder().build()

  internal var toastParams: QuickToastParams = QuickToastParams.Builder().build()

  internal var dataParams: QuickDataParams? = null

  class Builder {

    private val quickParams = QuickViewParams()

    fun setQuickStickyParams(stickyParams: QuickStickyParams): Builder {
      quickParams.stickyParams = stickyParams
      return this
    }

    fun setQuickBarParams(barParams: BarParams): Builder {
      quickParams.barParams = barParams
      return this
    }

    fun setToastParams(toastParams: QuickToastParams): Builder {
      quickParams.toastParams = toastParams
      return this
    }

    fun setDataParams(dataParams: QuickDataParams): Builder {
      quickParams.dataParams = dataParams
      return this
    }

    fun build(): QuickViewParams {
      val data = quickParams.dataParams
          ?: throw IllegalArgumentException("QuickDataParams must be set!")
      if (data.data?.keys.isNullOrEmpty()) {
        throw IllegalArgumentException("Quick data can not be empty!")
      }
      if (data.viewHolders.isNullOrEmpty()) {
        throw IllegalArgumentException("Quick ViewHolder can not be empty!")
      }
      return quickParams
    }
  }
}