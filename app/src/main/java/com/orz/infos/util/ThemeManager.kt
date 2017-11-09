package com.orz.infos.util

import android.content.Context
import android.support.annotation.NonNull
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Administrator on 2017/10/27.
 */
object ThemeManager{
    private val RESOURCE_NIGHT_SUFFIX = "_night"

    private var currentThem = ThemeMode.DAY
    private var mListenerList = LinkedList<OnThemeChangeListener>()
    private var mResourceMap = HashMap<String, HashMap<String, Int>>()

    fun setThemeMode(@NonNull mode: ThemeMode){
        if(currentThem != mode){
            currentThem = mode
        }
        for (listener in mListenerList){
            listener.onChange()
        }
    }

    fun getCurrentThemeRes(context: Context, resId: Int):Int{
        if (currentThem == ThemeMode.DAY){
            return resId
        }
        var entryName = context.resources.getResourceEntryName(resId)
        var typeName = context.resources.getResourceTypeName(resId)
        var cacheRes = mResourceMap[typeName]
        if (cacheRes == null){
            cacheRes = HashMap<String, Int>()
        }
        var tempResId = cacheRes[entryName + RESOURCE_NIGHT_SUFFIX]
        if (tempResId != null && tempResId != 0){
            return tempResId
        }else{
            var nightResId = context.resources.getIdentifier(entryName + RESOURCE_NIGHT_SUFFIX, typeName, context.packageName)
            cacheRes.put(entryName + RESOURCE_NIGHT_SUFFIX, nightResId)
            mResourceMap.put(typeName, cacheRes)
            return nightResId
        }
    }


    fun registerThemeChangeListener(listener: OnThemeChangeListener){
        if (!mListenerList.contains(listener)){
            mListenerList.add(listener)
        }
    }

    fun unRegisterThemeChangeListener(listener: OnThemeChangeListener){
        if (mListenerList.contains(listener)){
            mListenerList.remove(listener)
        }
    }

    fun getThemeMode():ThemeMode{
        return currentThem
    }


}

enum class ThemeMode{
    DAY,
    NIGHT
}



interface OnThemeChangeListener{
    fun onChange()
}


