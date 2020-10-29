package com.somoplay.wefungame.libChat.messagePage.emoji.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.LayoutParams
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.ImageView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.EmotionUtils

class EmotionGridViewAdapter : BaseAdapter {
    private var context: Context
    private var emotionNames: List<String>
    private var itemWidth: Int
    private var emotion_map_type: Int

    constructor(context: Context, emotionNames: List<String>, itemWidth: Int, emotion_map_type: Int){
        this.context = context
        this.emotionNames = emotionNames
        this.itemWidth = itemWidth
        this.emotion_map_type = emotion_map_type
    }

    override fun getView(postion: Int, convertView: View?, parent: ViewGroup?): View {
        var iv_emotion: ImageView = ImageView(context)

        iv_emotion.setPadding(itemWidth/10, itemWidth/10, itemWidth/10, itemWidth/10)
     //   var params: WindowManager.LayoutParams = WindowManager.LayoutParams(itemWidth, itemWidth)
        var params: LayoutParams =LayoutParams(itemWidth,itemWidth)
        Log.d("emo", "itemWidth=$itemWidth")
        iv_emotion.layoutParams = params

        if(postion < count){
           /* iv_emotion.setImageResource(R.drawable.delete)
            Log.d("emo", "postion = $postion")
        }else{*/
            var emotionName = emotionNames[postion]
            iv_emotion.setImageResource(EmotionUtils.getImgByName(emotion_map_type, emotionName))
        }

        return iv_emotion
    }

    override fun getItem(position: Int): Any {
        return emotionNames[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return emotionNames.size
    }
}