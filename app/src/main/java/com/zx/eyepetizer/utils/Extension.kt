package com.zx.eyepetizer.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zx.eyepetizer.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by admin on 2017/10/30.
 */


/**
 * 切换线程
 */
fun <T> Observable<T>.applySchedulers(): Observable<T>{
    return subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun Context.display(imageView: ImageView?, url: String){
    if (null == imageView){
        throw IllegalArgumentException("argument error")
    }

    Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .placeholder(R.drawable.ic_image_loading)
            .error(R.drawable.ic_empty_picture)
            .crossFade()
            .into(imageView)
}