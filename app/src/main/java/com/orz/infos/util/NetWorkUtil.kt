package com.orz.infos.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Administrator on 2017/10/20.
 */
object NetWorkUtil{
    fun isNetWorkAvailable(context: Context): Boolean{
        var cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cm != null){
            var info: NetworkInfo? = cm.activeNetworkInfo
            if (info != null && info.isConnected){
                return true
            }
        }
        return false
    }
}