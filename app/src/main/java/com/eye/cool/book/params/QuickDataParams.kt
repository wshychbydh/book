package com.eye.cool.book.params

import com.eye.cool.adapter.support.DataViewHolder
import com.eye.cool.book.support.IQuickProvider

/**
 *Created by ycb on 2020/1/8 0008
 */
class QuickDataParams private constructor() {

  internal var data: LinkedHashMap<String, List<IQuickProvider>>? = null

  internal var viewHolders = hashMapOf<Class<out IQuickProvider>, Class<out DataViewHolder<out IQuickProvider>>>()

  class Builder {

    val params = QuickDataParams()

    fun setData(data: LinkedHashMap<String, List<IQuickProvider>>): Builder {
      params.data = data
      return this
    }

    fun registerViewHolders(map: Map<Class<out IQuickProvider>, Class<out DataViewHolder<out IQuickProvider>>>): Builder {
      map.forEach {
        params.viewHolders[it.key] = it.value
      }
      return this
    }

    fun registerViewHolder(dataClass: Class<out IQuickProvider>, viewHolder: Class<out DataViewHolder<out IQuickProvider>>): Builder {
      params.viewHolders[dataClass] = viewHolder
      return this
    }

    fun build() = params
  }
}