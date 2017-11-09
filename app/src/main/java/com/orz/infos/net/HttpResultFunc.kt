package com.orz.infos.net

import com.orz.infos.App
import com.orz.infos.mvp.bean.HttpResultBean
import com.orz.infos.util.LogUtil
import com.orz.infos.util.NetWorkUtil
import rx.functions.Func1

/**
 * Created by Administrator on 2017/10/20.
 */
class HttpResultFunc<T>: Func1<HttpResultBean<T>, T>{

    override fun call(t: HttpResultBean<T>?): T {
        LogUtil.d("resultCode:" + t!!.retCode)
        if ("200".equals(t.retCode)){
            return t.data
        }else if("504".equals(t.retCode) && !NetWorkUtil.isNetWorkAvailable(App.context)){
            throw HttpErrorException("网络异常")
        }else{
            throw HttpErrorException(t.msg)
        }
    }

    class HttpErrorException(message: String?) : RuntimeException(message)

}
