package com.test.demo.widget

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.demo.R

/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-26
 * 描述：
 */
class BaseRecyclerAdapter<T>(
    private var mContext: Context,
    private var data: MutableList<T>,
    private var mResource: Int
) : RecyclerView.Adapter<SimpleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder.createViewHolder(mContext, parent, mResource)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        inter.convert(holder, data[position], position)
    }

    fun setRecycleView(rvList: RecyclerView, lineHeight: Int) {
        setRecycleView(rvList, lineHeight, R.color.mainTextColor)
    }

    private fun setRecycleView(rvList: RecyclerView, lineHeight: Int, lineColor: Int) {
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.itemAnimator = DefaultItemAnimator()
        if (lineHeight > 0 && rvList.itemDecorationCount == 0) {
            val divider = DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ContextCompat.getDrawable(mContext, lineColor)!!)
            rvList.addItemDecoration(divider)
        }
        rvList.adapter = this
    }

    lateinit var inter: Convert<T>

    interface Convert<T> {
        fun convert(holder: SimpleViewHolder, data: T, position: Int)
    }
}