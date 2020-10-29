package com.somoplay.wefungame.libChat.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.main.WeFunDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LibChatRepository {
    private var db: WeFunDatabase
    private var executorService : ExecutorService
   /* private lateinit mChatList: LiveData<MutableList<LibChatModel>>*/

    constructor(context: Context){
        db = WeFunDatabase.invoke(context)
        this.executorService = Executors.newSingleThreadExecutor()

    }

    fun getAllChat(userId: String): LiveData<MutableList<LibChatModel>>{
        return db.ChatDao().loadLiveChatList(userId)
    }

    fun insert(newChat: LibChatModel){
        db.ChatDao().insertChat(newChat)
    }

    fun update(chat: LibChatModel){
        executorService.execute{
            db.ChatDao().updateChat(chat.id)
        }
    }

    fun update(chatId: Long){
        if(chatId >= 0)
        executorService.execute{
            db.ChatDao().updateChat(chatId)
        }
    }

    fun delete(chat: LibChatModel){
        db.ChatDao().deleteChat(chat)
    }

    fun getChat( userId: String, targetId: String): LiveData<LibChatModel>{
        return db.ChatDao().loadLiveChat(userId, targetId)
    }

}