package com.orz.infos.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by Administrator on 2017/10/20.
 */
fun Context.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun <T> Context.skipTo(clz: Class<T>){
    var intent = Intent(this, clz)
    startActivity(intent)
}

fun Context.dip2px(value: Int): Int{
    var scale:Float = this.resources.displayMetrics.density
    return (value * scale + 0.5).toInt()
}

fun Context.px2dip(value: Int): Int{
    var scale: Float = this.resources.displayMetrics.density
    return (value / scale + 0.5).toInt()
}

fun Context.isNetWorkvailable(): Boolean{
    var cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (cm != null){
        var info = cm.activeNetworkInfo
       if (info != null){
           return info.isConnected
       }
        return false
    }
    return false
}
