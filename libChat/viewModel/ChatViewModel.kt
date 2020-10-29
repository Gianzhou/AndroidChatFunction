package com.somoplay.wefungame.libChat.viewModel

import android.app.Application
import android.app.NotificationChannel
import android.content.BroadcastReceiver
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.libChat.model.LibMessageModel
import com.somoplay.wefungame.libChat.repository.LibChatRepository
import com.somoplay.wefungame.libChat.repository.LibMessageRepository
import java.sql.Timestamp
import java.util.concurrent.Executors
import kotlin.properties.Delegates

class ChatViewModel: AndroidViewModel {
    private var mChatRepository: LibChatRepository
    private var mMessageRepository: LibMessageRepository
   /* private var mChatList: LiveData<MutableList<LibChatModel>>*/
    private lateinit var mLastMessage: String
    private var mTimestamp by Delegates.notNull<Long>()

    constructor(application: Application) : super(application) {
        mChatRepository = LibChatRepository(application)
        mMessageRepository = LibMessageRepository(application)

    }

    /*private val changeObserver = Observer<LibChatModel>{value ->
        value?.let{
            it.contentMain = mLastMessage
            it.timestamp = mTimestamp
        }
    }*/
    fun getAllChat(userId: String) : LiveData<MutableList<LibChatModel>> {return mChatRepository.getAllChat(userId)}

    fun insert(chat: LibChatModel){
        mChatRepository.insert(chat)
    }


    fun delete(chat: LibChatModel){
        mChatRepository.delete(chat)
    }

    fun update(chat: LibChatModel){
       /* Executors.newSingleThreadExecutor().execute{
            val contentMessage = mMessageRepository.getLastMessageContent(chat.id)
            if(contentMessage != chat.contentMain) {
                mChatRepository.update(chat)
            }
        }*/
        mChatRepository.update(chat)

    }
    fun update(chatId: Long){
        mChatRepository.update(chatId)
    }
   /* fun update(lastMessage: String, timestamp: Long, userId: String, targetId: String) {
        mLastMessage = lastMessage
        mTimestamp = timestamp
        var chat: LiveData<LibChatModel> = mChatRepository.getChat(userId, targetId)
        if (chat != null) {
            chat.observe(this, changeObserver)
        } else {
            insert(chat)
        }
    }*/
}

