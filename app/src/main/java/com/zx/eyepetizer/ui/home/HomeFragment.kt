package com.zx.eyepetizer.ui.home

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.zx.eyepetizer.R
import com.zx.eyepetizer.base.BaseFragment
import com.zx.eyepetizer.base.tabsId
import com.zx.eyepetizer.entity.HomeBean
import com.zx.eyepetizer.entity.HomeBean.IssueListBean.ItemListBean
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList
import java.util.regex.Pattern


/**
 * Created by admin on 2017/10/30.
 */
class HomeFragment : BaseFragment(tabsId[0]), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    var mPresenter: HomeContract.Presenter? = null
    var mAdapter: HomeAdatper? = null
    var mList = ArrayList<ItemListBean>()
    var mIsRefresh: Boolean = false
    var date: String? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter = HomePresenter(context, this)
        mPresenter?.start()
        mAdapter = HomeAdatper(context, mList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mList.size - 1){
                    if (date != null){
                        mPresenter?.moreData(date)
                    }
                }

            }
        })
        refreshLayout.setOnRefreshListener(this)

    }

    override fun setData(bean: HomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean?.nextPageUrl)
        date = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh){
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0){
                mList.clear()
            }
        }

        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mList.add(it) }
        mAdapter?.notifyDataSetChanged()

    }

    override fun onRefresh() {
        if (!mIsRefresh){
            mIsRefresh = true
            mPresenter?.start()
        }
    }
}