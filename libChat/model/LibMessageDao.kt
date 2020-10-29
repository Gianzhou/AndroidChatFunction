package com.somoplay.wefungame.libChat.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LibMessageDao  {

    @Query("SELECT * FROM gm_chat_messages ORDER BY messageCreatedDate")
    fun loadAll(): LiveData<List<LibMessageModel>>

    @Query("SELECT * FROM gm_chat_messages ORDER BY messageCreatedDate")
    fun loadAllN():List<LibMessageModel>

    @Query("SELECT * FROM gm_chat_messages WHERE id = :messageId")
    fun loadMessage(messageId: Long): LibMessageModel

    @Query("SELECT * FROM gm_chat_messages WHERE chatConversation = :chatId order by messageCreatedDate")
    fun loadLiveMessageList(chatId : Long) : LiveData<MutableList<LibMessageModel>>

    @Query("SELECT * FROM gm_chat_messages WHERE chatConversation = :chatId order by messageCreatedDate")
    fun loadMessageList(chatId : Long) : MutableList<LibMessageModel>

    @Query("SELECT * FROM gm_chat_messages WHERE id = :messageId")
    fun loadLiveMessage(messageId: Long): LiveData<LibMessageModel>

    /*load the last message of the chat*/
    @Query("SELECT * FROM gm_chat_messages WhERE id = (SELECT max(id) FROM gm_chat_messages WHERE chatConversation = :chatId order by messageCreatedDate)")
    fun loadlastMessageContent(chatId: Long) : LiveData<LibMessageModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessage(message: LibMessageModel)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertMessageList(messageList: List<LibMessageModel>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(libMessageModel: LibMessageModel)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMessage(message: LibMessageModel)

    @Delete
    fun deleteMessage(message: LibMessageModel)

}
