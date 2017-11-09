package com.orz.infos.mvp.view

import com.orz.infos.mvp.bean.ArticleResult
import com.orz.infos.mvp.view.base.IBaseView

/**
 * Created by Administrator on 2017/10/23.
 */
interface IArticlesView: IBaseView{

    fun getArticlesSuccess(articleResultBean: ArticleResult)

    fun getArticlesFailure(msg: String)
}