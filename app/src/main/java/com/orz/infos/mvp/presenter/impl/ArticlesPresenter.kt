package com.orz.infos.mvp.presenter.impl

import com.orz.infos.Constant
import com.orz.infos.mvp.bean.ArticleResult
import com.orz.infos.mvp.presenter.IArticlesPresenter
import com.orz.infos.mvp.view.IArticlesView
import com.orz.infos.net.ArticleService
import com.orz.infos.net.HttpResultFunc
import com.orz.infos.util.LogUtil
import com.orz.infos.util.RetrofitUtil
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Administrator on 2017/10/23.
 */
class ArticlesPresenter(view: IArticlesView): IArticlesPresenter<IArticlesView>{

    private var mView: IArticlesView = view

    override fun getArticlesByCid(type: String, page: Int, count: Int) {
        RetrofitUtil.getInstance().getService(Constant.BASE_REQUEST_URL, ArticleService::class.java)
                .getArticlesByType(Constant.MOB_USER_KEY, type, page, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(HttpResultFunc<ArticleResult>())
                .subscribe(object: Subscriber<ArticleResult>(){
                    override fun onNext(t: ArticleResult?) {
                        if (mView != null){
                            mView.getArticlesSuccess(t!!)
                        }
                    }

                    override fun onCompleted() {
                        LogUtil.d("onCompleted")
                    }

                    override fun onError(e: Throwable?) {
                        if (mView != null){
                            mView.getArticlesFailure(e!!.localizedMessage)
                        }
                        LogUtil.e("onError:" + e!!.localizedMessage)
                    }
                })
    }

}