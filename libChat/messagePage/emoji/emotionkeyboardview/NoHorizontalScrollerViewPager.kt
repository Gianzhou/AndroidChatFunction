package com.somoplay.wefungame.libChat.messagePage.emoji.emotionkeyboardview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoHorizontalScrollerViewPager: ViewPager {
    constructor(context: Context):super(context){}

    constructor(context: Context, attris: AttributeSet):super(context, attris){}

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}