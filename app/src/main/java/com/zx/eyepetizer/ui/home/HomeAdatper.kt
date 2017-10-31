package com.zx.eyepetizer.ui.home

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zx.eyepetizer.R
import com.zx.eyepetizer.entity.HomeBean
import com.zx.eyepetizer.utils.ImageLoadUtils
import com.zx.eyepetizer.utils.display
import org.jetbrains.anko.find

/**
 * Created by admin on 2017/10/30.
 */
class HomeAdatper(context: Context, list: MutableList<HomeBean.IssueListBean.ItemListBean>) : RecyclerView.Adapter<HomeAdatper.HomeViewHolder>() {

    var context: Context? = null
    var list: MutableList<HomeBean.IssueListBean.ItemListBean>? = null
    var inflater: LayoutInflater? = null
    init {
        this.context = context
        this.list = list
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return list?.size ?:0//问号和冒号?:  if null {} else {} 的简写

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeViewHolder {
        return HomeViewHolder(inflater?.inflate(R.layout.item_home, parent, false), context!!)//双感叹号是Kotlin调用者强转一个可空类型到不可空类型的操作
    }

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        var bean = list?.get(position)
        var data = bean?.data
        var title = data?.title
        var category = data?.category
        var minute = data?.duration?.div(60)
        var second = data?.duration?.minus((minute?.times(60)) as Long)
        var realMinute : String
        var realSecond : String
        if (minute!! < 10){
            realMinute = "0" + minute
        }else{
            realMinute = minute.toString()
        }
        if (second!! < 10){
            realSecond = "0" + second
        }else{
            realSecond = second.toString()
        }

        var photo = data?.cover?.feed
        var author = data?.author
        ImageLoadUtils.display(context, holder?.iv_photo, photo as String)
//        context?.display(holder?.iv_photo, photo as String)
        holder?.tv_title?.text = title
        holder?.tv_detail?.text = "发布于 $category / $realMinute:$realSecond"

        if (null != author){
            context?.display(holder?.iv_user, author.icon)
        }else{
            holder?.iv_user?.visibility = View.GONE
        }

        holder?.itemView?.setOnClickListener{

        }




    }

    class HomeViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView) {
        var tv_detail : TextView?= null
        var tv_title : TextView ? = null
        var iv_photo : ImageView? = null
        var iv_user : ImageView? = null
        init {
            tv_detail = itemView?.find<TextView>(R.id.tv_detail)
            tv_title = itemView?.find<TextView>(R.id.tv_title)
            iv_photo = itemView?.find<ImageView>(R.id.iv_photo)
            iv_user = itemView?.find<ImageView>(R.id.iv_user)
            tv_title?.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        }
    }
}