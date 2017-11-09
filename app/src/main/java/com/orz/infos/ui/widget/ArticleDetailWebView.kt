package com.orz.infos.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

/**
 * Created by Administrator on 2017/10/24.
 */
class ArticleDetailWebView: WebView{

    constructor(context: Context): super(context){
        initSetting()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        initSetting()
    }

    constructor(context: Context, attrs: AttributeSet, defStylAttr: Int): super(context, attrs, defStylAttr){
        initSetting()
    }

    fun initSetting(){
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.builtInZoomControls = false
        settings.lightTouchEnabled = false
        settings.loadsImagesAutomatically = true
        settings.setSupportZoom(false)
        settings.useWideViewPort = false

        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false

        setBackgroundColor(Color.TRANSPARENT)
        scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
    }
}