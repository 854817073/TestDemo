package com.test.demo.ui.activity

import android.content.SharedPreferences
import android.widget.TextView
import com.google.gson.Gson
import com.test.demo.R
import com.test.demo.base.BaseActivity
import com.test.demo.bean.TestBean
import com.test.demo.bean.TestBeanList
import com.test.demo.databinding.ActivityHistoryBinding
import com.test.demo.widget.BaseRecyclerAdapter
import com.test.demo.widget.SimpleViewHolder

/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-25
 * 描述：历史记录页面
 */
class HistoryActivity : BaseActivity<ActivityHistoryBinding>() {
    private lateinit var settings: SharedPreferences
    private val fileName = "file"
    private val dataHistory = "list"
    private lateinit var mDataHistory: TestBeanList
    private var map = mutableMapOf<String, Any>()
    override fun initData() {
        settings = getSharedPreferences(fileName, 0)
        val list = settings.getString(dataHistory, "")
        if (list != null) {
            if (list.isNotEmpty()) {
                mDataHistory = Gson().fromJson(list, TestBeanList::class.java)
                val adapter = BaseRecyclerAdapter(this, mDataHistory.list, R.layout.adapter_item)
                adapter.inter = object : BaseRecyclerAdapter.Convert<TestBean> {
                    override fun convert(holder: SimpleViewHolder, data: TestBean, position: Int) {
                        var textTime: TextView = holder.getView(R.id.tv_time)
                        var tvContent: TextView = holder.getView(R.id.tv_content)
                        textTime.text = data.time
                        tvContent.text = data.content
                        holder.itemView.setOnClickListener {
                            map.clear()
                            map["data"] = data
                            launchActivity(DetailActivity::class.java, map)
                        }
                    }
                }
                adapter.setRecycleView(binding.recyclerView, 1)
            }
        }
    }

    override fun onclickListener() {
        binding.tvTitle.setOnClickListener { finish() }
    }
}