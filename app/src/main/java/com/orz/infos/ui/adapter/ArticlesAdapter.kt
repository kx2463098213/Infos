package com.orz.infos.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orz.infos.R
import com.orz.infos.mvp.bean.ArticleBean
import com.orz.infos.util.GlideApp
import com.orz.infos.util.LogUtil
import com.orz.infos.util.ThemeManager
import kotlinx.android.synthetic.main.article_item.view.*
import kotlinx.android.synthetic.main.load_more_footview.view.*

/**
 * Created by Administrator on 2017/10/23.
 */
class ArticlesAdapter(context: Context, listener: OnArticleItemClickListener): Adapter<RecyclerView.ViewHolder>(){

    private val TYPE_ITEM_ARTICLE = 0
    private val TYPE_ITEM_FOOT_VIEW = 1

    companion object {
        val STATE_PULL_UP_TO_LOAD_MORE = 0
        val STATE_LOADING_DATA = 1
        val STATE_NO_MORE_DATA = 2
    }

    private var mContext: Context = context
    private var mData: ArrayList<ArticleBean> = ArrayList()
    private var mListener = listener
    private var mLoadMoreState = STATE_PULL_UP_TO_LOAD_MORE


    fun setData(list: ArrayList<ArticleBean>){
        mData = list
        notifyDataSetChanged()
    }

    fun addRefreshData(list: ArrayList<ArticleBean>){
        mData.addAll(0, list)
        notifyDataSetChanged()
    }

    fun addMoreData(list: ArrayList<ArticleBean>){
        mData.addAll(list)
        notifyDataSetChanged()
    }

    fun changeLoadMoreState(newState: Int){
        this.mLoadMoreState = newState
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder
        if (viewType == TYPE_ITEM_ARTICLE){
            var view: View = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false)
            viewHolder = ArticleItemHolder(view)
        }else{
            var view: View = LayoutInflater.from(mContext).inflate(R.layout.load_more_footview, parent, false)
            viewHolder = FootViewHolder(view)
        }
        return  viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder == null){
            LogUtil.e("ViewHolder is null")
            return
        }
        if (holder is FootViewHolder){
            val footViewHolder = holder
            if (mLoadMoreState == STATE_NO_MORE_DATA) {
                footViewHolder.tipText.text = "抱歉，没有啦"
            } else if (mLoadMoreState == STATE_PULL_UP_TO_LOAD_MORE) {
                footViewHolder.tipText.text = "松开手指加载更多数据..."
            } else if (mLoadMoreState == STATE_LOADING_DATA) {
                footViewHolder.tipText.text = "正在加载数据..."
            }
            footViewHolder.tipText.setBackgroundColor(ContextCompat.getColor(mContext, ThemeManager.getCurrentThemeRes(mContext, R.color.colorItemBg)))
            footViewHolder.tipText.setTextColor(ContextCompat.getColor(mContext, ThemeManager.getCurrentThemeRes(mContext, R.color.colorArticleTitle)))
        }else {
            if (holder is ArticleItemHolder){
                var bean = mData[position]
                holder.itemView.setOnClickListener(View.OnClickListener {
                    if (mListener != null){
                        mListener.onItemClick(bean)
                    }
                })
                val articleHolder: ArticleItemHolder = holder
                articleHolder.content.setBackgroundColor(ContextCompat.getColor(mContext, ThemeManager.getCurrentThemeRes(mContext, R.color.colorItemBg)))
                articleHolder.textTitle.text = bean.title
                articleHolder.textTitle.setTextColor(ContextCompat.getColor(mContext, ThemeManager.getCurrentThemeRes(mContext, R.color.colorArticleTitle)))
                articleHolder.textSbuTitle.text = bean.subTitle
                articleHolder.textHitCount.text = "点击次数 " + bean.hitCount
                articleHolder.textPubTime.text = bean.pubTime
                GlideApp.with(mContext)
                        .load(bean.thumbnails)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(articleHolder.thumbnailImage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount){
            return TYPE_ITEM_FOOT_VIEW
        }
        return TYPE_ITEM_ARTICLE
    }

    override fun getItemCount(): Int {
        var count = if (mData.isEmpty()) 0
        else mData.size + 1
        return count
    }

    class ArticleItemHolder(view: View): RecyclerView.ViewHolder(view) {
        var thumbnailImage = view.image_thumbnail!!
        var textTitle = view.text_title!!
        var textSbuTitle = view.text_subtitle!!
        var textPubTime = view.text_pub_time!!
        var textHitCount = view.text_hit_count!!
        var content =  view

    }

    class FootViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tipText = view.text_footView!!
    }

    interface OnArticleItemClickListener{
        fun onItemClick(bean: ArticleBean)
    }

    fun initTheme() {
        notifyDataSetChanged()
    }

}