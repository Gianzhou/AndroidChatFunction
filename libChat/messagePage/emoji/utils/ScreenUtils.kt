package com.somoplay.wefungame.libChat.messagePage.emoji.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenUtils {
    companion object{
        fun getScreenWidth(context: Context): Int{
            var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            var outMetrics: DisplayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        fun getScreenHeight(context: Context): Int{
            var wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            var outMetrics: DisplayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }


    }
}