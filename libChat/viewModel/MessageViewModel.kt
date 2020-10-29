package com.somoplay.wefungame.libChat.viewModel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.libChat.model.LibMessageDao
import com.somoplay.wefungame.libChat.model.LibMessageModel
import com.somoplay.wefungame.libChat.repository.LibMessageRepository
import com.somoplay.wefungame.main.WeFunDatabase
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MessageViewModel: AndroidViewModel {

    private  var mMessageRepository: LibMessageRepository

    constructor( application: Application) : super(application) {
        mMessageRepository = LibMessageRepository(application)
        /*mAllChat = mMessageRepository.getMessageList()*/

    }

    fun getLastMessageContent(chatId: Long): LiveData<LibMessageModel> {
        return mMessageRepository.getLastMessageContent(chatId)
    }

    fun getAllChat(chatId: Long): LiveData<MutableList<LibMessageModel>> { return mMessageRepository.getMessageList(chatId)}

    fun insert(contentMain: String, conversation: LibChatModel){
        val message = LibMessageModel(messageCreatedDate = Date(),chatConversation = conversation.id,messageTimestamp = System.currentTimeMillis(),messageSubImage = "",messageSubImageType = "",messageUserId =conversation.userId,messageTargetId = conversation.targetId,contentMain = contentMain,messageIsRead = true)
        mMessageRepository.insert(message)
        val intent = Intent("com.someplay.wefungame.DATA_UPDATA")
        intent.putExtra("chatId",conversation.id)
        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(intent)
        /*Log.d("MessageViewModel", "sendbroadcast")
        Toast.makeText(getApplication(), "SOME_ACTION is sent", Toast.LENGTH_LONG).show()*/

    }

    fun delete(message: LibMessageModel){
        mMessageRepository.delete(message)
    }

    //add update

}