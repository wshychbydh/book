package com.eye.cool.book.params

/**
 *Created by ycb on 2020/1/7 0007
 */
class QuickViewParams private constructor(
    internal val barParams: QuickBarParams,
    internal val stickyParams: QuickStickyParams,
    internal val toastParams: QuickToastParams,
    internal val dataParams: QuickDataParams
) {

  companion object {
    inline fun build(
        data: QuickDataParams,
        block: Builder.() -> Unit
    ) = Builder(data).apply(block).build()
  }

  data class Builder(
      var dataParams: QuickDataParams,
      var barParams: QuickBarParams = QuickBarParams.Builder().build(),
      var stickyParams: QuickStickyParams = QuickStickyParams.Builder().build(),
      var toastParams: QuickToastParams = QuickToastParams.Builder().build()
  ) {

    fun quickStickyParams(stickyParams: QuickStickyParams) = apply {
      this.stickyParams = stickyParams
    }

    fun quickBarParams(barParams: QuickBarParams) = apply { this.barParams = barParams }

    fun toastParams(toastParams: QuickToastParams) = apply { this.toastParams = toastParams }

    fun dataParams(dataParams: QuickDataParams) = apply { this.dataParams = dataParams }

    fun build() = QuickViewParams(
        barParams = barParams,
        stickyParams = stickyParams,
        toastParams = toastParams,
        dataParams = dataParams
    )
  }
}