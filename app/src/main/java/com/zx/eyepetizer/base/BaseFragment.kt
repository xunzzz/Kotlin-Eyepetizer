package com.zx.eyepetizer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zx.eyepetizer.R
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * Created by admin on 2017/10/30.
 */

var currentFragment = R.id.rb_home
val tabsId = listOf(R.id.rb_home, R.id.rb_find, R.id.rb_hot, R.id.rb_mine)
abstract class BaseFragment(tabId: Int): Fragment() {

    var tabId = 0
    init {
        this.tabId = tabId
    }

    var isFirst: Boolean = false
    var rootView: View? = null
    var isFragmentVisiable: Boolean = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView){
            rootView = inflater?.inflate(getLayoutResources(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun getLayoutResources(): Int

    abstract fun initView()

    open fun setupToolbar(): Boolean{
        if (tabId != currentFragment){
            return true
        }
        return false
    }

}