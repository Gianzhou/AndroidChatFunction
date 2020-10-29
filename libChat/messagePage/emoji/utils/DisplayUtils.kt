package com.somoplay.wefungame.libChat.messagePage.emoji.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log

class DisplayUtils {
    companion object{
        fun px2dp(context : Context, pxValue: Float): Int{
            val scale: Float= context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun dp2px(context: Context, dpValue: Float): Int{
            val scale: Float = context.resources.displayMetrics.density
            Log.d("emo", "scale=$scale")
            Log.d("emo", (dpValue*scale +0.5f).toString())
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2sp(context: Context, pxValue: Float): Int{
            val fontScale: Float = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        fun sp2px(context: Context, spValue: Float): Int{
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        fun getScreenWidthPixels(context: Activity): Int{
            var metric : DisplayMetrics = DisplayMetrics()
                context.windowManager.defaultDisplay.getMetrics(metric)
            return metric.widthPixels
        }

        fun getScreenHeightPixels(context: Activity): Int{
            var metrics: DisplayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(metrics)
            return metrics.heightPixels
        }
    }
}