package com.zx.eyepetizer.ui.home

import android.content.Context
import com.zx.eyepetizer.entity.HomeBean
import com.zx.eyepetizer.net.ApiService
import com.zx.eyepetizer.net.RetrofitClient
import io.reactivex.Observable

/**
 * Created by admin on 2017/10/30.
 */
class HomeModel {
    fun loadData(context: Context, isFirst: Boolean, date: String?): Observable<HomeBean>?{
        val client = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = client.create(ApiService::class.java)
        when(isFirst){
            true -> return apiService?.getHomeData()

            false -> return apiService?.getHomeMoreData(date.toString(), "2")
        }


    }
}