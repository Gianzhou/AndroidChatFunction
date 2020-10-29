package com.somoplay.wefungame.libChat.messagePage.AutoInput.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter.AutoInputGridViewAdapter
import com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter.AutoInputPagerAdapter
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.TextModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.repository.TextRepository
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.viewModel.TextViewModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.utils.GlobalOnAutoInputItemClickMangerUtils
import com.somoplay.wefungame.libChat.messagePage.emoji.fragment.BaseFragment
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.DisplayUtils
import org.w3c.dom.Text

class AutoInputFragment() : BaseFragment(){

    private lateinit var mActivity: Activity
    private lateinit var vp_complate_autoinput_layout: ViewPager

    private lateinit var mContext: Context
    private lateinit var autoInputPagerAdapter: AutoInputPagerAdapter
    private lateinit var mTextModeList: LiveData<List<TextModel>>
    private lateinit var mTextViewModel: TextViewModel
    private var mId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView :View = inflater.inflate(R.layout.fragment_autoinput, container, false)
        mTextViewModel = ViewModelProvider(this).get(TextViewModel::class.java)
        mActivity = this.requireActivity()
        mContext = this.requireContext()

        mId = args.getLong(FragmentFactory.AUTOINPUT_TYPE)
        mTextModeList = mTextViewModel.getSameCategoryTextItems(mId)
        initView(rootView)
        initListener()
        return rootView
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }


    fun initView(rootView: View){
        vp_complate_autoinput_layout = rootView.findViewById(R.id.vp_complate_autoInput_layout)
        initAutoInput()

    }
    fun initListener(){
        vp_complate_autoinput_layout.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }
        })
    }
    private fun initAutoInput(){
        var screenWidth: Int =  DisplayUtils.getScreenWidthPixels(mActivity)
        var spacing: Int =  DisplayUtils.dp2px(mActivity, 12f)
        var name: String
        var itemWidth: Int = (screenWidth-spacing*20)/ 3
        var gvHeight :Int = itemWidth*3 + spacing*6
        Log.d("emo", "AutoInputFragment: itemWidth=$itemWidth and screenWidth=$screenWidth")
        var textViews : ArrayList<GridView> = ArrayList()
        var textNames : ArrayList<String> = ArrayList()

        mTextModeList.observe(viewLifecycleOwner, Observer { textModelList->
            run {
                for (textModel: TextModel in textModelList) { // version add all the text into one gridView
                    name = textModel.getName()
                    if(!name.isEmpty()) {
                        Log.d("emo", "this is from autoinputfragment $name")
                        textNames.add(name)
                        if(textNames.size == 20){
                            var gv: GridView = createAutoInputGridView(textNames, screenWidth, spacing, itemWidth, gvHeight)
                            textViews.add(gv)
                            textNames = ArrayList<String>()
                        }
                    }
                }
                if(textNames.isNullOrEmpty()){// first time
                    println("List is null or empty")
                }else{
                    var gv: GridView = createAutoInputGridView(textNames, screenWidth, spacing, itemWidth, gvHeight)
                    textViews.add(gv)
                }
                Log.d("emo", "textView.size: " + textViews.size)

                autoInputPagerAdapter = AutoInputPagerAdapter(textViews)
                vp_complate_autoinput_layout.adapter = autoInputPagerAdapter
                var params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(screenWidth, gvHeight)
                vp_complate_autoinput_layout.layoutParams = params



            }

        })

            /*for(textName:String in mTextModeList){
            TextNames.add(textName)
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
        Log.d("emo", emotionViews.size.toString())*/

    }

    private fun createAutoInputGridView(itemName: ArrayList<String>, gvWidth: Int, padding: Int, itemWidth: Int, gvHeight: Int): GridView{
        var gv: GridView = GridView(mActivity)
        gv.setSelector(android.R.color.transparent)
        gv.numColumns = 2
        gv.setPadding(padding, padding, padding, padding)
        gv.horizontalSpacing = padding
        gv.verticalSpacing = padding*2

        var params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(gvHeight, gvWidth)
        gv.layoutParams = params

        var adapter: AutoInputGridViewAdapter = AutoInputGridViewAdapter(mActivity, itemName, itemWidth)
        gv.adapter = adapter
        //invoke global listener here

        gv.onItemClickListener = GlobalOnAutoInputItemClickMangerUtils.getInstance(mActivity)!!.getItemOnClickListener()
        return gv
    }


}