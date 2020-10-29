package com.somoplay.wefungame.libChat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

open class LoginResponse {
    @Expose
    @SerializedName("data")
    private lateinit var user: AppUserModel

    /*protected fun getData(): JSONObject{
        return data3
    }
    fun getId():String{return id}

*/

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun toString(): String {
        /*if(data.length() == 0){
            return "data is null"
        }
        return data.toString()
    */

        return user.toString()
    }

}