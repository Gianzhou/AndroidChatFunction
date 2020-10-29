package com.somoplay.wefungame.libChat.model

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface LibChatDao  {

    @Query("SELECT * FROM gm_chat_entity WHERE userId = :userA or targetId = :userA ")
    fun loadAll(userA : String): LiveData<List<LibChatModel>>

    @Query("SELECT * FROM gm_chat_entity WHERE userId = :userA or targetId = :userA ")
    fun loadAllN(userA : String): List<LibChatModel>

    @Query("SELECT * FROM gm_chat_entity WHERE userId = :userId or targetId = :userId ORDER BY createDate")
    fun loadChatList(userId : String): MutableList<LibChatModel>

    @Query("SELECT * FROM gm_chat_entity WHERE userId = :userId or targetId = :userId ORDER BY createDate")
    fun loadLiveChatList(userId : String): LiveData<MutableList<LibChatModel>>


    @Query("SELECT * FROM gm_chat_entity WHERE (userId = :userA and targetId = :userB)  or (userId = :userB and targetId = :userA)")
    fun loadLiveChat(userA : String, userB: String): LiveData<LibChatModel>

    @Query("SELECT * FROM gm_chat_entity WHERE (userId = :userA and targetId = :userB)  or (userId = :userB and targetId = :userA)")
    fun loadChat(userA : String, userB: String): LibChatModel

    //insert new chat conversation
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertChat(conversation: LibChatModel)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertChatList(chatList: List<LibChatModel>)

    //include modifying the messages list
    @Query("UPDATE gm_chat_entity SET contentMain = (SELECT contentMain FROM gm_chat_messages WhERE id = (SELECT max(id) FROM gm_chat_messages WHERE chatConversation = :chatId order by messageCreatedDate)) WHERE id = :chatId")
    fun updateChat(chatId : Long)

    @Delete
    fun deleteChat(chat: LibChatModel)
}
