package com.zx.eyepetizer.ui.home

import android.content.Context
import com.zx.eyepetizer.entity.HomeBean
import com.zx.eyepetizer.utils.applySchedulers
import io.reactivex.Observable

/**
 * Created by admin on 2017/10/30.
 */
class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {


    var mContext: Context? = null
    var mView: HomeContract.View? = null
    //懒初始化model,lazy:只会在第一次使用model的时候创建
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mContext = context
        mView = view
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable: Observable<HomeBean>? = mContext?.let { mModel.loadData(it, true, "0") }
        observable?.applySchedulers()
                ?.subscribe { homeBean : HomeBean -> mView?.setData(homeBean) }

    }

    override fun moreData(data: String?) {
        val observable = mContext?.let { mModel.loadData(it, false, data) }
        observable?.applySchedulers()
                ?.subscribe { homeBean : HomeBean -> mView?.setData(homeBean) }

    }
}