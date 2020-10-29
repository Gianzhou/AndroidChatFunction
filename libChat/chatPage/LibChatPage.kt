package com.somoplay.wefungame.libChat.chatPage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.LibMessagePage
import com.somoplay.wefungame.libChat.viewModel.ChatViewModel
import com.somoplay.wefungame.libChat.viewModel.MessageViewModel
import com.somoplay.wefungame.main.CurrentUser
import com.somoplay.wefungame.pageInit.basicPage.LibMainTabBasePage
import com.somoplay.wefungame.pageInit.tabBar.LibMainTabFragment

/*
hard coding the data now, (use another to handle the connection with the web server, this class invokes that class and user class(singleton) to get the message info and current user info)
 */
class LibChatPage: LibMainTabBasePage(), LibChatAdapter.LibChatItemClickListener {

    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mChatRecyclerView: RecyclerView
    private lateinit var mAdapter: LibChatAdapter
    private lateinit var mChatViewModel: ChatViewModel
    private lateinit var mMessageViewModel: MessageViewModel
    private val mCurrentUser= CurrentUser.instance

    private var mTitle: Int = 0
    private var count: Int = 0
    private var count2: Int = 0
    private var chatId: Long = 0
    private lateinit var mChatList: LiveData<MutableList<LibChatModel>>

    private lateinit var receiver: BroadcastReceiver
    private lateinit var intentFilter: IntentFilter
    private lateinit var mContext: Context

    companion object {
        private const val titleKey = "titleKey"

        fun newInstance(title: Int): LibChatPage {
            val bundle = Bundle()
            bundle.putInt(titleKey, title)

            val fragment = LibChatPage()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            mTitle = args.getInt(titleKey)
        }
        mContext = this.requireContext()
        mChatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        mMessageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        mChatList = mChatViewModel.getAllChat(mCurrentUser.userId)


        intentFilter = IntentFilter("com.someplay.wefungame.DATA_UPDATA")
        /* intentFilter.addAction()*/
        receiver = object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    if(intent.action.equals("com.someplay.wefungame.DATA_UPDATA")){
                        chatId = intent.getLongExtra("chatId",1)
                        //change one chat at a time
                        update(chatId)

                       /* Toast.makeText(context, "SOME_ACTION is RECEVED", Toast.LENGTH_LONG).show()*/
                    }

                }
                /*Log.d("ConnectionReceiver", "this is receiver")*/
            }
        }


    /*    mChatList = mChatViewModel.getAllChat(mCurrentUser.userId)*/
        Log.d("LibChatPage", mCurrentUser.lastName)


        val intent = Intent("com.someplay.wefungame.DATA_UPDATA")
        context?.let{LocalBroadcastManager.getInstance(it).sendBroadcast(intent)
            it.sendBroadcast(intent)}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("LibChatPage", "onCreateView")
        return initViewFromXml(inflater, container)
    }

    private fun initViewFromXml(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.lib_list_fragment, container, false)
    //    view.setBackgroundColor(Color.parseColor("#888888"))
    //    view.setBackgroundResource(R.drawable.background_game)
        view.setBackgroundColor(Color.WHITE)

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        //toolbar.setTitleTextColor(Color.BLACK)
        toolbar.setTitle(mTitle)

        mRefreshLayout = view.findViewById(R.id.refresh_layout) as SwipeRefreshLayout
        mChatRecyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        mChatRecyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        Log.d("LibChatPage", "onLazyInitView")
        mChatViewModel.getAllChat(mCurrentUser.userId).observe(this, Observer {
            mAdapter = LibChatAdapter(it,this, mChatViewModel, mMessageViewModel, mContext)
            mChatRecyclerView.adapter = mAdapter
            Log.d("LibChatPage", "heollo")
            /*for(chat in it){
                mChatViewModel.update(chat)
            }*/
        })

    //    Log.d("LibChatPage", mLibMain.getChatModelList().toString())
    }

    override fun chatItemClicked(list: LibChatModel){
        //the tab should be the user name (not fixed)
        //issue: it need Int types instead of string
        (parentFragment as LibMainTabFragment).startBrotherFragment(
            LibMessagePage.newInstance(R.string.tab_home, list)
        )

    }

    override fun onResume() {
        super.onResume()
        context?.let { LocalBroadcastManager.getInstance(it).registerReceiver(receiver, intentFilter) }
    }

    override fun onPause() {
        context?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(receiver) }
        super.onPause()
    }
    override fun onStart(){
        super.onStart()
        Log.d("LibChatPage", "this is onStart")
      /* refresh(15000)*/
    }


    override fun onStop() {
        super.onStop()
        Log.d("LibChatPage", "this is onStop")
    }

    /*private fun refresh(milliseconds: Long){
        mChatList.observe(this, Observer {
            val handler = Handler()
            val runnable = Runnable {
                update(it)
            }
            handler.postDelayed(runnable, milliseconds)
        })
        Log.d("LibChatPage", "refreshing"+ count++)
    }
    public fun update(chats: MutableList<LibChatModel>){
       *//* if(mMessageViewModel.getLastMessageContent(chat.id) != chat.contentMain) {
            mChatViewModel.update(chat)
            Log.d("LibChatPage", "updating")
        }*//*
        for(chat in chats) {
            val message = mMessageViewModel.getLastMessageContent(chat.id)
            message.observe(this, Observer {
                if (it.contentMain != chat.contentMain)
                    mChatViewModel.update(chat)
                Log.d("LibChatPage", "counter2" + count2++)
            })
        }

        refresh(15000)
    }*/


    // now it up the last message
    private fun update (chatId: Long){
        mChatViewModel.update(chatId)
    }


}