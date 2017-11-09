package com.orz.infos.util

import android.util.Log
import com.orz.infos.Constant

/**
 * Created by Administrator on 2017/10/20.
 */
object LogUtil{
    private val TAG = "INFOS"

    fun i(msg: String) {
        if (Constant.isDebugModel) {
            Log.i(TAG, msg)
        }
    }

    fun d(msg: String) {
        if (Constant.isDebugModel) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    fun v(msg: String) {
        if (Constant.isDebugModel) {
            Log.v(TAG, msg)
        }
    }

    fun w(msg: String) {
        Log.w(TAG, msg)
    }
}