package com.zx.eyepetizer.view.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.zx.eyepetizer.R

/**
 * Created by zx on 2017/11/3.
 */
class PullRecyclerView: RecyclerView {

    constructor(context: Context?): this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){
        overScrollMode = OVER_SCROLL_NEVER
    }


    var downY = -1
    var constDownY = -1//常数，在down之后下次up之前，值不变

    var mLastMotionY = 0f
    var mLastMotionX= 0f
    var deltaX = 0f// X轴偏移量
    var deltaY = 0f// Y轴偏移量

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        var resume = super.onInterceptTouchEvent(e)
        when(e?.action){
            MotionEvent.ACTION_DOWN -> {
                //记录坐标
                mLastMotionX = e.x
                mLastMotionY = e.y

                downY = e.y.toInt()
                constDownY = e.y.toInt()
                resume = false
            }
            MotionEvent.ACTION_MOVE -> {
                deltaX = e.x.minus(mLastMotionX)
                // deltaY > 0 是向下运动,< 0是向上运动
                deltaY = e.y.minus(mLastMotionY)

                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    resume = false
                }else{
                    //当前view正在滑动，拦截事件
                    if (true){

                    }
                    resume = true
                }
            }
            MotionEvent.ACTION_UP -> {
                resume = false
            }
        }

        return resume
    }
    var isFirstMove = true
    var canRefresh = false
    var hasShow: Boolean = false
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when(e?.action){
            MotionEvent.ACTION_DOWN -> {
                downY = e.y.toInt()
                constDownY = e.y.toInt()



            }
            MotionEvent.ACTION_MOVE -> {
                if (isFirstMove){
                    isFirstMove = false
                    if (canRefresh){
                        canRefresh = e.y - downY > 0
                    }
                }

                if (canRefresh){
                    val firstView = getChildAt(0)

                    if (!hasShow){
                        showLoadingView(firstView)
                    }


                }



            }
            MotionEvent.ACTION_UP -> {

            }
        }

        return super.onTouchEvent(e)
    }

    val loading by lazy {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.eye_loading_progress)
        imageView
    }

    private val loadingView by lazy {
        val relativeLayout = RelativeLayout(context)
        relativeLayout.setBackgroundColor(0xaa000000.toInt())
        relativeLayout.gravity = Gravity.CENTER
        relativeLayout.addView(loading)
        relativeLayout.layoutParams = ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT)
        relativeLayout
    }

    private fun showLoadingView(viewGroup: ViewGroup?) {
        hasShow = true
        viewGroup?.addView(loadingView)

    }

}