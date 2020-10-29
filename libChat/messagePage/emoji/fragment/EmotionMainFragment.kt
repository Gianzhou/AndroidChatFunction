package com.somoplay.wefungame.libChat.messagePage.emoji.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.AutoInput.AutoInputKeyboard
import com.somoplay.wefungame.libChat.messagePage.AutoInput.NoHorizontalScrollerAutoInputViewPager
import com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter.HorizontalRecyclerAutoInputViewAdapter
import com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter.NoHorizontalScrollerVPAutoInputAdapter
import com.somoplay.wefungame.libChat.messagePage.AutoInput.fragment.AutoInputFragment
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.CategoryModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.viewModel.CategoryViewModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.utils.GlobalOnAutoInputItemClickMangerUtils
import com.somoplay.wefungame.libChat.messagePage.emoji.EmotionKeyboard
import com.somoplay.wefungame.libChat.messagePage.emoji.ImageModel
import com.somoplay.wefungame.libChat.messagePage.emoji.KeyboardBar
import com.somoplay.wefungame.libChat.messagePage.emoji.adapter.HorizontalRecyclerviewAdapter
import com.somoplay.wefungame.libChat.messagePage.emoji.adapter.HorizontalRecyclerviewAdapter.OnClickItemListener
import com.somoplay.wefungame.libChat.messagePage.emoji.adapter.NoHorizontalScrollerVPAdapter
import com.somoplay.wefungame.libChat.messagePage.emoji.emotionkeyboardview.NoHorizontalScrollerViewPager
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.EmotionUtils
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.GlobalOnEmotionItemClickManagerUtils
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.SharedPreferencedUtils
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.libChat.viewModel.MessageViewModel


class EmotionMainFragment: BaseFragment() {
    companion object {
        const val BIND_TO_EDITTEXT: String = "bind_to_edittext"
        const val HIDE_BAR_EDITTEXT_AND_BTN: String = "hide bar's editText and btn"
        private const val EMOTION_CURRENT_POSITION_FLAG: String = "EMOTION_CURRENT_POSITION_FLAG"
        private const val AUTO_CURRENT_POSTION_FLAG: String = "AUTO_CURRENT_POSITION_FLAG"

    }
        private var Emotion_CurrentPosition: Int = 0
        private var Auto_CurrentPosition: Int = 0
        private lateinit var emotion_recyclerview_horizontal: RecyclerView
        private lateinit var auto_recyclerview_horizontal: RecyclerView
        private lateinit var horizontalRecyclerviewAdapter: HorizontalRecyclerviewAdapter

        private lateinit var mHorizontalRecyclerAutoInputViewAdapter: HorizontalRecyclerAutoInputViewAdapter

        private lateinit var mEmotionKeyboard: EmotionKeyboard
        private lateinit var mAutoInputKeyboard: AutoInputKeyboard

        private lateinit var bar_edit_text: EditText
        private lateinit var bar_image_add_btn: ImageView
        private lateinit var bar_btn_send: Button
        private lateinit var rl_editbar_bg: LinearLayout

        private lateinit var contentView: View

        private lateinit var emotionViewPager : NoHorizontalScrollerViewPager

        private lateinit var autoViewPager: NoHorizontalScrollerAutoInputViewPager

        private var isBindToBarEditText: Boolean = true

        private var isHidenBarEditTextAndBtn: Boolean = false

        private lateinit var mActivity: Activity

        private lateinit var mEmotionDelete: ImageView

        private lateinit var mEmotionSend: Button

        var emotionFragmentsList: ArrayList<Fragment> = ArrayList()
        var autoInputFragmentsList: ArrayList<Fragment> = ArrayList()
    

        private lateinit var mMessageViewModel: MessageViewModel
        private lateinit var mCurrentConversation: LibChatModel

        private lateinit var mKeyboardBar: KeyboardBar

        private lateinit var mCategoryViewModel: CategoryViewModel

        private lateinit var mCategoryList: LiveData<List<CategoryModel>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mActivity = this.requireActivity()
        Log.d("emo", "this is EmotionMainFragment onCreateView")
        val rootView: View = inflater.inflate(R.layout.fragment_main_emotion, container, false)
       isHidenBarEditTextAndBtn = args.getBoolean(HIDE_BAR_EDITTEXT_AND_BTN)

