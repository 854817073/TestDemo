package com.test.demo.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-26
 * 描述：
 */
interface HttpService {
    @GET("/")
    fun post(): Call<ResponseBody>
}