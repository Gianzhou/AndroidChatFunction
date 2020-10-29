package com.somoplay.wefungame.libChat.messagePage.AutoInput.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.TextModel
@Dao
interface TextDao {
    @Query("SELECT * FROM gm_message_autoinput_item ORDER BY id")
    fun loadAll(): LiveData<List<TextModel>>

    @Query("SELECT * FROM gm_message_autoinput_item ORDER BY id")
    fun loadAllN(): List<TextModel>

    @Query("SELECT * FROM gm_message_autoinput_item WHERE textContent = :text")
    fun loadLiveTextItem(text: String): LiveData<TextModel>

    @Query("SELECT * FROM gm_message_autoinput_item WHERE categoryId = :categoryId ORDER BY id")
    fun loadSameCategoryLiveTextItems(categoryId: Long): LiveData<List<TextModel>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTextItem(textModel: TextModel)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertTextItemList(textModelList: List<TextModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTextItem(textModel: TextModel)

    @Delete
    fun deleteTextITem(textModel: TextModel)

    @Query("SELECT * FROM gm_message_autoinput_item WHERE textContent = :text")
    fun loadTextItem(text: String): TextModel

}