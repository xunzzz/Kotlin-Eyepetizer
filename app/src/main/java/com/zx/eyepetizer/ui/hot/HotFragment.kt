package com.zx.eyepetizer.ui.hot

import com.zx.eyepetizer.R
import com.zx.eyepetizer.base.BaseFragment
import com.zx.eyepetizer.base.tabsId

/**
 * Created by admin on 2017/10/30.
 */
class HotFragment : BaseFragment(tabsId[2]) {
    override fun getLayoutResources(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
    }
}