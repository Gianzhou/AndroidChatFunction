package com.somoplay.wefungame.libChat.messagePage

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.adapter.LibChatGridViewAdapter
import com.somoplay.wefungame.libChat.messageCell.LibMessageItemClickListener
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.libChat.model.LibMessageModel
import com.somoplay.wefungame.libChat.viewModel.MessageViewModel
import com.somoplay.wefungame.pageInit.basicPage.LibBaseBackPage
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText
import me.yokeyword.fragmentation.SupportHelper
import java.util.*


class LibMessagePageV1: LibBaseBackPage(), LibMessageItemClickListener {

    //private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mChatRecyclerView: RecyclerView
    private lateinit var mVoiceButton: Button
    private lateinit var mSendButton: Button

    private lateinit var mCurrentChatConversation : LibChatModel
    private lateinit var mMessageList: LiveData<MutableList<LibMessageModel>>

    private lateinit var mSendAddListener: ImageView

    private lateinit var mInputSwitcher: ImageView // voice or text input switcher
    private lateinit var mkeyboardSwitcher: ImageView
    private lateinit var mEmojiSwitcher: ImageView //emoji

    private lateinit var mEmojiconEditText: EmojiconEditText
    private lateinit var mEmojiconAction : EmojIconActions //emoji controller

    private lateinit var mGridView : GridView

    private lateinit var mMessageViewModel: MessageViewModel
    private lateinit var mAdapter: LibMessageAdapter

    private lateinit var mCoverView : View
    private lateinit var mEmojiCoverView : ImageView



    //private val publishSubject : PublishSubject<String>

    //temperate
    private  var mVoiceInput = false // to record the mode to input switcher
    private  var mshowGrid = false


    private var mTitle: Int = 0

    companion object {
        private const val titleKey = "titleKey"
        fun newInstance(title: Int, chatConversation : LibChatModel): LibMessagePageV1 {
            val bundle = Bundle()
            bundle.putInt(titleKey, title)

            val fragment = LibMessagePageV1()
            fragment.arguments = bundle
            fragment.mTitle = title
            fragment.mCurrentChatConversation = chatConversation
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            mTitle = args.getInt(titleKey)
        }

      /*  context?.let{

            mMessageViewModel = MessageViewModel(it, mCurrentChatConversation.id)
        }*/

        mMessageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)