        /*var linearLayout: LinearLayout = rootView.findViewById(R.id.ll_emotion_layout)
        Log.d("emo",  "this is emotionMainFragment: " + linearLayout.layoutParams.height)
        linearLayout.visibility = View.VISIBLE
        */
        mEmotionDelete = rootView.findViewById(R.id.emotion_delete)
        mEmotionSend = rootView.findViewById(R.id.emotion_send)

        mKeyboardBar = KeyboardBar.with(mActivity)

        isBindToBarEditText = args.getBoolean(BIND_TO_EDITTEXT)
        initView(rootView)
Log.d("emo", "mEmotionkeyboard")
        mEmotionKeyboard = EmotionKeyboard.with(mActivity)
         //   .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout), rootView.findViewById(R.id.voice_input_layout), rootView.findViewById(R.id.automatic_layout), rootView.findViewById(R.id.addMore_layout))
            .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout), rootView.findViewById(R.id.voice_input_layout), rootView.findViewById(R.id.addMore_layout))
            .bindToContent(contentView)
            .bindToKeyboardBar(mKeyboardBar)
            .bindToEditText(
                (if (!isBindToBarEditText) contentView as EditText else rootView.findViewById<View>(
                    R.id.bar_edit_text
                ) as EditText)
            )
            .bindToVoiceButton(rootView.findViewById(R.id.voice_input))
         //   .bindToAutomaticButton(rootView.findViewById(R.id.automatic_input))
            .bindToAddMoreButton(rootView.findViewById(R.id.add_more))
            .bindToEmotionButton(rootView.findViewById(R.id.emotion_button))
            .build()

        mAutoInputKeyboard = AutoInputKeyboard.with(mActivity)
            .setAutoInputView(rootView.findViewById(R.id.automatic_layout))
            .bindToContent(contentView)
            .bindTokeyboardBar(mKeyboardBar)
            .bindToAutomaticButton(rootView.findViewById(R.id.automatic_input))
            .build()



        var globalOnEmotionItemClickManager: GlobalOnEmotionItemClickManagerUtils = GlobalOnEmotionItemClickManagerUtils.getInstance(mActivity)!!
        var globalOnAutoInputItemClickMangerUtils: GlobalOnAutoInputItemClickMangerUtils = GlobalOnAutoInputItemClickMangerUtils.getInstance(mActivity)!!

        if(isBindToBarEditText){
            globalOnEmotionItemClickManager.attachToEditText(bar_edit_text)
            globalOnAutoInputItemClickMangerUtils.attachToEditText(bar_edit_text)
        }else{
            globalOnEmotionItemClickManager.attachToEditText(contentView as EditText)
            mEmotionKeyboard.bindToEditText(contentView as EditText)

            globalOnAutoInputItemClickMangerUtils.attachToEditText(contentView as EditText)
        //    mAutoInputKeyboard
        }

        mMessageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mCategoryList = mCategoryViewModel.getAllCategories()
        mCategoryList.observe(viewLifecycleOwner, Observer {
            for(c: CategoryModel in it ){
                Log.d("emo", "category: " + c.categoryName)
            }
        })



        initListener()
        initDatas()
        initAutoInputView(rootView)
        initAutoData()
        return rootView
    }

    fun bindToContentView(contentView: View){
        this.contentView = contentView
    }
    fun bindToCurrentConversation(chatModel: LibChatModel){
        mCurrentConversation = chatModel
    }


    protected  fun initView (rootView: View){
        emotionViewPager = rootView.findViewById(R.id.vp_emotionview_layout) as NoHorizontalScrollerViewPager
        emotion_recyclerview_horizontal = rootView.findViewById(R.id.emotion_recyclerview_horizontal) as RecyclerView
        bar_edit_text = rootView.findViewById(R.id.bar_edit_text) as EditText
        bar_image_add_btn = rootView.findViewById(R.id.bar_image_add_btn) as ImageView
        bar_btn_send = rootView.findViewById(R.id.bar_btn_send) as Button
        rl_editbar_bg = rootView.findViewById(R.id.rl_editbar_bg) as LinearLayout

        if(isHidenBarEditTextAndBtn){
            bar_edit_text.visibility = View.GONE
            bar_image_add_btn.visibility = View.GONE
            bar_btn_send.visibility = View.GONE
            rl_editbar_bg.setBackgroundResource(R.color.bg_edittext_color)
        }else{
            bar_edit_text.visibility = View.VISIBLE
         //   bar_image_add_btn.visibility = View.VISIBLE
         //   bar_btn_send.visibility = View.VISIBLE
            rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext)
        }
        Log.d("emo", "this is EmotionMainFragment initView")
    }

    protected fun initListener(){

    }

    @SuppressLint("ClickableViewAccessibility")
    protected  fun initDatas() {
        replaceFragment()
        Log.d("emo", "this is EmotionMainFragment initDatas, emotionFragmentsList = " + emotionFragmentsList.size)
        var list: ArrayList<ImageModel> = ArrayList()
        for (i in 0 until emotionFragmentsList.size step 1) {
            if (i == 0) {
                var model1: ImageModel = ImageModel()
                model1.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_emotion, null)
                model1.flag = "经典笑脸"
                model1.isSelected = true
                list.add(model1)
            } else {
                var model: ImageModel = ImageModel()
                model.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_plus, null)
                model.flag = "其他笑脸$i"
                model.isSelected = false
                list.add(model)
            }
        }
            Emotion_CurrentPosition = 0
            SharedPreferencedUtils.setInteger(mActivity, EMOTION_CURRENT_POSITION_FLAG, Emotion_CurrentPosition)


            mEmotionDelete.setOnTouchListener (View.OnTouchListener{view, motionEvent ->
                when(motionEvent.action){
                    MotionEvent.ACTION_DOWN->{

                    }
                    MotionEvent.ACTION_UP->{
                        view.setBackgroundResource(R.color.bg_delete_btn_normal)
                        view.setBackgroundResource(R.color.bg_delete_btn_clicked)
                        Log.d("emo", "the delete button is click")
                        if(!bar_edit_text.text.isEmpty()) {
                            bar_edit_text.dispatchKeyEvent(
                                KeyEvent(
                                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL
                                )
                            )
                        }

                    }
                }


                return@OnTouchListener true

            })


            mEmotionSend.setOnTouchListener(View.OnTouchListener{view, motionEvent ->
                when(motionEvent.action){
                    MotionEvent.ACTION_UP->{
                        view.setBackgroundResource(R.color.bg_send_btn_normal)
                        var contentMain = bar_edit_text.text.toString().trim {it <= ' '}
                        if (!TextUtils.isEmpty(contentMain)) {
                                    //    mAdapter.addMessage(contentMain, mCurrentChatConversation.id)
                                    //   hardcode hard
                            mMessageViewModel.insert(contentMain, mCurrentConversation)
                             bar_edit_text.setText("")
                 //           contentView.scrollToPosition(mAdapter.itemCount - 1)
                        }



                    }
                    MotionEvent.ACTION_DOWN->{
                        view.setBackgroundResource(R.color.bg_send_btn_clicked)
                    }
                }

                return@OnTouchListener true
            })

        horizontalRecyclerviewAdapter = HorizontalRecyclerviewAdapter(mActivity, list)
            emotion_recyclerview_horizontal.setHasFixedSize(true) //使RecyclerView保持固定的大小,这样会提高RecyclerView的性能

            emotion_recyclerview_horizontal.adapter = horizontalRecyclerviewAdapter
            emotion_recyclerview_horizontal.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
            //initialized recyclerview_horizontal

            horizontalRecyclerviewAdapter.setOnClickItemListener(object : OnClickItemListener {
                override fun onItemClick(view: View, position: Int, datas: ArrayList<ImageModel>) {

                    val oldPosition: Int = SharedPreferencedUtils.getInteger(mActivity, EMOTION_CURRENT_POSITION_FLAG, 0)
                    Log.d("emo", oldPosition.toString())

                    datas[oldPosition].isSelected = false

                    Emotion_CurrentPosition = position
                    datas[Emotion_CurrentPosition].isSelected = true
                    SharedPreferencedUtils.setInteger(mActivity, EMOTION_CURRENT_POSITION_FLAG, Emotion_CurrentPosition)
                    horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition)
                    horizontalRecyclerviewAdapter.notifyItemChanged(Emotion_CurrentPosition)
                    //viewpager界面切换
                    emotionViewPager.setCurrentItem(position, false)
                }

                override fun onItemLongClick(view: View, position: Int, datas: ArrayList<ImageModel>) {

                }


            })



    }


    private fun initAutoInputView(rootView:View){
        var b = Bundle()
    /*    b.putParcelable("root",rootView)
     */   var AutoInput : AutoInputFragment = AutoInputFragment().newInstance(AutoInputFragment::class.java, b)

        autoViewPager = rootView.findViewById(R.id.vp_autoInput_layout) as NoHorizontalScrollerAutoInputViewPager
        auto_recyclerview_horizontal = rootView.findViewById(R.id.auto_recyclerview_horizontal) as RecyclerView


    }

    private fun initAutoData(){
        AutoInputReplaceFragment()
        var list = ArrayList<CategoryModel>()
        mCategoryList.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                // add + icon to list
            }else {
                for (c: CategoryModel in it) {
                    list.add(c)
                }
                Log.d("emo", "inside initAutoData.observe: " + list.size)

            }


            Auto_CurrentPosition = 0

            SharedPreferencedUtils.setInteger(mActivity, AUTO_CURRENT_POSTION_FLAG, Auto_CurrentPosition)


            mHorizontalRecyclerAutoInputViewAdapter = HorizontalRecyclerAutoInputViewAdapter(mActivity, list)
            auto_recyclerview_horizontal.setHasFixedSize(true)
            auto_recyclerview_horizontal.adapter = mHorizontalRecyclerAutoInputViewAdapter
            auto_recyclerview_horizontal.layoutManager = GridLayoutManager(mActivity, 1, GridLayoutManager.HORIZONTAL, false)

            mHorizontalRecyclerAutoInputViewAdapter.setOnClickItemListener(object :
                HorizontalRecyclerAutoInputViewAdapter.OnClickItemAutoListener {
                override fun onItemClick(
                    view: View,
                    position: Int,
                    datas: ArrayList<CategoryModel>
                ) {
                    var oldPosition : Int = SharedPreferencedUtils.getInteger(mActivity, AUTO_CURRENT_POSTION_FLAG,0)

                    Auto_CurrentPosition = position

                    SharedPreferencedUtils.setInteger(mActivity, AUTO_CURRENT_POSTION_FLAG, Auto_CurrentPosition)
                    mHorizontalRecyclerAutoInputViewAdapter.notifyItemChanged(oldPosition)
                    mHorizontalRecyclerAutoInputViewAdapter.notifyItemChanged(Auto_CurrentPosition)
                    //viewpager界面切换
                    autoViewPager.setCurrentItem(position, false)

                }

                override fun onItemLongClick(
                    view: View,
                    position: Int,
                    datas: ArrayList<CategoryModel>
                ) {

                }

            })


        })




    }
    private fun AutoInputReplaceFragment(){
        var factory: com.somoplay.wefungame.libChat.messagePage.AutoInput.fragment.FragmentFactory = com.somoplay.wefungame.libChat.messagePage.AutoInput.fragment.FragmentFactory.getSingleFactoryINstance()

        mCategoryList.observe(viewLifecycleOwner, Observer {
            for(c: CategoryModel in it) {
                var f1: AutoInputFragment = factory.getFragment(c.id) as AutoInputFragment
                autoInputFragmentsList.add(f1)
            }
            Log.d("emo", "inside autoInptreplaceFragment.observe: " + autoInputFragmentsList.size)
            var autoInputAdapter : NoHorizontalScrollerVPAutoInputAdapter = NoHorizontalScrollerVPAutoInputAdapter((mActivity as AppCompatActivity).supportFragmentManager, autoInputFragmentsList)
            autoViewPager.adapter = autoInputAdapter
        })

        Log.d("emo", "outside autoInputreplaceFragment.observe: " + autoInputFragmentsList.size)

        /*var autoInputAdapter : NoHorizontalScrollerVPAutoInputAdapter = NoHorizontalScrollerVPAutoInputAdapter((mActivity as AppCompatActivity).supportFragmentManager, autoInputFragmentsList)
        autoViewPager.adapter = autoInputAdapter*/
    }
    private fun replaceFragment(){
        var factory: FragmentFactory = FragmentFactory.getSingleFactoryInstance()
        var f1 : EmotionComplateFragment = factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE) as EmotionComplateFragment
        emotionFragmentsList.add(f1)
        lateinit var b: Bundle
        for(i: Int in 0 until 7 step 1){
            b = Bundle()
            b.putString("Interge", "fragment-$i")
            var fg: Fragment1 = Fragment1().newInstance(Fragment1::class.java,b)
            emotionFragmentsList.add(fg)
        }

        var adapter: NoHorizontalScrollerVPAdapter = NoHorizontalScrollerVPAdapter((mActivity as AppCompatActivity).supportFragmentManager,emotionFragmentsList )
        emotionViewPager.adapter = adapter
    }

    fun isInterceptBackPress(): Boolean{
        return mEmotionKeyboard.interceptBackPress()
    }

}