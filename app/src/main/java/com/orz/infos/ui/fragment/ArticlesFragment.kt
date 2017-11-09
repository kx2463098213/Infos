package com.orz.infos.ui.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.widget.TextView
import com.google.gson.Gson
import com.orz.infos.R
import com.orz.infos.mvp.bean.ArticleBean
import com.orz.infos.mvp.bean.ArticleResult
import com.orz.infos.mvp.bean.ArticleTypeBean
import com.orz.infos.mvp.presenter.IArticlesPresenter
import com.orz.infos.mvp.presenter.impl.ArticlesPresenter
import com.orz.infos.mvp.view.IArticlesView
import com.orz.infos.ui.activity.ArticleDetailActivity
import com.orz.infos.ui.adapter.ArticlesAdapter
import com.orz.infos.util.*
import kotlinx.android.synthetic.main.fragment_articles.*
import kotlinx.android.synthetic.main.fragment_articles.view.*

/**
 * Created by Administrator on 2017/10/20.
 */
class ArticlesFragment : Fragment(), IArticlesView, ArticlesAdapter.OnArticleItemClickListener
    ,SwipeRefreshLayout.OnRefreshListener, OnThemeChangeListener{

    private val pageCount = 10

    private lateinit var mSwipeRefreshView: SwipeRefreshLayout
    private lateinit var mRefreshHitText: TextView
    private var currentPage = 1
    private var lastVisibleItem = 0
    private lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var mContext: Context
    private lateinit var mType: ArticleTypeBean
    private lateinit var mPresenter: IArticlesPresenter<IArticlesView>
    private lateinit var mAdapter: ArticlesAdapter
    private var isRefresh = false
    private var mHitHeight: Int = 0

    override fun onAttach(context: Context?) {
//        LogUtil.e("onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        LogUtil.e("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        LogUtil.e("onCreateView")
        initVar()
        var view: View = inflater!!.inflate(R.layout.fragment_articles, container, false)
        initView(view)
        ThemeManager.registerThemeChangeListener(this)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        LogUtil.e("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
//        LogUtil.e("onStart")
        super.onStart()
    }

    override fun onResume() {
//        LogUtil.e("onResume")
        super.onResume()
    }

    override fun onPause() {
//        LogUtil.e("onPause")
        super.onPause()
    }

    override fun onStop() {
//        LogUtil.e("onStop")
        super.onStop()
    }

    override fun onDestroyView() {
//        LogUtil.e("onDestroyView")
        super.onDestroyView()
        ThemeManager.unRegisterThemeChangeListener(this)
    }

    override fun onDetach() {
//        LogUtil.e("onDetach")
        super.onDetach()
    }


    override fun onChange() {
        initTheme()
        content_fragment.setBackgroundColor(ContextCompat.getColor(mContext, ThemeManager.getCurrentThemeRes(mContext, R.color.colorItemBg)))
    }

    private fun initTheme(){

        mAdapter.initTheme()
    }

    private fun initVar(){
        mContext = context
        mHitHeight = context.dip2px(30)
        var bundle: Bundle = arguments
        var typeData: String = bundle!!.getString("type")
        mType = Gson().fromJson(typeData, ArticleTypeBean::class.java)
        mPresenter = ArticlesPresenter(this)
        mAdapter = ArticlesAdapter(mContext, this)
    }

    private fun initView(view: View){
        mLinearLayoutManager = LinearLayoutManager(mContext)
        view.article_list.layoutManager = mLinearLayoutManager
        view.article_list.adapter = mAdapter
        view.article_list.addOnScrollListener(RecyclerViewOnScrollListener())
        mSwipeRefreshView = view.layout_swipe_refresh
        mRefreshHitText = view.text_refresh_hint
        mSwipeRefreshView.setOnRefreshListener(this)
        mSwipeRefreshView.isRefreshing = true
        onRefresh()
    }

    override fun onRefresh() {
        if (!NetWorkUtil.isNetWorkAvailable(mContext)){
            mContext.toast("网络不可用")
        }
        isRefresh = true
        mPresenter.getArticlesByCid(mType!!.cid, currentPage ,pageCount)
    }

    override fun getArticlesFailure(msg: String) {
        mSwipeRefreshView.isRefreshing = false
        mContext.toast(msg)
        isRefresh = false
        mAdapter.changeLoadMoreState(ArticlesAdapter.STATE_NO_MORE_DATA)
    }

    override fun onItemClick(bean: ArticleBean) {
        if (bean != null){
            var intent = Intent(mContext, ArticleDetailActivity::class.java)
            intent.putExtra("article", Gson().toJson(bean))
            mContext.startActivity(intent)

        }
    }

    override fun getArticlesSuccess(articleResultBean: ArticleResult) {
        if (isRefresh){
            mAdapter.addRefreshData(articleResultBean.articles)
            mSwipeRefreshView.isRefreshing = false
            isRefresh = false
            if(currentPage > 1){
                mRefreshHitText.height = mHitHeight
                mRefreshHitText.visibility = View.VISIBLE
                mRefreshHitText.text = "更新了"+articleResultBean.articles.size + "条数据"
                mRefreshHitText.postDelayed({
                    hideRefreshHitText(mRefreshHitText)
                },800)
            }
        }else{
            mAdapter.addMoreData(articleResultBean.articles)
            mAdapter.changeLoadMoreState(ArticlesAdapter.STATE_PULL_UP_TO_LOAD_MORE)
        }
        currentPage++
    }


    private fun hideRefreshHitText(textView: TextView){
        // TextView 竟然用ObjectAnimator改变高度的时候，xml布局里面的高度要是wrap_content...
        var animator = ObjectAnimator.ofInt(textView, "height", mHitHeight,0)
        /*animator.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                textView.visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })*/
//        animator.repeatMode = ValueAnimator.RESTART
//        animator.repeatCount = -1

//        var animator2 = ObjectAnimator.ofFloat(textView, "rotation", 0f, 359f)
//        animator2.repeatMode = ValueAnimator.RESTART
//        animator2.repeatCount = -1

//        var set = AnimatorSet()
//        set.duration = 2000
//        set.play(animator).with(animator2)
        animator.duration = 800
        animator.start()
//        set.start()
    }

    inner class RecyclerViewOnScrollListener: RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.itemCount){
                LogUtil.d("load more data")
                mAdapter.changeLoadMoreState(ArticlesAdapter.STATE_LOADING_DATA)
                mPresenter.getArticlesByCid(mType.cid, currentPage, pageCount)
            }
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition()
        }
    }

}