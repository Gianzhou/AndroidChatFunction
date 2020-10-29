package com.somoplay.wefungame.libChat.messagePage.emoji.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class EmotionPagerAdapter: PagerAdapter {
    private var gvs: List<GridView>

    constructor(gvs: List<GridView>){
        this.gvs = gvs
    }
    override fun isViewFromObject(view: View, arg1: Any): Boolean {
        return  view == arg1
    }

    override fun getCount(): Int {
       return gvs.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        (container as ViewPager).removeView(gvs[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        (container as ViewPager).addView(gvs[position])
        return gvs[position]
    }
}