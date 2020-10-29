package com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class NoHorizontalScrollerVPAutoInputAdapter : FragmentPagerAdapter {

    private var datas: ArrayList<Fragment>

    constructor(fm: FragmentManager, datas: ArrayList<Fragment>):super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        this.datas = datas
    }

    override fun getItem(position: Int): Fragment {
        return datas[position]
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

}