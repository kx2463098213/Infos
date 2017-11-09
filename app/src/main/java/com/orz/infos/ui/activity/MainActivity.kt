package com.orz.infos.ui.activity

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate.*
import android.view.View
import com.google.gson.Gson
import com.orz.infos.Constant
import com.orz.infos.R
import com.orz.infos.mvp.bean.ArticleTypeBean
import com.orz.infos.ui.fragment.ArticlesFragment
import com.orz.infos.util.OnThemeChangeListener
import com.orz.infos.util.ThemeManager
import com.orz.infos.util.skipTo
import com.orz.infos.util.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    var preBackClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        initVar()
        initView()
    }

    private fun initVar(){
        var fragment = ArticlesFragment()
        var bundle = Bundle()
        var articleType = ArticleTypeBean("1", "时尚")
        bundle.putString("type", Gson().toJson(articleType))
        fragment.arguments = bundle
        supportFragmentManager
                .beginTransaction()
                .add(R.id.layout_content, fragment, articleType.cid)
                .commit()

    }

    private fun initView(){
        fab.setOnClickListener(this)
        fab_2.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - preBackClickTime > 1000){
            toast("再按一次退出")
            preBackClickTime = System.currentTimeMillis()
        }else{
            super.onBackPressed()
        }
    }

    override fun onClick(p0: View?) {
        var id = p0!!.id
        when (id) {
            R.id.fab -> {
                switchDayLightModel()
            }
            R.id.fab_2 ->{
                skipTo(Main2Activity::class.java)
            }
        }
    }

    private fun switchDayLightModel(){
        var mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (mode == Configuration.UI_MODE_NIGHT_NO){
            setDefaultNightMode(MODE_NIGHT_YES)
        }else{
            setDefaultNightMode(MODE_NIGHT_NO)
        }
        recreate()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
