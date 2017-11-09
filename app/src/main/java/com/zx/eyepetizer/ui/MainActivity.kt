package com.zx.eyepetizer.ui

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.zx.eyepetizer.R
import com.zx.eyepetizer.base.BaseFragment
import com.zx.eyepetizer.base.currentFragment
import com.zx.eyepetizer.base.tabsId
import com.zx.eyepetizer.ui.find.FindFragment
import com.zx.eyepetizer.ui.home.HomeFragment
import com.zx.eyepetizer.ui.hot.HotFragment
import com.zx.eyepetizer.ui.mine.MineFragment
import com.zx.eyepetizer.ui.search.SEARCH_TAG
import com.zx.eyepetizer.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {
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
    }

    private fun setRadioButton() {
        rb_home.isChecked = true
        chooseFragment(R.id.rb_home)
        rg_root.setOnCheckedChangeListener { _, checkedId -> chooseFragment(checkedId) }

    }

    private fun chooseFragment(checkedId: Int) {
        currentFragment = checkedId
        val beginTransaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(checkedId.toString())
        if (null == fragment){
            when(checkedId){
                R.id.rb_home -> beginTransaction.add(R.id.fl_content, HomeFragment(), checkedId.toString())
                R.id.rb_find -> beginTransaction.add(R.id.fl_content, FindFragment(), checkedId.toString())
                R.id.rb_hot -> beginTransaction.add(R.id.fl_content, HotFragment(), checkedId.toString())
                R.id.rb_mine -> beginTransaction.add(R.id.fl_content, MineFragment(), checkedId.toString())
            }
        }

        tabsId.forEach { tab ->
            val aFragment = supportFragmentManager.findFragmentByTag(tab.toString()) as BaseFragment?

            if (tab == checkedId){
                aFragment?.let {
                    aFragment.setupToolbar()
                    beginTransaction.show(it)
                }
            }else{
                aFragment?.let {
                    beginTransaction.hide(it)
                }
            }
        }

        beginTransaction.commit()

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

}
