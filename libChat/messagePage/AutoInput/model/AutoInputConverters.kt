package com.somoplay.wefungame.libChat.messagePage.AutoInput.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class AutoInputConverters {
    companion object{
        var gson = Gson()
        @TypeConverter
        @JvmStatic
        fun toTextModel(data: String?): List<TextModel>{
            if(data == null){
                return Collections.emptyList()
            }
            val listType: Type =
                object: TypeToken<List<TextModel?>?>() {}.type
            return gson.fromJson(data, listType)

        }

        @TypeConverter
        @JvmStatic
        fun fromTextModel(someObject: List<TextModel?>?): String{
            return gson.toJson(someObject)
        }

    }
}