package com.somoplay.wefungame.libChat.messagePage.AutoInput.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.TextModel
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.dao.TextDao
import com.somoplay.wefungame.main.WeFunDatabase
import org.w3c.dom.Text
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TextRepository {

    private var mTextDao: TextDao
    private var db: WeFunDatabase
    private var executorService: ExecutorService

    constructor(context: Context){
        db = WeFunDatabase.invoke(context)
        mTextDao = db.TextDao()
        this.executorService = Executors.newSingleThreadExecutor()
    }

    fun getAllTextItem():LiveData<List<TextModel>> {
        return mTextDao.loadAll()
    }
    fun getSameCategoryTextItem(categoryId: Long): LiveData<List<TextModel>> {
        return mTextDao.loadSameCategoryLiveTextItems(categoryId)
    }


    private fun insertTextItem(textModel: TextModel){
        mTextDao.insertTextItem(textModel)
    }

    private fun updateTextItem(textModel: TextModel){
        mTextDao.updateTextItem(textModel)
    }

    private fun deleteTextItem(textModel: TextModel){
        mTextDao.deleteTextITem(textModel)
    }
    private fun selectTextItem(text: String): TextModel{
        return mTextDao.loadTextItem(text)
    }
    fun selectLiveTextItem(text: String): LiveData<TextModel>{
        return mTextDao.loadLiveTextItem(text)
    }

    fun deleteText(text: String){
        var textModel = selectTextItem(text)
        deleteTextItem(textModel)
    }

    fun updateText(newName: String, oldName: String): Boolean{
        var newModel = selectTextItem(newName)
        var oldModel: TextModel = selectTextItem(oldName)
        if(newModel.isEmpty()){
            oldModel.setName(newName)
            updateTextItem(oldModel)
            return true
        }
        return false
    }

    fun insertText(newName: String, oldName: String): Boolean{
        var newModel: TextModel = selectTextItem(newName)
        var oldModel: TextModel = selectTextItem(oldName)
        if(newModel.isEmpty()){
            oldModel.setName(newName)
            insertTextItem(oldModel)
            return true
        }
        return false
    }

}