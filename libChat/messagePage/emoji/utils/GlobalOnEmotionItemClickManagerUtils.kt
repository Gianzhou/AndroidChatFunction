package com.somoplay.wefungame.libChat.messagePage.emoji.utils

import android.content.Context
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.EditText
import com.somoplay.wefungame.libChat.messagePage.emoji.adapter.EmotionGridViewAdapter

class GlobalOnEmotionItemClickManagerUtils {
    fun getOnItemClickListener(emotion_map_type: Int): AdapterView.OnItemClickListener{
        return AdapterView.OnItemClickListener{ parent: AdapterView<*>, view: View, position: Int, id: Long ->
            var itemAdapter: Adapter? = parent.adapter
            if(itemAdapter is EmotionGridViewAdapter){
                var emotionGvAdapter: EmotionGridViewAdapter = itemAdapter as EmotionGridViewAdapter

                //press the delete key
                if(position < emotionGvAdapter.count ){
                /*    mEditText.dispatchKeyEvent(KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL
                    ))
                }else{*/
                    var emotionName: String = emotionGvAdapter.getItem(position) as String

                    var curPosition: Int = mEditText.selectionStart
                    var sb: StringBuilder = java.lang.StringBuilder(mEditText.text.toString())
                    sb.insert(curPosition,emotionName)

                    mEditText.setText(SpanStringUtils.getEmotionContent(emotion_map_type, mContext
                        , mEditText, sb.toString()))

                    mEditText.setSelection(curPosition + emotionName.length)
                }
            }


        }

    }

    fun attachToEditText(editText: EditText){
        mEditText = editText
    }

    companion object{
        private lateinit var mEditText: EditText
        private val LOCK= Any()
        private var instance: GlobalOnEmotionItemClickManagerUtils? = null
        private lateinit var mContext: Context
        fun getInstance(context: Context): GlobalOnEmotionItemClickManagerUtils?{
            mContext = context
            if(instance == null){
                synchronized(LOCK){
                    if(instance == null)
                        instance = GlobalOnEmotionItemClickManagerUtils()
                }
            }
            return instance
        }



    }
}