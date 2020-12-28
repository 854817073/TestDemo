package com.test.demo.ui.activity

import com.test.demo.base.BaseActivity
import com.test.demo.bean.TestBean
import com.test.demo.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override fun initData() {
        val data: TestBean = intent.getSerializableExtra("data") as TestBean
        data?.let {
            binding.tvTime.text = it.time
            binding.tvContent.text = it.content
        }

    }

    override fun onclickListener() {
        binding.tvTitle.setOnClickListener { finish() }
    }

}