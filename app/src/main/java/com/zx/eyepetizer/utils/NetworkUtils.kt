package com.zx.eyepetizer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telecom.ConnectionService

/**
 * Created by admin on 2017/10/30.
 */
object NetworkUtils {

    fun isNetConneted(context: Context) : Boolean{
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo : NetworkInfo = connectManager.activeNetworkInfo
        if (null == networkInfo){
            return false
        }else{
            return networkInfo.isAvailable && networkInfo.isConnected
        }

    }

}