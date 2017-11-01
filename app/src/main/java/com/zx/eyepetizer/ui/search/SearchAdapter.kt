package com.zx.eyepetizer.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.zx.eyepetizer.R
import org.jetbrains.anko.find

/**
 * Created by admin on 2017/10/31.
 */
class SearchAdapter(context: Context, list: ArrayList<String>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var context: Context? = null
    var list: ArrayList<String>? = null
    var inflater: LayoutInflater? = null
    var mDialogListener: OnDialogDismiss? = null
    init {
        this.context = context
        this.list = list
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchViewHolder {
        return SearchViewHolder(inflater?.inflate(R.layout.item_search, parent, false), context!!)

    }

    override fun onBindViewHolder(holder: SearchViewHolder?, position: Int) {
        holder?.tv_tilte?.text = list?.get(position)
        val params = holder?.tv_tilte?.layoutParams
        if (params is FlexboxLayoutManager.LayoutParams){
            (holder?.tv_tilte?.layoutParams as FlexboxLayoutManager.LayoutParams).flexGrow = 1.0f
        }
        holder?.itemView?.setOnClickListener {

        }



    }

    class SearchViewHolder(itemview: View?, context: Context) : RecyclerView.ViewHolder(itemview) {
        var tv_tilte: TextView = itemview?.findViewById(R.id.tv_title) as TextView
    }

    interface OnDialogDismiss{
        fun onDismiss()
    }

    fun setOnDialogDismissListener(onDialogDismiss: OnDialogDismiss){
        mDialogListener = onDialogDismiss
    }

}