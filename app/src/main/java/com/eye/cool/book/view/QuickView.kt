package com.eye.cool.book.view

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eye.cool.book.R
import com.eye.cool.book.adapter.RecyclerAdapter
import com.eye.cool.book.params.QuickBarParams
import com.eye.cool.book.params.QuickViewParams
import com.eye.cool.book.support.IQuickProvider
import com.eye.cool.book.support.OnLetterSelectedListener

/**
 * Created by cool on 2018/4/19.
 */
class QuickView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val recyclerView: RecyclerView
  private val quickBar: QuickBar
  private val layoutManager = LinearLayoutManager(context)
  private val adapter = RecyclerAdapter()
  private val quickToastView: TextView

  private var stickyKeys: SparseArray<String>? = null

  @Volatile
  private var params: QuickViewParams? = null

  init {
    val view = LayoutInflater.from(context).inflate(R.layout.book_quick_view, this, true)
    recyclerView = view.findViewById(R.id.quickRecyclerView)
    quickBar = view.findViewById(R.id.quickBar)
    quickToastView = view.findViewById(R.id.quickToastView)

    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = adapter
    recyclerView.setHasFixedSize(true)
  }

  /**
   * Search by keyword
   *
   * @return Returns the result that meets the criteria
   */
  fun search(key: String): List<IQuickProvider> {
    if (key.isNullOrEmpty()) return emptyList()
    val values = params?.dataParams?.data?.values ?: return emptyList()
    val result = arrayListOf<IQuickProvider>()
    values.forEach { list ->
      result.addAll(list.filter {
        it.getSearchKey().contains(key)
      })
    }
    return result
  }

  /**
   * Setup with params to display
   */
  fun setup(params: QuickViewParams) {
    this.params = params
    params.dataParams?.apply {
      viewHolders.forEach {
        adapter.registerViewHolder(it.key, it.value)
      }

      val keys = params.stickyParams.keys
      keys.clear()
      val values = arrayListOf<IQuickProvider>()
      data?.forEach {
        keys.put(values.size, it.key)
        values.addAll(it.value)
      }
      stickyKeys = keys
      recyclerView.addItemDecoration(StickyItemDecoration(context, params.stickyParams))
      val letters = data?.keys?.toTypedArray()
      quickBar.setupQuickBar(letters, getBarParams(params))
      adapter.updateData(values)
    }
  }

  private fun getBarParams(params: QuickViewParams): QuickBarParams {
    val barParams = params.barParams
    val onLetterSelectedListener = barParams.onLetterSelectedListener
    barParams.onLetterSelectedListener = object : OnLetterSelectedListener {
      override fun onSelected(letter: String) {
        onLetterSelectedListener?.onSelected(letter)
        layoutManager.scrollToPositionWithOffset(getLetterPosition(letter), 0)
      }
    }
    if (barParams.toastTextView == null) {
      val toastParams = params.toastParams
      quickToastView.setTextColor(toastParams.textColor)
      quickToastView.textSize = toastParams.textSize
      when {
        toastParams.backgroundColor != null -> {
          quickToastView.setBackgroundColor(toastParams.backgroundColor)
        }
        toastParams.backgroundDrawable != null -> {
          ViewCompat.setBackground(quickToastView, toastParams.backgroundDrawable)
        }
        else -> {
          quickToastView.setBackgroundResource(R.drawable.book_quick_toast_background)
        }
      }
      val lp = quickToastView.layoutParams ?: LayoutParams(-2, -2)
      lp.width = toastParams.viewWidth
      lp.height = toastParams.viewHeight
      quickToastView.layoutParams = lp
      barParams.toastTextView = quickToastView
    }

    return barParams
  }

  private fun getLetterPosition(letter: String): Int {
    val keys = stickyKeys ?: return 0
    val index = keys.indexOfValue(letter)
    return if (index < 0) 0 else keys.keyAt(index)
  }
}