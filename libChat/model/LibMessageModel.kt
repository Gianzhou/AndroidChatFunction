package com.somoplay.wefungame.libChat.model

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.somoplay.wefungame.util.LibFileUtils
import com.somoplay.wefungame.util.LibImageUtils
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "gm_chat_messages",
        foreignKeys = [ForeignKey(entity = LibChatModel::class, parentColumns = ["id"], childColumns = ["chatConversation"], onDelete = CASCADE)])
data class LibMessageModel (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
/*    var senderId: String = "",
    var senderName: String = "",
    var senderImage: String = "",
    var senderImageType: String = "",
    var receiverId: String = "",
    var receiverName: String = "",
//    var receiverImage: String = "",
    var receiverImageType: String = "",
    var contentMain: String = "",
    var subImage: String = "",
    var subImageType: String = "",
*/
    var messageCreatedDate: Date,
    var messageTimestamp: Long,
    var messageUserId: String,
    var messageTargetId: String,
    var messageSubImage: String,
    var messageSubImageType: String,
    var contentMain: String, // if the type isn't text, it contain the path
    var messageType: MessageType = MessageType.TEXT,
    var messageIsRead : Boolean,
    var chatConversation: Long
){
//    constructor(contentMain: String){
//        this.contentMain = contentMain
//
//
//    }
//
/*    fun setImage(image: Bitmap, context: Context) {
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
*/
 /*
    companion object {
        fun generateImageFilename(id: Long): String {
            return "chatModel$id.png"
        }

        fun initData(): ArrayList<LibMessageModel> {
            val msgList = ArrayList<LibMessageModel>()

            val name = arrayOf("Jake", "Eric", "Kenny", "Helen", "Carr")
            val chats = arrayOf("message1", "message2", "message3", "message4", "message5")

            for (i in 0..14) {
                val index = (Math.random() * 5).toInt()
                val chat = LibMessageModel()
                chat.recipient = name[index]
                chat.messageContent = chats[index]
                msgList.add(chat)
            }
            return msgList
        }
    }*/
}


enum class MessageType(val type : String){
    IMAGE("image"),
    TEXT("text"),
    VOICE("voice"),
    FILE("file")
}