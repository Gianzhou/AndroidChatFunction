package com.somoplay.wefungame.libChat.messagePage.emoji.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import me.yokeyword.fragmentation.SupportFragment

open class BaseFragment : SupportFragment() {
    protected lateinit var args: Bundle

    fun <T : Fragment> newInstance(
        clazz: Class<*>,
        args: Bundle?
    ): T {
        var mFragment: T = Fragment() as T
        try {
            mFragment = clazz.newInstance() as T
        } catch (e: java.lang.InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        mFragment.arguments = args
        return mFragment
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = this.requireArguments()
    }
}