package com.somoplay.wefungame.libChat.model

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.somoplay.wefungame.util.LibFileUtils
import com.somoplay.wefungame.util.LibImageUtils
import java.util.*

@Entity(tableName = "gm_chat_entity")
data class LibChatModel (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
  /*  var senderId: String = "",
    var senderName: String = "",
    var senderImage: String = "",
    var senderImageType: String = "",
    var receiverId: String = "",
    var receiverName: String = "",
    var receiverImage: String = "",
    var receiverImageType: String = "",
    var contentMain: String = "",
    var subImage: String = "",
    var subImageType: String = "",
    var isRead: Boolean = false,
    var messageType: Int = 1,
    var createdDate: Date = Date(),
    var sendSuccess: Boolean = false,
    var unreadNumber: Int = 0

   */
    var createDate: Date, // the create time of the conversation
    var expireDate: Date, // the expire time of the conversation, usually the conversation would last 7 days after the created date
    var timestamp: Long, // the send time of the last message (contentMain)
    var targetUnreadNumber: Int, // the count for unread messages number in user side
    var userUnreadNumber: Int, // the count for unread messages number in target side
    var userId : String, // the log in user
    var targetId : String, // the another user(target) in the conversation
    var contentMain : String // the last message within the conversation

)/*{
    fun setImage(image: Bitmap, context: Context) {
        id?.let {
            LibImageUtils.saveBitmapToFile(context, image,
                generateImageFilename(it))
        }
    }

    fun deleteImage(context: Context) {
        id?.let {
            LibFileUtils.deleteFile(context, generateImageFilename(it))
        }
    }

    companion object {
        fun generateImageFilename(id: Long): String {
            return "chatModel$id.png"
        }

        fun initData(): ArrayList<LibChatModel> {
            val msgList = ArrayList<LibChatModel>()

            val name = arrayOf("Jake", "Eric", "Kenny", "Helen", "Carr")
            val chats = arrayOf("message1", "message2", "message3", "message4", "message5")
*//*
            for (i in 0..14) {
                val index = (Math.random() * 5).toInt()
                val chat = LibChatModel()
                chat.receiverName = name[index]
                chat.contentMain = chats[index]
                msgList.add(chat)
            }
      *//*      return msgList
        }
    }


}
*/