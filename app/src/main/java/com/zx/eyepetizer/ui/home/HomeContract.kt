package com.zx.eyepetizer.ui.home

import com.zx.eyepetizer.base.BasePresenter
import com.zx.eyepetizer.base.BaseView
import com.zx.eyepetizer.entity.HomeBean

/**
 * Created by admin on 2017/10/30.
 */
interface HomeContract {

    interface View : BaseView<Presenter>{
        fun setData(bean: HomeBean)
    }

    interface Presenter : BasePresenter{
        fun requestData()
        fun moreData(data: String?)
    }

}