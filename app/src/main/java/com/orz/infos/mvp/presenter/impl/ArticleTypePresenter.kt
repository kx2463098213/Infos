package com.orz.infos.model.impl

import com.orz.infos.Constant
import com.orz.infos.mvp.bean.ArticleTypeBean
import com.orz.infos.mvp.presenter.IArticleTypePresenter
import com.orz.infos.net.ArticleService
import com.orz.infos.net.HttpResultFunc
import com.orz.infos.util.LogUtil
import com.orz.infos.util.RetrofitUtil
import com.orz.infos.view.IArticleTypesView
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Administrator on 2017/10/23.
 */
class ArticleTypePresenter : IArticleTypePresenter<IArticleTypesView> {

    override fun getArticleTypes() {
        RetrofitUtil.getInstance().getService(Constant.BASE_REQUEST_URL, ArticleService::class.java)
                .getArticleType(Constant.MOB_USER_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(HttpResultFunc<List<ArticleTypeBean>>())
                .subscribe(object: Subscriber<List<ArticleTypeBean>>(){
                    override fun onNext(t: List<ArticleTypeBean>?) {
                        LogUtil.d("size:" + t!!.size)
                    }

                    override fun onCompleted() {
                        LogUtil.d("onCompleted")
                    }

                    override fun onError(e: Throwable?) {
                        LogUtil.e(e!!.localizedMessage)
                    }
                })
    }

}