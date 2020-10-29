package com.somoplay.wefungame.libChat.keyDB

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.newSingleThreadContext


class AppKeyDB {
    private var pref: SharedPreferences
    private var mcontext: Context
    constructor(context: Context) {
        this.mcontext = context
        pref= context?.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    }


    companion object{
        @Volatile

        private lateinit var mcontext: Context
         private var INSTANT :AppKeyDB? = null
         fun instance(context: Context)= INSTANT?: synchronized(Any()){
            mcontext = context
            INSTANT ?: initialize(mcontext).also{ INSTANT = it}
        }

        fun initialize(context: Context): AppKeyDB{
            return AppKeyDB(context)
        }



    }
    fun save(  key: String, value: Int){
        var editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()

    }

    fun save(key: String, value: Long){
        var editor = pref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun save(key: String, value: Float){
        var editor = pref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun save(key: String, value: Boolean){
        var editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
    fun save(key: String, value: String) {
        var editor = pref.edit()
        editor.putString(key, value).apply()
    }

    fun saveToken(token : String){
        var editor = pref.edit()
        editor.putString("token", token).apply()
    }

    fun getToken():String?{
        return pref.getString("token", "")
    }

    fun get(key: String): String?{
        return pref.getString(key, "")
    }


}


