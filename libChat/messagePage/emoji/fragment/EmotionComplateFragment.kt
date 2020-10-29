package com.somoplay.wefungame.libChat.messagePage.emoji.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.emoji.adapter.EmotionGridViewAdapter
import com.somoplay.wefungame.libChat.messagePage.emoji.adapter.EmotionPagerAdapter
import com.somoplay.wefungame.libChat.messagePage.emoji.emotionkeyboardview.EmojiIndicatorView
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.DisplayUtils
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.EmotionUtils
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.GlobalOnEmotionItemClickManagerUtils

class EmotionComplateFragment : BaseFragment() {
    private lateinit var emotionPagerGvAdapter: EmotionPagerAdapter
    private lateinit var vp_complate_emotion_layout: ViewPager
    private lateinit var ll_point_group: EmojiIndicatorView
    private lateinit var mActivity: Activity
    private var emotion_map_type: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_complate_emotion, container, false)
        mActivity = this.requireActivity()
        initView(rootView)
        initListener()
        return rootView
    }

    protected fun initView(rootView: View){
        vp_complate_emotion_layout = rootView.findViewById(R.id.vp_complate_emotion_layout) as ViewPager
        ll_point_group = rootView.findViewById(R.id.ll_point_group) as EmojiIndicatorView
        ll_point_group.setBackgroundResource(R.color.white)
        emotion_map_type = args.getInt(FragmentFactory.EMOTION_MAP_TYPE)
        initEmotion()
    }

    private fun initListener(){
        vp_complate_emotion_layout.addOnPageChangeListener(object : OnPageChangeListener {
            var oldPagerPos = 0
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position)
                oldPagerPos = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun initEmotion(){
        var screenWidth: Int =  DisplayUtils.getScreenWidthPixels(mActivity)
        var spacing: Int =  DisplayUtils.dp2px(mActivity, 12f)

        var itemWidth: Int = (screenWidth-spacing*10)/ 9
        var gvHeight :Int = itemWidth*3 + spacing*6
Log.d("emo", "itemWidth=$itemWidth and screenWidth=$screenWidth")
        var emotionViews : ArrayList<GridView> = ArrayList()
        var emotionNames : ArrayList<String> = ArrayList()

        for(emojiName:String in EmotionUtils.getEmojiMap(emotion_map_type).keys){
            emotionNames.add(emojiName)
            if(emotionNames.size == 27){
                var gv: GridView = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
                emotionViews.add(gv)
                emotionNames = ArrayList()
            }

        }

        if(emotionNames.size > 0){
            var gv : GridView = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
            emotionViews.add(gv)
        }

        ll_point_group.initIndicator(emotionViews.size)
        emotionPagerGvAdapter = EmotionPagerAdapter(emotionViews)
        vp_complate_emotion_layout.adapter = emotionPagerGvAdapter
        var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(screenWidth, gvHeight)
        vp_complate_emotion_layout.layoutParams = params
        Log.d("emo", emotionViews.size.toString())

    }


    private fun createEmotionGridView(emotionName: ArrayList<String>, gvWidth: Int, padding: Int, itemWidth: Int, gvHeight: Int ): GridView{
        var gv : GridView = GridView(mActivity)
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent)

        gv.numColumns = 9
        gv.setPadding(padding, padding, padding, padding)

        gv.horizontalSpacing = padding

        gv.verticalSpacing = padding*2

  Log.d("emo", "padding=$padding")

        var params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(gvWidth, gvHeight)
        gv.layoutParams = params

        var adapter: EmotionGridViewAdapter = EmotionGridViewAdapter(mActivity, emotionName, itemWidth, emotion_map_type)
        gv.adapter = adapter

        gv.onItemClickListener = GlobalOnEmotionItemClickManagerUtils.getInstance(mActivity)!!.getOnItemClickListener(emotion_map_type)
        return gv
    }

}