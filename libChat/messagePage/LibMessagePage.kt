package com.somoplay.wefungame.libChat.messagePage

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messageCell.LibMessageItemClickListener
import com.somoplay.wefungame.libChat.messagePage.emoji.fragment.EmotionMainFragment
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.libChat.model.LibMessageModel
import com.somoplay.wefungame.libChat.viewModel.MessageViewModel
import com.somoplay.wefungame.pageInit.basicPage.LibBaseBackPage
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText
import me.yokeyword.fragmentation.SupportHelper
import java.util.*


class LibMessagePage: LibBaseBackPage(), LibMessageItemClickListener {

    //private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mChatRecyclerView: RecyclerView
    private lateinit var mVoiceButton: Button
    private lateinit var mSendButton: Button

    private lateinit var mCurrentChatConversation : LibChatModel
    private lateinit var mMessageList: LiveData<MutableList<LibMessageModel>>

    private lateinit var mInputSwitcher:    ImageView // text input
    private lateinit var mEmotionInput:     ImageView
    /*private lateinit var mEmojiSwitcher:    ImageView //emoji*/
    private lateinit var mSpeakInput:       ImageView //voice input
    private lateinit var mAutomaticInput:   ImageView //
    private lateinit var mAddMore:          ImageView
    private lateinit var mSpeaker:          ImageView
    private lateinit var mEmotionInputCover: ImageView //cover
    private lateinit var mEmotionInputFrontCover: ImageView //front cover

    private lateinit var mSpeaker_layout: LinearLayout

    private lateinit var mEmojiconEditText: EmojiconEditText
    private lateinit var mEmojiconAction : EmojIconActions //emoji controller

    private lateinit var mAutomaticLayout: RelativeLayout  // Automatic
    private lateinit var mGridView : GridView //add more

    private lateinit var mMessageViewModel: MessageViewModel
    private lateinit var mAdapter: LibMessageAdapter

    private lateinit var mCoverView : View
    private lateinit var mEmojiCoverView : ImageView


    private var mMoreClicked      = false
    private var mSpeakClicked     = false
    private var mAutomaticClicked = false
    private var mStickerClicked   = false
    private var mClickfromEditText = true

private lateinit var mtest : ImageView

    //private val publishSubject : PublishSubject<String>

    //temperate
    private  var mVoiceInput = false // to record the mode to input switcher
    private  var mshowGrid = false

    private lateinit var mActivity : Activity
    private lateinit var emotionMainFragment: EmotionMainFragment
    private var mTitle: Int = 0

