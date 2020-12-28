package com.test.demo.ui.activity

import android.content.SharedPreferences
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.test.demo.R
import com.test.demo.base.BaseActivity
import com.test.demo.bean.TestBean
import com.test.demo.bean.TestBeanList
import com.test.demo.databinding.ActivityMainBinding
import com.test.demo.http.HttpService
import com.test.demo.widget.BaseRecyclerAdapter
import com.test.demo.widget.SimpleViewHolder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var build: Retrofit
    private lateinit var httpService: HttpService
    private val url: String = "https://api.github.com/"
    private val handler = Handler()
    private val time: Long = 5000
    private val mData = mutableListOf<TestBean>()
    private var mDataHistory = TestBeanList()
    private val adapter = BaseRecyclerAdapter(this, mData, R.layout.adapter_item)
    private lateinit var settings: SharedPreferences
    private val fileName = "file"
    private val lastDataName = "last"
    private val dataHistoryName = "list"
    private var fromJson: TestBean? = null
    private var map = mutableMapOf<String, Any>()

    companion object {
        private var runnable: Runnable? = null
    }

    override fun onResume() {
        super.onResume()
        runnable?.let { handler.postDelayed(it, time) }
        binding.btnStopRefresh.text = "停止刷新"
    }

    override fun initData() {
        settings = getSharedPreferences(fileName, 0)
        val lastData = settings.getString(lastDataName, "")
        val list = settings.getString(dataHistoryName, "")
        if (lastData != null) {
            if (lastData.isNotEmpty()) {
                binding.group.visibility = View.VISIBLE
                fromJson = Gson().fromJson(lastData, TestBean::class.java)
                binding.tvTime.text = fromJson?.time
                binding.tvContent.text = fromJson?.content
            }
        }
        if (list != null) {
            if (list.isNotEmpty()) {
                mDataHistory = Gson().fromJson(list, TestBeanList::class.java)
            }
        }
        build = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        httpService = build.create(HttpService::class.java)
        binding.swipeRefreshLayout.isEnabled = false
        initRunnable()
        initRecyclerView()
    }

    private fun initRecyclerView() {
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

    private fun initRunnable() {
        runnable = Runnable {
            binding.swipeRefreshLayout.isRefreshing = true
            post()
        }
        handler.postDelayed(runnable!!, time)
    }

    private fun post() {
        var call: Call<ResponseBody> = httpService.post()
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                runnable?.let { handler.postDelayed(it, time) }
                binding.swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@MainActivity, "刷新失败", Toast.LENGTH_SHORT)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var test = TestBean()
                test.time =
                    "时间：" + SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))
                if (response.body() != null) {
                    response.body()?.let { test.content = "返回结果：${it.string()}" }
                } else {
                    test.content = "返回结果：${response}"
                }
                mData.add(test)
                adapter.notifyDataSetChanged()
                binding.swipeRefreshLayout.isRefreshing = false
                val editor = settings.edit()
                mDataHistory.list.add(test)
                editor.putString(dataHistoryName, Gson().toJson(mDataHistory))
                editor.commit()
                runnable?.let { handler.postDelayed(it, time) }
            }
        })
    }

    override fun onclickListener() {
        binding.btnStopRefresh.setOnClickListener {
            if ("停止刷新" == binding.btnStopRefresh.text) {
                runnable?.let { it1 -> handler.removeCallbacks(it1) }
                binding.btnStopRefresh.text = "开始刷新"
            } else {
                runnable?.let { it1 -> handler.postDelayed(it1, time) }
                binding.btnStopRefresh.text = "停止刷新"
            }
        }
        binding.tvHistory.setOnClickListener {
            launchActivity(HistoryActivity::class.java)
        }
        binding.imgGo.setOnClickListener {
            fromJson?.let { it1 ->
                map.clear()
                map["data"] = it1
                launchActivity(DetailActivity::class.java, map)
            }
        }
        binding.tvContent.setOnClickListener {
            fromJson?.let { it1 ->
                map.clear()
                map["data"] = it1
                launchActivity(DetailActivity::class.java, map)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mData.isNotEmpty()) {
            val editor = settings.edit()
            editor.putString(lastDataName, mData[0].toString())
            editor.commit()
        }
        runnable?.let { handler.removeCallbacks(it) }
        binding.btnStopRefresh.text = "开始刷新"
    }
}