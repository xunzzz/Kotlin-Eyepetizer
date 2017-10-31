package com.zx.eyepetizer.ui.search

import android.app.DialogFragment
import android.content.DialogInterface
import android.graphics.Typeface
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.view.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.zx.eyepetizer.R
import com.zx.eyepetizer.utils.KeyBoardUtils
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * Created by admin on 2017/10/30.
 */

const val SEARCH_TAG = "SearchFragment"

class SearchFragment : DialogFragment(), View.OnClickListener, CircularRevealAnim.AnimListener, DialogInterface.OnKeyListener {



    var data : MutableList<String> = arrayListOf("脱口秀","城会玩","666","笑cry","漫威",
            "清新","匠心","VR","心理学","舞蹈","品牌广告","粉丝自制","电影相关","萝莉","魔性"
            ,"第一视角","教程","毕业设计","奥斯卡","燃","冰与火之歌","温情","线下campaign","公益")

    lateinit var mRootView: View
    lateinit var mCircularRevealAnim: CircularRevealAnim
    lateinit var mAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onStart() {
        super.onStart()
        initDialog()
    }

    private fun initDialog() {
        val window = dialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.98).toInt()
        window?.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)//移除dialog过渡动画
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater?.inflate(R.layout.fragment_search, container, false)!!
        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setData()
    }

    private fun setData() {

    }

    private fun init() {
        tv_hint.typeface = Typeface.createFromAsset(activity.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        mCircularRevealAnim = CircularRevealAnim()
        mCircularRevealAnim.setAnimListener(this)
        dialog.setOnKeyListener(this)
        iv_search_search.setOnClickListener(this)
        iv_search_back.setOnClickListener(this)

        mAdapter = SearchAdapter(activity, data as ArrayList<String>)
        mAdapter.setOnDialogDismissListener(object : SearchAdapter.OnDialogDismiss{
            override fun onDismiss() {
                hideAnim()
            }
        })

        val manager = FlexboxLayoutManager()
        //设置主轴排列方式
        manager.flexDirection = FlexDirection.ROW
        //设置是否换行
        manager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
    }


    override fun onHideAnimationEnd() {
        et_search_keyword.setText("")
        dismiss()
    }

    override fun onShowAnimationEnd() {
        if (isVisible){
            KeyBoardUtils.openKeyboard(activity, et_search_keyword)
        }
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP){
            hideAnim()
        }else if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP){
            search()
        }

        return false
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_search_search -> {
                search()
            }
            R.id.iv_search_back -> {
                hideAnim()
            }
        }

    }

    private fun hideAnim() {
        KeyBoardUtils.closeKeyboard(activity, et_search_keyword)
        mCircularRevealAnim.hide(iv_search_search, mRootView)

    }

    private fun search() {


    }


}