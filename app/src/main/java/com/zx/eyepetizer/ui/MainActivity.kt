package com.zx.eyepetizer.ui

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.zx.eyepetizer.R
import com.zx.eyepetizer.ui.find.FindFragment
import com.zx.eyepetizer.ui.home.HomeFragment
import com.zx.eyepetizer.ui.hot.HotFragment
import com.zx.eyepetizer.ui.mine.MineFragment
import com.zx.eyepetizer.ui.search.SEARCH_TAG
import com.zx.eyepetizer.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragment: HotFragment? = null
    var mineFragment: MineFragment? = null
    var mExitTime: Long = 0

    lateinit var searchFragment: SearchFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //状态栏适配
        ImmersionBar.with(this).transparentBar().barAlpha(0.8f).fitsSystemWindows(true).init()
        //隐藏底部导航栏
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params

        setRadioButton()
        initToolbar()
        initFragment(savedInstanceState)
    }


    private fun initToolbar() {
        var today = getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener {
            if (rb_mine.isChecked){
                //设置按钮

            }else{
                //搜索按钮
                searchFragment = SearchFragment()
                searchFragment.show(fragmentManager, SEARCH_TAG)
            }
        }
    }

    private fun getToday(): String {
        val list = resources.getStringArray(R.array.day_of_week)
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        var index = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0){
            index = 0
        }
        return list[index]
    }

    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        clearState()
        when(v?.id){
            R.id.rb_home -> {
                rb_home.isChecked = true
                rb_home.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(homeFragment)
                        .hide(findFragment)
                        .hide(hotFragment)
                        .hide(mineFragment)
                        .commit()
                titleSet(getToday(), View.VISIBLE)
            }
            R.id.rb_find -> {
                rb_find.isChecked = true
                rb_find.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(findFragment)
                        .hide(homeFragment)
                        .hide(hotFragment)
                        .hide(mineFragment)
                        .commit()
                titleSet("Discover", View.VISIBLE)
            }
            R.id.rb_hot -> {
                rb_hot.isChecked = true
                rb_hot.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(hotFragment)
                        .hide(findFragment)
                        .hide(homeFragment)
                        .hide(mineFragment)
                        .commit()
                titleSet("Ranking", View.VISIBLE)
            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment)
                        .hide(findFragment)
                        .hide(hotFragment)
                        .hide(homeFragment)
                        .commit()
                titleSet("", View.GONE)
            }
        }
    }

    fun titleSet(title: String, visible: Int){
        tv_bar_title.text = title
        tv_bar_title.visibility = visible
        if (View.VISIBLE == visible){
            iv_search.setImageResource(R.drawable.icon_search)
        }else{
            iv_search.setImageResource(R.drawable.icon_setting)
        }
    }

    private fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
        rb_hot.setTextColor(resources.getColor(R.color.gray))
        rb_find.setTextColor(resources.getColor(R.color.gray))
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (null != savedInstanceState){
            val fragments: List<Fragment> = supportFragmentManager.fragments
            for (item in fragments){
                if (item is HomeFragment){
                    homeFragment = item
                }
                if (item is FindFragment){
                    findFragment = item
                }
                if (item is HotFragment){
                    hotFragment = item
                }
                if (item is MineFragment){
                    mineFragment = item
                }
            }
        }else{
            homeFragment = HomeFragment()
            findFragment = FindFragment()
            hotFragment = HotFragment()
            mineFragment = MineFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fl_content, homeFragment)
            transaction.add(R.id.fl_content, findFragment)
            transaction.add(R.id.fl_content, hotFragment)
            transaction.add(R.id.fl_content, mineFragment)
            transaction.commit()
        }

        supportFragmentManager.beginTransaction().show(homeFragment)
                .hide(findFragment)
                .hide(hotFragment)
                .hide(mineFragment)
                .commit()

    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis().minus(mExitTime) <=3000){
                finish()

            }else{
                mExitTime = System.currentTimeMillis()
                toast("再按一次退出程序")
            }
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}
