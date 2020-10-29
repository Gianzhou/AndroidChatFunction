package com.somoplay.wefungame.libChat.model

import android.os.Message
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class Converters {
    companion object{
        var gson = Gson()
        @TypeConverter
        @JvmStatic
        fun toLibUserModel(data: String?): List<LibUserModel> {
            if (data == null) {
                return Collections.emptyList()
            }
            val listType: Type =
                object : TypeToken<List<LibUserModel?>?>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromLibUserModel(someObjects: List<LibUserModel?>?): String {
            return gson.toJson(someObjects)
        }


        @TypeConverter
        @JvmStatic
        fun toMessageType(data: String?) : MessageType {
            if(data == null){
                return MessageType.TEXT
            }
            val type = object : TypeToken<MessageType?>() {}.type
            return gson.fromJson(data, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromMessageType(someObjects: MessageType) : String{
            return gson.toJson(someObjects)
        }

        @TypeConverter
        @JvmStatic
        fun toLibMessageModel(data: String?) :MutableList<LibMessageModel>{
            if(data == null){
                return Collections.emptyList()
            }
            val listType: Type =
                object : TypeToken<MutableList<LibUserModel?>?>() {}.type
            return gson.fromJson(data, listType)
        }
        @TypeConverter
        @JvmStatic
        fun fromLibMessageModel(someObjects: MutableList<LibMessageModel?>?) : String{
            return gson.toJson(someObjects)
        }

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?) : Date? {
            return value?.let{Date(it)}
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?) : Long?{
            return date?.time?.toLong()
        }
    }



}