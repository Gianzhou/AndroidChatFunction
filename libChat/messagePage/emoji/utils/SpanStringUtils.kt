package com.somoplay.wefungame.libChat.messagePage.emoji.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.Log
import android.widget.TextView
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.EmotionUtils
import java.util.regex.Pattern

class SpanStringUtils {
    companion object{

        fun getEmotionContent(emotion_map_type: Int, context: Context, tv:TextView, source: String): SpannableString{
            var spannableString = SpannableString(source)
            var res = context.resources
            var regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]"
            var patternEmotion = Pattern.compile(regexEmotion)
            var matcherEmotion = patternEmotion.matcher(spannableString)
            Log.d("emo", "this is getEmotionContent spannableString = $spannableString")


            var count = 0
            while(matcherEmotion.find()){
                count++
                var key: String = matcherEmotion.group()
                var start: Int = matcherEmotion.start()

                var imgRes :Int = EmotionUtils.getImgByName(emotion_map_type, key)
                Log.d("emo", "key = $key, matcher = $matcherEmotion, count = $count, imgRes = $imgRes, emotion_map_type = $emotion_map_type")

                if(imgRes != 0){
                    var size: Int = (tv.textSize*13/10).toInt()
                    var bitmap : Bitmap = BitmapFactory.decodeResource(res, imgRes)
                    var scaleBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true)
                    var span: ImageSpan = ImageSpan(context, scaleBitmap)
                    spannableString.setSpan(span, start, start + key.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
            return spannableString
        }
    }
}