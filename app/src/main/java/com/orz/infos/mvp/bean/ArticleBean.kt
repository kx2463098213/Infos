package com.orz.infos.mvp.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2017/10/20.
 */
/**
 * 文章类型的数据类
 */
data class ArticleTypeBean(val cid: String,
                           val name: String)

/**
 * 文章请求结果的数据类
 */
data class ArticleResult(val curPage: Int,
                         val Total: Int,
                         @SerializedName("list") val articles: ArrayList<ArticleBean>)

/**
 * 文章的数据类
 */
data class ArticleBean(val id: String,
                       val cid: String,
                       val title: String,
                       val subTitle: String,
                       val sourceUrl: String,
                       val hitCount: String,
                       val pubTime: String,
                       val thumbnails: String)