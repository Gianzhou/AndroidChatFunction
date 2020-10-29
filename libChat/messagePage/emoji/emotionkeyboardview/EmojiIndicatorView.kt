package com.somoplay.wefungame.libChat.messagePage.emoji.emotionkeyboardview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.DisplayUtils

class EmojiIndicatorView : LinearLayout {
    private lateinit var mContext: Context
    private lateinit var mImageViews: ArrayList<View>
    private var size = 6
    private var marginSize = 15
    private var pointSize: Int = 0
    private var marginLeft: Int = 0


    constructor(context: Context):super(context, null){
        mContext = context
        pointSize = DisplayUtils.dp2px(context, size.toFloat())
        marginLeft = DisplayUtils.dp2px(context, marginSize.toFloat())

    }
    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs, 0) {
        mContext = context
        pointSize = DisplayUtils.dp2px(context, size.toFloat())
        marginLeft = DisplayUtils.dp2px(context, marginSize.toFloat())

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):super(context, attrs, defStyleAttr){
        mContext = context
        pointSize = DisplayUtils.dp2px(context, size.toFloat())
        marginLeft = DisplayUtils.dp2px(context, marginSize.toFloat())

    }

    fun initIndicator(count: Int){
        mImageViews = ArrayList()
        this.removeAllViews()
        var lp: LayoutParams
    //    Log.d("emo", "width = ${pointSize.toInt()}, marginLeft=$marginLeft")
        for (i in 0 until count) {
            var v = ImageView(mContext)
            lp = LayoutParams(pointSize, pointSize)
         //   lp = LayoutParams(pointSize, pointSize)

           if (i != 0) lp.leftMargin = marginLeft
            //  if (i != 0) lp.leftMargin = 12
            v.layoutParams = lp


            if (i == 0) {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_select)
            } else {
                v.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal)
            }
            mImageViews.add(v)
         //   v.setBackgroundResource(R.drawable.shape_bg_indicator_point_select)
            this.addView(v)
        }
    }

    fun playByStartPointToNext(startPosition: Int, nextPosition: Int) {
        var startPosition = startPosition
        var nextPosition = nextPosition
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            nextPosition = 0
            startPosition = nextPosition
        }
        val ViewStrat = mImageViews[startPosition]
        val ViewNext = mImageViews[nextPosition]
        ViewNext.setBackgroundResource(R.drawable.shape_bg_indicator_point_select)
        ViewStrat.setBackgroundResource(R.drawable.shape_bg_indicator_point_nomal)
    }



}