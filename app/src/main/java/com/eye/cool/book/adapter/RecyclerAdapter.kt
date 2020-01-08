package com.eye.cool.book.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.eye.cool.book.support.IQuickProvider
import java.util.*

/**
 * Created by cool on 18/6/14.
 */
open class RecyclerAdapter : RecyclerView.Adapter<DataViewHolder<IQuickProvider>>() {

  private val viewHolder = SparseArray<Class<out DataViewHolder<*>>>()
  protected val data = ArrayList<IQuickProvider>()
  private var clickListener: View.OnClickListener? = null
  private var checkedListener: CompoundButton.OnCheckedChangeListener? = null
  private var longClickListener: View.OnLongClickListener? = null

  override fun getItemCount(): Int {
    return data.size
  }

  override fun getItemViewType(position: Int): Int {
    return data[position].javaClass.name.hashCode()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<IQuickProvider> {
    val clazz = viewHolder.get(viewType)
        ?: throw IllegalArgumentException("You should call registerViewHolder() first!")
    var layoutId = clazz.getAnnotation(LayoutId::class.java)?.value
    if (layoutId == null || layoutId == 0) {
      val layoutName = clazz.getAnnotation(LayoutName::class.java)?.value
      if (!layoutName.isNullOrEmpty()) {
        layoutId = parent.resources.getIdentifier(layoutName, "layout", parent.context.packageName)
      }
    }

    require(!(layoutId == null || layoutId == 0)) { clazz.simpleName + " must be has @LayoutId or @LayoutName annotation" }

    try {
      val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
      return clazz.getConstructor(View::class.java).newInstance(itemView) as DataViewHolder<IQuickProvider>
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  override fun onBindViewHolder(holder: DataViewHolder<IQuickProvider>, position: Int) {
    holder.clickListener = clickListener
    holder.longClickListener = longClickListener
    holder.checkedListener = checkedListener
    holder.dataSize = data.size
    holder.updateViewByData(data[position])
  }

  /**
   * Register ViewHolder by dataClass, data is exclusive.
   *
   * @param dataClazz data Class
   * @param clazz     ViewHolder Class
   */
  fun registerViewHolder(dataClazz: Class<*>, clazz: Class<out DataViewHolder<*>>) {
    viewHolder.put(dataClazz.name.hashCode(), clazz)
  }

  /**
   * Register a callback to be invoked when this view is clicked. If this view is not
   * clickable, it becomes clickable.
   *
   * @param clickListener The callback that will run
   *
   * @see #setClickable(boolean)
   */
  fun setOnClickListener(clickListener: View.OnClickListener) {
    this.clickListener = clickListener
  }

  fun setOnCheckedChangeListener(checkedListener: CompoundButton.OnCheckedChangeListener) {
    this.checkedListener = checkedListener
  }

  fun setOnLongClickListener(longClickListener: View.OnLongClickListener) {
    this.longClickListener = longClickListener
  }

  open fun updateData(data: List<IQuickProvider>) {
    this.data.clear()
    if (null != data) {
      this.data.addAll(data)
    }
    notifyDataSetChanged()
  }
}
