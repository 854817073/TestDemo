package com.test.demo.bean

import java.io.Serializable

/**
 * @author This Man
 * 版本：1.0
 * 创建日期：2020-12-26
 * 描述：
 */
class TestBean : Serializable {
    var time = ""
    var content = ""
    override fun toString(): String {
        return "{time:'$time', content:'$content'}"
    }

}