        mMessageList = mMessageViewModel.getAllChat(mCurrentChatConversation.id)
        Log.d("LibMessagePage", "onCreate")
        Log.d("LibMessagePage", mMessageList.toString())
        var testlist : List<LibMessageModel>
        /*mChatRecyclerView.setHasFixedSize(true)
        mChatRecyclerView.setItemAnimator(DefaultItemAnimator())*/




    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("LibMessagePage", "onCreateView")
        return initViewFromXml(inflater, container)
    }

    private fun initViewFromXml(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.lib_chat_detail, container, false)
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        //toolbar.setTitleTextColor(Color.BLACK)
        toolbar.setTitle(mTitle)
        initToolbarNav(toolbar)

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

        var itemsAdapter = LibChatGridViewAdapter(this.context!!, this.getGridFeaturesList())
        mGridView.adapter = itemsAdapter

        mChatRecyclerView.layoutManager = LinearLayoutManager(activity)

        mSendAddListener.setOnClickListener(sendAddListener())

        mEmojiCoverView.setOnClickListener(emojiIconClick())
        //click event
        mInputSwitcher.setOnClickListener(inputSwitcher())

        mSendButton.setOnClickListener(sendMessage())

        // initial view in lower layout
        mEmojiconAction = EmojIconActions(this.context, view, mEmojiconEditText,mEmojiSwitcher,
                "#495c66", "#DCE1E2", "#E6EBEF")
        mEmojiconAction.ShowEmojIcon()
        mEmojiconAction.addEmojiconEditTextList()
        mEmojiconAction.setIconsIds(R.drawable.tool_view_keyboard_at_3x, R.drawable.tool_view_emotion_at_3x)
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
            /*    mCallonClick = true
                mInputSwitcher.callOnClick()
                mCallonClick = false*/

                /*mInputSwitcher.setImageResource(R.drawable.tool_view_keyboard_at_3x)
                mVoiceInput = false*/
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

    private fun sendMessage(): View.OnClickListener{
        return View.OnClickListener {
            val contentMain = mEmojiconEditText.text.toString().trim({ it <= ' ' })
            if (TextUtils.isEmpty(contentMain)) return@OnClickListener
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
            mEmojiSwitcher.visibility = View.VISIBLE
            mEmojiCoverView.visibility = View.GONE

            mVoiceButton.visibility = View.GONE
            mEmojiconEditText.visibility = View.VISIBLE
            mInputSwitcher.callOnClick()
            mEmojiSwitcher.callOnClick()

        }
    }
    private fun sendAddListener() : View.OnClickListener{
        return View.OnClickListener {
            if(mVoiceInput)
                mInputSwitcher.callOnClick()
            SupportHelper.hideSoftInput(mEmojiconEditText)
    //        SupportHelper.hideSoftInput(mEmojiconEditText)// hide keyboard
            val  r = Runnable {
                val message = mEmojiconEditText.text.toString()
                if(0 != message.length) {
                    mEmojiconEditText.setText("")
                }

                if (mGridView.visibility != View.VISIBLE) {
                    mGridView.visibility = View.VISIBLE
                    mCoverView.visibility = View.VISIBLE
                }else {
                    mGridView.visibility = View.GONE
                    mCoverView.visibility = View.GONE
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
                mEmojiSwitcher.visibility = View.VISIBLE
                SupportHelper.showSoftInput(mEmojiconEditText)
                false
            }else{// switch to voice input
                mInputSwitcher.setImageResource(R.drawable.tool_view_keyboard_at_3x)
                mVoiceButton.visibility = View.VISIBLE
                mEmojiconEditText.visibility = View.GONE

                mEmojiSwitcher.visibility = View.GONE
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

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        Log.d("LibMessagePage", "onLazyInitView")
        var testlist : List<LibMessageModel>
        mChatRecyclerView.setHasFixedSize(true)
        mChatRecyclerView.setItemAnimator(DefaultItemAnimator())
       /* mMessageList.observe(this, Observer {
            *//*mAdapter = LibMessageAdapter(it,this, mCurrentChatConversation.userId, mCurrentChatConversation.targetId)*//*


            *//*mAdapter = LibMessageAdapter(context, this)*//*
            context?.let{
                mAdapter = LibMessageAdapter(it, this)
            }
            testlist = it
            Log.d("LibMessagePage", testlist.toString())
            mAdapter.setData(it)
            mChatRecyclerView.adapter = mAdapter
        })*/
        mMessageList.observe(this, Observer { messages ->
            /*mAdapter = LibMessageAdapter(it,this, mCurrentChatConversation.userId, mCurrentChatConversation.targetId)*/


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

//    override fun onEnterAnimationEnd(savedInstanceState: Bundle) {
//        super.onEnterAnimationEnd(savedInstanceState)
//    }

    override fun messageItemClicked(list: LibMessageModel){

    }

    object LibChatGridFeatureList {
        val featureList = arrayOf(
            LibChatGridViewItem("Image",R.drawable.img_chat_add_image),
            LibChatGridViewItem("Photo",R.drawable.img_chat_add_camera),
            LibChatGridViewItem("Location",R.drawable.img_chat_add_location),
            LibChatGridViewItem("File",R.drawable.img_chat_add_file),
            LibChatGridViewItem("Name Card",R.drawable.img_chat_add_namecard),
            LibChatGridViewItem("Lucky Money", R.drawable.img_chat_add_luckymoney)

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

