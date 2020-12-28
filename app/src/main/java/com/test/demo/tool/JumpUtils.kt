package com.test.demo.tool

import android.app.Activity
import android.content.Intent
import java.io.Serializable

/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-28
 * 描述：
 */
object JumpUtils {
    fun runActivity(
        activity: Activity,
        cls: Class<*>?,
        map: Map<String?, Any>
    ) {
        var intent = Intent(activity, cls)
        for ((key, v) in map) {
            when (v) {
                is String -> {
                    intent.putExtra(key, v)
                }
                is Int -> {
                    intent.putExtra(key, v)
                }
                is Long -> {
                    intent.putExtra(key, v)
                }
                is Boolean -> {
                    intent.putExtra(key, v)
                }
                is Float -> {
                    intent.putExtra(key, v)
                }
                is Double -> {
                    intent.putExtra(key, v)
                }
                is Long -> {
                    intent.putExtra(key, v)
                }
                is ByteArray -> {
                    intent.putExtra(key, v)
                }
                is Serializable ->{
                    intent.putExtra(key,v)
                }
            }
        }
        activity.startActivity(intent)
    }
}