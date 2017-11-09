package com.orz.infos.mvp.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2017/10/20.
 */
data class HttpResultBean<T>(val retCode: String,
                             val msg: String,
                             @SerializedName("result") val data: T)