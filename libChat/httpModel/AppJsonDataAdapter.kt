package com.somoplay.wefungame.libChat.httpModel

import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

object AppJsonDataAdapter {
    fun <T> objectToJSONObject(t: T?): JSONObject? {
        var jsonObject: JSONObject? = null
        if (t != null) {
            val gson = Gson()
            return try {
                JSONObject(gson.toJson(t))
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            }
        }
        return null
    }


    fun <T> objectFromJson(jsonString: String?, objectClass: Class<T>): T? {
        val gson = Gson()
        return try {
            if (objectClass == JSONObject::class.java) JSONObject(jsonString) as T else gson.fromJson(
                jsonString,
                objectClass
            )
        } catch (e: Exception) {
            null
        }
    }

    fun <T> objectToJson(t: T?): String? {
        if (t != null) {
            val gson = Gson()
            return gson.toJson(t)
        }
        return null
    }
}