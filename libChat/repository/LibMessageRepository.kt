package com.somoplay.wefungame.libChat.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.somoplay.wefungame.libChat.model.LibMessageDao
import com.somoplay.wefungame.libChat.model.LibMessageModel
import com.somoplay.wefungame.main.WeFunDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LibMessageRepository {

    private var mMessageDao: LibMessageDao
    private lateinit var mMessageList: LiveData<MutableList<LibMessageModel>>
    private var db: WeFunDatabase
    private var executorService: ExecutorService

    constructor(context: Context){
        //TODO change the database (Future work)
        db = WeFunDatabase.invoke(context)

        mMessageDao = db.MessageDao()
        this.executorService = Executors.newSingleThreadExecutor()
    }

    fun getMessageList(chatId: Long): LiveData<MutableList<LibMessageModel>> {
        mMessageList = db.MessageDao().loadLiveMessageList(chatId)
        return mMessageList
    }


    fun insert(message: LibMessageModel) {
        insertAsyncTask(mMessageDao).execute(message)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: LibMessageDao) : AsyncTask<LibMessageModel, Void, Void>() {

        override fun doInBackground(vararg params: LibMessageModel): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    fun getLastMessageContent(chatId: Long): LiveData<LibMessageModel>{

            return db.MessageDao().loadlastMessageContent(chatId)


    }


    fun update(message: LibMessageModel){
        executorService.execute { mMessageDao.updateMessage(message) }
    }

    fun delete(message: LibMessageModel){
        executorService.execute{ mMessageDao.deleteMessage(message) }
    }
}