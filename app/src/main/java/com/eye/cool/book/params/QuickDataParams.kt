package com.eye.cool.book.params

import com.eye.cool.book.adapter.DataViewHolder
import com.eye.cool.book.support.IQuickProvider

/**
 *Created by ycb on 2020/1/8 0008
 */
class QuickDataParams private constructor(
    internal val data: LinkedHashMap<String, List<IQuickProvider>>,

    internal val viewHolders: Map<Class<out IQuickProvider>,
        Class<out DataViewHolder<out IQuickProvider>>>
) {

  companion object {
    inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
  }

  data class Builder(
      var data: LinkedHashMap<String, List<IQuickProvider>>? = null,

      var viewHolders: Map<Class<out IQuickProvider>,
          Class<out DataViewHolder<out IQuickProvider>>>? = null
  ) {

    /**
     * Association grouping and data for display
     *
     * @param [data]
     */
    fun data(data: LinkedHashMap<String, List<IQuickProvider>>) = apply { this.data = data }

    fun viewHolders(viewHolders: Map<Class<out IQuickProvider>,
        Class<out DataViewHolder<out IQuickProvider>>>) = apply {
      this.viewHolders = viewHolders
    }

    fun registerViewHolders(
        map: Map<Class<out IQuickProvider>, Class<out DataViewHolder<out IQuickProvider>>>
    ) = apply {
      val maps = this.viewHolders?.toMutableMap() ?: hashMapOf()
      map.forEach {
        maps[it.key] = it.value
      }
      this.viewHolders = maps
    }

    /**
     * Grouping data display style
     *
     * @param [dataClass] grouping type
     * @param [viewHolder] display style
     */
    fun registerViewHolder(
        dataClass: Class<out IQuickProvider>,
        viewHolder: Class<out DataViewHolder<out IQuickProvider>>
    ) = apply {
      val maps = this.viewHolders?.toMutableMap() ?: hashMapOf()
      maps[dataClass] = viewHolder
      this.viewHolders = maps
    }

    fun build(): QuickDataParams {
      val data = this.data
          ?: throw IllegalArgumentException("QuickDataParams must be set!")
      if (data.keys.isNullOrEmpty()) {
        throw IllegalArgumentException("Quick data can not be empty!")
      }
      if (viewHolders.isNullOrEmpty()) {
        throw IllegalArgumentException("Quick ViewHolder can not be empty!")
      }
      return QuickDataParams(data = data, viewHolders = viewHolders!!)
    }
  }
}