package com.zx.eyepetizer.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zx.eyepetizer.R

/**
 * Created by admin on 2017/10/30.
 */
class ImageLoadUtils {

    companion object {
        fun display(context: Context?, imageView: ImageView?, url: String){
            if (null == imageView){
                throw IllegalArgumentException("argument error")
            }

            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .crossFade()
                    .into(imageView)
        }


    }

}