package com.orz.infos.mvp.presenter

import com.orz.infos.mvp.view.base.IBaseView

/**
 * Created by Administrator on 2017/10/23.
 */
interface IArticlesPresenter<T: IBaseView>{
    /**
     * 根据分类获取文章列表
     */
    fun getArticlesByCid(type: String, page: Int, count: Int)
}