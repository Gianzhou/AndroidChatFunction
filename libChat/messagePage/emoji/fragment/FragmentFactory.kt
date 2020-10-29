package com.somoplay.wefungame.libChat.messagePage.emoji.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment


class FragmentFactory {


    companion object{
        const val EMOTION_MAP_TYPE = "EMOTION_MAP_TYPE"
        var instance: FragmentFactory = FragmentFactory()
        private val LOCK = Any()
        fun getSingleFactoryInstance(): FragmentFactory{
            if(instance != null){
                synchronized(LOCK){
                    if(instance != null)
                        instance = FragmentFactory()
                }
            }
            return instance
        }
    }

    fun getFragment(emotinType:Int): Fragment {
         var bundle : Bundle = Bundle()
        if (bundle != null) {
            bundle.putInt(FragmentFactory.EMOTION_MAP_TYPE, emotinType)
        }
        var fragment: EmotionComplateFragment = EmotionComplateFragment().newInstance(EmotionComplateFragment::class.java,bundle)

        return fragment
    }
}