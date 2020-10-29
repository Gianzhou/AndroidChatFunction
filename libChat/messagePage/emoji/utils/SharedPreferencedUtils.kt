package com.somoplay.wefungame.libChat.messagePage.emoji.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


class SharedPreferencedUtils {
    companion object {
        var mPreference: SharedPreferences? = null

        fun getPreference(context: Context): SharedPreferences? {
            if (mPreference == null) mPreference = PreferenceManager
                .getDefaultSharedPreferences(context)
            return mPreference
        }

        fun setInteger(context: Context, name: String, value: Int) {
            getPreference(context)!!.edit().putInt(name, value).apply()
        }

        fun getInteger(context: Context, name: String, default_i: Int): Int {
            return getPreference(context)!!.getInt(name, default_i)
        }

        /**
         * 设置字符串类型的配置
         */
        fun setString(
            context: Context,
            name: String,
            value: String
        ) {
            getPreference(context)!!.edit().putString(name, value).apply()
        }

        fun getString(context: Context, name: String): String? {
            return getPreference(context)!!.getString(name, null)
        }

        /**
         * 获取字符串类型的配置
         */
        fun getString(
            context: Context,
            name: String,
            defalt: String
        ): String? {
            return getPreference(context)!!.getString(name, defalt)
        }

        /**
         * 获取boolean类型的配置
         *
         * @param context
         * @param name
         * @param defaultValue
         * @return
         */
        fun getBoolean(
            context: Context, name: String,
            defaultValue: Boolean
        ): Boolean {
            return getPreference(context)!!.getBoolean(name, defaultValue)
        }

        /**
         * 设置boolean类型的配置
         *
         * @param context
         * @param name
         * @param value
         */
        fun setBoolean(
            context: Context,
            name: String,
            value: Boolean
        ) {
            getPreference(context)!!.edit().putBoolean(name, value).apply()
        }

        fun setFloat(context: Context, name: String, value: Float) {
            getPreference(context)!!.edit().putFloat(name, value).apply()
        }

        fun getFloat(
            context: Context,
            name: String,
            value: Float
        ): Float {
            return getPreference(context)!!.getFloat(name, 0f)
        }

        fun setLong(context: Context, name: String, value: Long) {
            getPreference(context)!!.edit().putLong(name, value).apply()
        }

        fun getLong(
            context: Context,
            name: String,
            defaultValue: Long
        ): Long {
            return getPreference(context)!!.getLong(name, defaultValue)
        }

    }

}