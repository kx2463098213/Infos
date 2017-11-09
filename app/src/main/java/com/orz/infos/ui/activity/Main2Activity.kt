package com.orz.infos.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.orz.infos.R
import com.orz.infos.util.OnThemeChangeListener
import com.orz.infos.util.ThemeManager
import com.orz.infos.util.ThemeMode
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(), OnThemeChangeListener {
    private var themeId = R.style.AppTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        if(savedInstanceState != null){
            themeId = savedInstanceState.getInt("theme")
            setTheme(themeId)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initView()
    }

    private fun initView(){
        ThemeManager.registerThemeChangeListener(this)
        fab_m.setOnClickListener({
            /*if (ThemeManager.getThemeMode() == ThemeMode.DAY){
                ThemeManager.setThemeMode(ThemeMode.NIGHT)
            }else{
                ThemeManager.setThemeMode(ThemeMode.DAY)
            }*/
            themeId = if(themeId == R.style.AppTheme){
                R.style.AppTheme_Night
            }else{
                R.style.AppTheme
            }
            recreate()
        })
//        initThemeMode()
    }

    override fun onDestroy() {
        super.onDestroy()
        ThemeManager.unRegisterThemeChangeListener(this)
    }

    override fun onChange() {
        initThemeMode()
    }

    private fun initThemeMode(){
        text_content.setBackgroundColor(ContextCompat.getColor(this,ThemeManager.getCurrentThemeRes(this, R.color.colorItemBg)))
        text_content.setTextColor(ContextCompat.getColor(this,ThemeManager.getCurrentThemeRes(this, R.color.colorArticleTitle)))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt("theme", themeId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        themeId = savedInstanceState!!.getInt("theme")
    }

}
