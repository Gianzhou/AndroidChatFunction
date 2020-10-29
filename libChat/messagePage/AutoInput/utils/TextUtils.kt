package com.somoplay.wefungame.libChat.messagePage.AutoInput.utils

import android.util.Log
import androidx.collection.ArrayMap

class TextUtils {

    var EMPTY_MAP : ArrayMap<String, Int>
    var TEXT_MAP : ArrayMap<String, Int>

    init {
        EMPTY_MAP = ArrayMap<String, Int>()
        TEXT_MAP = ArrayMap<String, Int>()
        TEXT_MAP.put("Hello", 0)
        TEXT_MAP.put("Hi",1)
    }

    companion object{
        val TEXT_INIT_TYPE = 0X0001
        var instant: TextUtils = TextUtils()
        /*
        get the text name by text type
         */
        fun getTextByName(textType: Int, textName: String): Int{
            var integer: Int? = null
            when(textType){
                TEXT_INIT_TYPE->{
                    integer = instant.TEXT_MAP[textName]
                }
                else->{
                    Log.d("emo", "the emojiMap is null")
                }
            }

            if(integer == null)
                return -1
            return integer

        }

        fun getInitTextMap(textType: Int): ArrayMap<String, Int>{
            var textArray :ArrayMap<String, Int>
            when(textType){
                TEXT_INIT_TYPE->{
                    textArray = instant.TEXT_MAP
                }
                else->{
                    textArray = instant.EMPTY_MAP
                }
            }
            return textArray
        }
    }
}