    companion object {
        private const val titleKey = "titleKey"
        private lateinit var mClickedMessage: LibMessagePage
        fun newInstance(title: Int, chatConversation : LibChatModel): LibMessagePage {
            val bundle = Bundle()
            bundle.putInt(titleKey, title)

            mClickedMessage = LibMessagePage()
            mClickedMessage.arguments = bundle
            mClickedMessage.mTitle = title
            mClickedMessage.mCurrentChatConversation = chatConversation
            return mClickedMessage
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////
        arguments?.let { args ->
            mTitle = args.getInt(titleKey)
        }

      /*  context?.let{

            mMessageViewModel = MessageViewModel(it, mCurrentChatConversation.id)
        }*/

        mActivity =requireActivity()
        mMessageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        mMessageList = mMessageViewModel.getAllChat(mCurrentChatConversation.id)
        Log.d("LibMessagePage", "onCreate")
        Log.d("LibMessagePage", mMessageList.toString())
        var testlist : List<LibMessageModel>
       /* mChatRecyclerView.setHasFixedSize(true)
        mChatRecyclerView.setItemAnimator(DefaultItemAnimator())*/




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("LibMessagePage", "onCreateView")
        var view = inflater.inflate(R.layout.lib_chat_detail_v3, container, false)
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(mTitle)

        initToolbarNav(toolbar)
        mChatRecyclerView = view.findViewById(R.id.chat_detail_recycler_view) as RecyclerView
        mChatRecyclerView.layoutManager = LinearLayoutManager(activity)
        initEmotionMainFragment()

        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViewFromXml(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.lib_chat_detail_v3, container, false)
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        //toolbar.setTitleTextColor(Color.BLACK)
        toolbar.setTitle(mTitle)
        initToolbarNav(toolbar)

        //initialize recyclerview



        initEmotionMainFragment()

       /*  attachKeyboardListeners(view.findViewById(R.id.rootLayout))
        //Emoji
        mEmojiconEditText = view.findViewById(R.id.emojicon_edit_text) as EmojiconEditText
        mEmotionInput   = view.findViewById(R.id.emotion_input) as ImageView
        mSpeakInput     = view.findViewById(R.id.speak_input)   as ImageView
        mAutomaticInput = view.findViewById(R.id.automatic)     as ImageView
        mAddMore        = view.findViewById(R.id.addMore)       as ImageView
        mSpeaker        = view.findViewById(R.id.speaker)       as ImageView
        mEmotionInputCover = view.findViewById(R.id.emotion_input_cover) as ImageView
        mEmotionInputFrontCover = view.findViewById(R.id.emotion_input_front_cover) as ImageView

        mSpeaker_layout = view.findViewById(R.id.speak_layout) as LinearLayout

        // automatic
        mAutomaticLayout = view.findViewById(R.id.Automatic_layout) as RelativeLayout

        //GridView /add more
        mGridView       = view.findViewById(R.id.gridview)      as GridView
        val itemsAdapter = LibChatGridViewAdapter(this.requireContext(), this.getGridFeaturesList())
        mGridView.adapter = itemsAdapter



        //set click even for add more
        mAddMore.setOnClickListener(sendAddListener())
        mSpeakInput.setOnClickListener(speakClickEvent())
        mAutomaticInput.setOnClickListener(automaticClickEvent())
        mEmotionInputCover.setOnClickListener(emotionClickEvent())
        mEmotionInputFrontCover.setOnClickListener(emotionFrontClickEvent())
        mSpeaker.setOnTouchListener { v, event ->
            val action = event.action
            when(action){

                MotionEvent.ACTION_DOWN -> {
                    mSpeaker.setImageResource(R.drawable.speaking)
                    mSpeaker.setBackgroundResource(R.drawable.holding_speaker)
                }


                MotionEvent.ACTION_MOVE -> { }

                MotionEvent.ACTION_UP -> {
                    mSpeaker.setImageResource(R.drawable.holdtospeak)
                    mSpeaker.setBackgroundResource(R.drawable.unhold_speaker)
                }

                MotionEvent.ACTION_CANCEL -> {

                }

                else ->{

                }
            }
            true
        }

        mEmojiconEditText.setOnEditorActionListener{v, actionId, event->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                Log.d("emo", "this is done")
                sendMessage()
                SupportHelper.hideSoftInput(mEmojiconEditText)
                *//*mEmojiconAction.closeEmojIcon()*//*
                true
            }else{
                false
            }
        }

        // initial Emojicon view in lower layout
        mEmojiconAction = EmojIconActions(this.context, view, mEmojiconEditText,mEmotionInput,
            "#495c66", "#DCE1E2", "#E6EBEF")

     //   mEmojiconAction.addEmojiconEditTextList()
     //   mEmojiconAction.setUseSystemEmoji(true)

        mEmojiconAction.setIconsIds( R.drawable.stickers_selected, R.drawable.stickers_normal)
        _mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        *//*mCoverView.setOnClickListener(recycleViewClick())*//*
        mChatRecyclerView.setHasFixedSize(true)
        mEmojiconAction.ShowEmojIcon()
        mEmojiconAction.setKeyboardListener(object : EmojIconActions.KeyboardListener{
            override fun onKeyboardClose() {
        //    mEmojiconAction.closeEmojIcon()
                if(mStickerClicked) {
                    mStickerClicked = false
                *//*    mEmotionInput.visibility = View.GONE
                    mEmotionInputFrontCover.visibility = View.VISIBLE
*//*
                }
                Log.d("emo", "onkeyboardclose()")
            }

            override fun onKeyboardOpen() {
                Log.d("emo", "onkeyboardopen()")

                if(!mStickerClicked) {
                    if(mMoreClicked){
                        mAddMore.callOnClick()
                    }
                    if(mSpeakClicked){
                        mSpeakInput.callOnClick()
                    }
                    if(mAutomaticClicked){
                        mAutomaticInput.callOnClick()
                    }
                   *//* mEmotionInput.visibility = View.VISIBLE
                    mEmotionInputFrontCover.visibility= View.GONE*//*



                    mStickerClicked = true
                }
            }
        })

*//*

        Log.d("LibMessagePage", "initViewFromXml")
        mSendButton = view.findViewById(R.id.chat_send_button) as Button
      //  mSendEditText = view.findViewById(R.id.chat_edit_text_send) as EditText
        mSendAddListener = view.findViewById(R.id.addMore) as ImageView
        //mRefreshLayout = view.findViewById(R.id.refresh_layout) as SwipeRefreshLayout
        mChatRecyclerView = view.findViewById(R.id.chat_detail_recycler_view) as RecyclerView

        mCoverView = view.findViewById(R.id.coverView) as View
        mEmojiCoverView = view.findViewById(R.id.emotion_keyboard_cover_view) as ImageView
        //voice_text_switcher
        mInputSwitcher = view.findViewById(R.id.voice_text_input_switcher) as ImageView
        mkeyboardSwitcher = view.findViewById(R.id.emotion_keyboard_input_switcher) as ImageView

        //Emoji & keyboard Input
        mEmojiSwitcher = view.findViewById(R.id.emotion_keyboard_input_switcher) as ImageView
        mEmojiconEditText = view.findViewById(R.id.emojicon_edit_text)
        //GridView
        mGridView = view.findViewById(R.id.gridview)

        mSendButton = view.findViewById(R.id.chat_send_button) as Button
        mVoiceButton = view.findViewById(R.id.chat_voice_button) as Button




        mSendAddListener.setOnClickListener(sendAddListener())

        mEmojiCoverView.setOnClickListener(emojiIconClick())
        //click event
        mInputSwitcher.setOnClickListener(inputSwitcher())

        mSendButton.setOnClickListener(sendMessage())


        mInputSwitcher.setImageResource(R.drawable.tool_view_input_voice_at_3x)

        _mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        mCoverView.setOnClickListener(recycleViewClick())
        mChatRecyclerView.setHasFixedSize(true)

        //mCoverView.callOnClick()
     //   mChatRecyclerView.setOnClickListener(recycleViewClick())
        mEmojiconAction.setKeyboardListener(object : EmojIconActions.KeyboardListener{
            override fun onKeyboardClose() {

                Log.d("emo", "onkeyboardclose()")
            }

            override fun onKeyboardOpen() {
            *//*
*//*    mCallonClick = true
                mInputSwitcher.callOnClick()
                mCallonClick = false*//**//*


                *//*
*//*mInputSwitcher.setImageResource(R.drawable.tool_view_keyboard_at_3x)
                mVoiceInput = false*//**//*

                mVoiceButton.visibility = View.GONE
                mEmojiconEditText.visibility = View.VISIBLE
                mChatRecyclerView.smoothScrollToPosition(0)
                mGridView.visibility = View.GONE
                mCoverView.visibility = View.VISIBLE
                Log.d("emo", "onkeyboardopen()")
            }
        })

        mEmojiconEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().trim().isNotEmpty()){
                    mSendAddListener.visibility = View.GONE
                    mSendButton.visibility = View.VISIBLE

                }else{
                    mSendButton.visibility = View.GONE
                    mSendAddListener.visibility = View.VISIBLE
                }

            }

        })
*//*
*/
        return attachToSwipeBack(view)
    }



    private fun recycleViewClick(): View.OnClickListener{
        return View.OnClickListener {
            Log.d("LibMessagePage", "Clicking on RecyclerView")
            mGridView.visibility = View.GONE
            SupportHelper.hideSoftInput(mEmojiconEditText)
            mCoverView.visibility = View.GONE
        }
    }

    /*private fun speakerClickEvent(): View.OnTouchListener{
        return View.OnTouchListener {

        }
    }*/
    private fun emotionClickEvent():View.OnClickListener{
        return View.OnClickListener {
            Log.d("emo", "emotionClickEvent")
            mEmotionInput.visibility = View.VISIBLE
            mEmotionInputCover.visibility = View.GONE
            SupportHelper.hideSoftInput(mEmojiconEditText)

        }
    }

    private fun emotionFrontClickEvent():View.OnClickListener{
        return View.OnClickListener {
            mEmojiconAction.ShowEmojIcon()
            if(mMoreClicked){
                mAddMore.callOnClick()
            }
            if(mSpeakClicked){
                mSpeakInput.callOnClick()
            }
            if(mAutomaticClicked){
                mAutomaticInput.callOnClick()
            }
            mEmotionInputFrontCover.visibility = View.GONE
            mEmotionInput.visibility = View.VISIBLE
            mEmotionInput.callOnClick()
        }
    }
    private fun speakClickEvent():View.OnClickListener{
        return View.OnClickListener {
            if (mStickerClicked) {
//
//                mInputSwitcher.callOnClick()
                SupportHelper.hideSoftInput(mEmojiconEditText)
                mEmojiconAction.closeEmojIcon()
            }
            if (mAutomaticClicked) {
                mAutomaticInput.callOnClick()
            }
            if (mMoreClicked) {
                mAddMore.callOnClick()
            }

            val r = Runnable {

                if (!mSpeakClicked) {
                    mSpeakInput.setImageResource(R.drawable.speak_selected)
                    mSpeakClicked = true
                    mSpeaker_layout.visibility = View.VISIBLE
                } else {
                    mSpeakInput.setImageResource(R.drawable.speak_normal)
                    mSpeakClicked = false
                    mSpeaker_layout.visibility = View.GONE
                }
            }
            var h = Handler()
            h.postDelayed(r, 100)


        }
    }
    private fun automaticClickEvent(): View.OnClickListener{
        return View.OnClickListener {
            if(mStickerClicked) {
//
//                mInputSwitcher.callOnClick()
                SupportHelper.hideSoftInput(mEmojiconEditText)
                mEmojiconAction.closeEmojIcon()
            }
            if(mSpeakClicked){
                mSpeakInput.callOnClick()
            }
            if(mMoreClicked){
                mAddMore.callOnClick()
            }

            val r = Runnable {
                if (!mAutomaticClicked) { //clicked
                    mAutomaticInput.setImageResource(R.drawable.automatic_selected)
                    mAutomaticClicked = true
                    mAutomaticLayout.visibility = View.VISIBLE



                } else {
                    mAutomaticInput.setImageResource(R.drawable.automatic_normal)
                    mAutomaticClicked = false
                    mAutomaticLayout.visibility = View.GONE
                }
            }
            var h = Handler()
            h.postDelayed(r, 100)

        }
    }

    private fun sendMessage(){

            val contentMain = mEmojiconEditText.text.toString().trim({ it <= ' ' })
            if (!TextUtils.isEmpty(contentMain)) {
                //    mAdapter.addMessage(contentMain, mCurrentChatConversation.id)
                //   hardcode hard
                mMessageViewModel.insert(contentMain, mCurrentChatConversation)
                mEmojiconEditText.setText("")
                mChatRecyclerView.scrollToPosition(mAdapter.itemCount - 1)
            }


    }
    private fun emojiIconClick(): View.OnClickListener{
        return View.OnClickListener {
            Log.d("LibMessage", "the emoji icon clicked")
            /*mEmojiSwitcher.visibility = View.VISIBLE*/
            mEmojiCoverView.visibility = View.GONE

            mVoiceButton.visibility = View.GONE
            mEmojiconEditText.visibility = View.VISIBLE
            mInputSwitcher.callOnClick()
           /* mEmojiSwitcher.callOnClick()*/

        }
    }
    private fun sendAddListener() : View.OnClickListener{
        return View.OnClickListener {
            if(mStickerClicked) {
//
//                mInputSwitcher.callOnClick()
                SupportHelper.hideSoftInput(mEmojiconEditText)
                mEmojiconAction.closeEmojIcon()
            }
            if(mSpeakClicked){
                mSpeakInput.callOnClick()
            }
            if(mAutomaticClicked){
                mAutomaticInput.callOnClick()
            }
    //        SupportHelper.hideSoftInput(mEmojiconEditText)// hide keyboard

            val  r = Runnable {
                val message = mEmojiconEditText.text.toString()
                if(0 != message.length) {
                    mEmojiconEditText.setText("")
                }

                if (mGridView.visibility != View.VISIBLE) {

                    mAddMore.setImageResource(R.drawable.more_selected)
                    mGridView.visibility = View.VISIBLE
                    mMoreClicked = true
                   /* mCoverView.visibility = View.VISIBLE*/
                }else {
                    mAddMore.setImageResource(R.drawable.more)
                    mGridView.visibility = View.GONE
                    mMoreClicked = false

                    /*mCoverView.visibility = View.GONE*/
                }

                mGridView.requestFocus()


            }

            val h = Handler()
            h.postDelayed(r, 100)
        }

    }
    private fun inputSwitcher():View.OnClickListener{
        return View.OnClickListener {
        //    mInputSwitcher.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.tool_view_keyboard_at_3x))
            mVoiceInput = if (mVoiceInput) {//switch to keyboard input
                mInputSwitcher.setImageResource(R.drawable.tool_view_input_voice_at_3x)
                mVoiceButton.visibility = View.GONE
                mEmojiconEditText.visibility = View.VISIBLE

                mEmojiCoverView.visibility = View.GONE
                /*mEmojiSwitcher.visibility = View.VISIBLE*/
                SupportHelper.showSoftInput(mEmojiconEditText)
                false
            }else{// switch to voice input
                mInputSwitcher.setImageResource(R.drawable.tool_view_keyboard_at_3x)
                mVoiceButton.visibility = View.VISIBLE
                mEmojiconEditText.visibility = View.GONE

                /*mEmojiSwitcher.visibility = View.GONE*/
                mEmojiCoverView.visibility = View.VISIBLE

                SupportHelper.hideSoftInput(mEmojiconEditText)

                true
            }
            Log.d("emo", mVoiceInput.toString())

        }
    }

    private fun getGridFeaturesList(): ArrayList<LibChatGridViewItem>{
        val featureList = ArrayList<LibChatGridViewItem>()
        for(feature in LibChatGridFeatureList.featureList){
            if (feature.getImageResource()!= null)
                featureList.add(feature)
        }
        return featureList
    }

    /**
     * 初始化表情面板
     */
    fun initEmotionMainFragment() {
        //构建传递参数
        val bundle = Bundle()
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true)
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false)
        //替换fragment
        //创建修改实例
         emotionMainFragment =
            EmotionMainFragment().newInstance(EmotionMainFragment::class.java, bundle) as EmotionMainFragment
        emotionMainFragment.bindToContentView(mChatRecyclerView)
        emotionMainFragment.bindToCurrentConversation(mCurrentChatConversation)
        val fragmentManger = requireActivity().supportFragmentManager
        val transaction: FragmentTransaction =
           requireActivity().supportFragmentManager.beginTransaction()
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.emotionview_main, emotionMainFragment)
     //   transaction.addToBackStack(null)
        //提交修改
        transaction.commit()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        Log.d("LibMessagePage", "onLazyInitView")
        var testlist : List<LibMessageModel>
        mChatRecyclerView.setHasFixedSize(true)
        mChatRecyclerView.setItemAnimator(DefaultItemAnimator())
       /* mMessageList.observe(this, Observer {
            mAdapter = LibMessageAdapter(it,this, mCurrentChatConversation.userId, mCurrentChatConversation.targetId)


            mAdapter = LibMessageAdapter(context, this)
            context?.let{
                mAdapter = LibMessageAdapter(it, this)
            }
            testlist = it
            Log.d("LibMessagePage", testlist.toString())
            mAdapter.setData(it)
            mChatRecyclerView.adapter = mAdapter
        })*/
        mMessageList.observe(this, Observer { messages ->


            context?.let{
                mAdapter = LibMessageAdapter(it, messages, mCurrentChatConversation.userId, this)
            }
            mAdapter.setData(messages)

            testlist = messages
            Log.d("LibMessagePage", testlist.toString())
            mAdapter.setData(messages)
            mChatRecyclerView.adapter = mAdapter
        })


        /*   mSendButton.setOnClickListener(View.OnClickListener {
               val contentMain = mSendEditText.text.toString().trim({ it <= ' ' })
               if (TextUtils.isEmpty(contentMain)) return@OnClickListener
               val messageModel = LibMessageModel(null, "mike", "","","","","","","",
                   contentMain,"","",false,1, Date(),false)
               mAdapter.addMessage(messageModel)
               mSendEditText.setText("")
               mChatRecyclerView.scrollToPosition(mAdapter.itemCount - 1)
           })
   */
        /*
        val messageModel = LibMessageModel(null, "mike", "","","","","","","",
            "test test test ","","",false,1, Date(),false)

        mAdapter.addMessage(messageModel)

         */
    }


     override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)
         /*if(mSpeakClicked){
             mSpeakInput.callOnClick()
         }
         if(mAutomaticClicked){
             mAutomaticInput.callOnClick()
         }
         if(mMoreClicked){
             mAddMore.callOnClick()
         }*/
        Log.d("emo", "the soft keyboard is show")

    }

     override fun onHideKeyboard() {
        super.onHideKeyboard()
        Log.d("emo", "the soft keyboard is hide")
    }

//    override fun onEnterAnimationEnd(savedInstanceState: Bundle) {
//        super.onEnterAnimationEnd(savedInstanceState)
//    }

    override fun messageItemClicked(list: LibMessageModel){

    }


    override fun onBackPressedSupport(): Boolean {
        Log.d("emo", "this is onBackPressedSupport")
        if(!emotionMainFragment.isInterceptBackPress())
            return false
        return true
    }

    object LibChatGridFeatureList {
        val featureList = arrayOf(
            LibChatGridViewItem("album",R.drawable.album),
            LibChatGridViewItem("camera",R.drawable.camera),
            LibChatGridViewItem("Location",R.drawable.locate)
            /*LibChatGridViewItem("File",R.drawable.img_chat_add_file),
            LibChatGridViewItem("Name Card",R.drawable.img_chat_add_namecard),
            LibChatGridViewItem("Lucky Money", R.drawable.img_chat_add_luckymoney)
*/
        )
        // Get Features:
        fun getFeature(featureName: String): LibChatGridViewItem? {
            // to detect if the feature is enabled, return null if is not enable
            for (feature in featureList) {
                if (feature.getName() == featureName) return feature
            }
            return null
        }
    }
}

