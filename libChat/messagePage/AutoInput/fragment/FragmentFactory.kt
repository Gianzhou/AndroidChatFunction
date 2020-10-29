package com.somoplay.wefungame.libChat.messagePage.AutoInput.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentFactory {
    companion object{
        const val AUTOINPUT_TYPE = "AUTOINPUT_TYPE"
        var instance : FragmentFactory = FragmentFactory()
        private val LOCK = Any()
        fun getSingleFactoryINstance(): FragmentFactory{
            if(instance != null){
                synchronized(LOCK){
                    if(instance != null){
                        instance = FragmentFactory()
                    }
                }
            }
            return instance
        }
    }

    fun getFragment(categoryId: Long): Fragment {
        var bundle: Bundle = Bundle()
        if(bundle != null){
            bundle.putLong(FragmentFactory.AUTOINPUT_TYPE, categoryId)
        }
        var fragment: AutoInputFragment = AutoInputFragment().newInstance(AutoInputFragment::class.java, bundle)
        return fragment
    }
}