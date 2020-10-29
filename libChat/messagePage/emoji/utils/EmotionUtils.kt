package com.somoplay.wefungame.libChat.messagePage.emoji.utils

import android.util.Log
import androidx.collection.ArrayMap
import com.somoplay.wefungame.R

class EmotionUtils {

    var EMPTY_MAP : ArrayMap<String, Int>
    var EMOTION_CLASSIC_MAP: ArrayMap<String, Int>

    init {
        EMPTY_MAP = ArrayMap<String, Int>()
        EMOTION_CLASSIC_MAP = ArrayMap<String, Int>()
        EMOTION_CLASSIC_MAP.put("[爱心]", R.drawable.d_aini)
        EMOTION_CLASSIC_MAP.put("[白眼]", R.drawable.d_baiyan)
        EMOTION_CLASSIC_MAP.put("[悲伤]", R.drawable.d_beishang)
        EMOTION_CLASSIC_MAP.put("[搞蛋]", R.drawable.d_gaodan)
        EMOTION_CLASSIC_MAP.put("[害羞]", R.drawable.d_haixiu)
        EMOTION_CLASSIC_MAP.put("[惊奇]", R.drawable.d_jinqi)
        EMOTION_CLASSIC_MAP.put("[惊悚]", R.drawable.d_jinshong)
        EMOTION_CLASSIC_MAP.put("[惊讶]", R.drawable.d_jinya)
        EMOTION_CLASSIC_MAP.put("[开心]", R.drawable.d_kaixin)
        EMOTION_CLASSIC_MAP.put("[高兴]", R.drawable.d_kaixing)
        EMOTION_CLASSIC_MAP.put("[酷]", R.drawable.d_ku)
        EMOTION_CLASSIC_MAP.put("[泪]", R.drawable.d_lei)
        EMOTION_CLASSIC_MAP.put("[流泪]", R.drawable.d_luilei)
        EMOTION_CLASSIC_MAP.put("[怒]", R.drawable.d_nu)
        EMOTION_CLASSIC_MAP.put("[亲亲]", R.drawable.d_qinqin)
        EMOTION_CLASSIC_MAP.put("[伤心]", R.drawable.d_shangxin)
        EMOTION_CLASSIC_MAP.put("[生病]", R.drawable.d_shengbing)
        EMOTION_CLASSIC_MAP.put("[生气]", R.drawable.d_shengqi)
        EMOTION_CLASSIC_MAP.put("[衰]", R.drawable.d_shuai)
        EMOTION_CLASSIC_MAP.put("[头痛]", R.drawable.d_toutong)
        EMOTION_CLASSIC_MAP.put("[想哭]", R.drawable.d_xiangku)
        EMOTION_CLASSIC_MAP.put("[喜喜]", R.drawable.d_xixi)
        EMOTION_CLASSIC_MAP.put("[晕]", R.drawable.d_yun)
        EMOTION_CLASSIC_MAP.put("[眨眼]", R.drawable.d_zhanyan)
        EMOTION_CLASSIC_MAP.put("[Happy]", R.drawable.d_zhanyan)


    }
    companion object{
        val EMOTION_CLASSIC_TYPE  = 0x0001
        val instant =
            EmotionUtils()
        fun getImgByName(EmotionType: Int, imgName: String): Int{
            var integer: Int? = null
            when(EmotionType){
                EMOTION_CLASSIC_TYPE->{
                    integer = instant.EMOTION_CLASSIC_MAP[imgName]
                }
                else->{
                    Log.d("emo", "the emojiMap is null")
                }
            }

            if(integer == null)
                return -1
            return integer
        }

        fun getEmojiMap(EmotionType: Int): ArrayMap<String, Int>{
            var EmojiMap = ArrayMap<String, Int>()
            when(EmotionType){
                EMOTION_CLASSIC_TYPE->EmojiMap = instant.EMOTION_CLASSIC_MAP
                else-> EmojiMap = instant.EMPTY_MAP
            }
            return EmojiMap
        }
    }
}