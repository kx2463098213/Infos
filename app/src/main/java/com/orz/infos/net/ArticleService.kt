package com.orz.infos.net

import com.orz.infos.mvp.bean.ArticleResult
import com.orz.infos.mvp.bean.ArticleTypeBean
import com.orz.infos.mvp.bean.HttpResultBean
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Administrator on 2017/10/20.
 */
interface ArticleService{

    /**
     * 获取文章类型
     */
    @GET("wx/article/category/query")
    fun getArticleType(@Query("key") userKey: String): Observable<HttpResultBean<List<ArticleTypeBean>>>

    /**
     * 根据类型获取文章列表
     */
    @GET("wx/article/search")
    fun getArticlesByType(@Query("key") userKey: String, @Query("cid") cid: String, @Query("page") page: Int, @Query("size") count: Int): Observable<HttpResultBean<ArticleResult>>

}