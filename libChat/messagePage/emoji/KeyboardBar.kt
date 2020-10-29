package com.somoplay.wefungame.libChat.messagePage.emoji

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log

class KeyboardBar private constructor(){
    private object Holder{
        val keyboardBar: KeyboardBar = KeyboardBar()
    }
    val SHARE_PREFERENCE_NAME = " MessagePageKeyboard";
    private val SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height"
    private lateinit var mActivity: Activity
    private lateinit var sp: SharedPreferences

    companion object{
        private val instance: KeyboardBar by lazy { Holder.keyboardBar }
        fun with(activity: Activity):KeyboardBar{
            instance.mActivity = activity
            instance.sp = activity.getSharedPreferences(instance.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)

            return instance
        }

    }

    fun isSoftInputShown(): Boolean{
        return getSupportSoftInputHeight() != 0
    }

    fun getSupportSoftInputHeight(): Int{
        var r: Rect = Rect()

        mActivity.window.decorView.getWindowVisibleDisplayFrame(r)

        var screenHeight = mActivity.window.decorView.rootView.height
        var softInputHeight = screenHeight - r.bottom

        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if(Build.VERSION.SDK_INT >= 20){
            softInputHeight -= getSoftButtonsBarHeight()
        }
        if(softInputHeight < 0){
            Log.w("emo","EmotionKeyboard--Warning: value of softInputHeight is below zero!")
        }
        if(softInputHeight> 0){
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT , softInputHeight).apply()
        }

        return softInputHeight
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getSoftButtonsBarHeight(): Int{
        //这个方法获取可能不是真实屏幕的高度
        var metrics: DisplayMetrics = DisplayMetrics()
        mActivity.windowManager.defaultDisplay.getMetrics(metrics)
        var usableHeight = metrics.heightPixels

        //获取当前屏幕的真实高度
        mActivity.windowManager.defaultDisplay.getRealMetrics(metrics)
        var realHeight: Int = metrics.heightPixels
        if(realHeight > usableHeight){
            return realHeight - usableHeight
        }else{
            return 0
        }

    }

    public fun getKeyBoardHeight(): Int{
        return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 787)
    }

}