package com.orz.infos.mvp.presenter

import com.orz.infos.mvp.view.base.IBaseView


/**
 * Created by Administrator on 2017/10/23.
 */
interface IArticleTypePresenter<T: IBaseView> {
    /**
     * 获取所有文章的分类
     */
    fun getArticleTypes()

}