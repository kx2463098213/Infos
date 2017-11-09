package com.orz.infos.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.google.gson.Gson
import com.orz.infos.R
import com.orz.infos.mvp.bean.ArticleBean
import kotlinx.android.synthetic.main.activity_article_detail.*

/**
 * Created by Administrator on 2017/10/24.
 */
class ArticleDetailActivity : AppCompatActivity(){

    private lateinit var mContext: Context
    private lateinit var mBean: ArticleBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        initVar()
        initView()
    }


    fun initVar(){
        mContext = this
        var data = intent.getStringExtra("article")
        if (!TextUtils.isEmpty(data)){
            mBean = Gson().fromJson(data, ArticleBean::class.java)
        }
    }

    fun initView(){
        article_detail.loadUrl(mBean.sourceUrl)
    }
}