package com.somoplay.wefungame.libChat.messageCell

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.LibMessageAdapter
import com.somoplay.wefungame.libChat.model.LibMessageModel
import com.somoplay.wefungame.libChat.model.LibUserModel
import com.somoplay.wefungame.main.CurrentUser
import org.jetbrains.annotations.NotNull

/*class LibMessageTextRightCell: LinearLayout, LibMessageAdapter.LibMessageCellUpdatable {

    private var sendTextView: TextView
    private var selfIcon : ImageView
    private var receiveTextView : TextView
    private var receiverIcon : ImageView
    private var currentUser : LibUserModel
    private val rightView: View
    private val leftView : View

    constructor(parent: ViewGroup) : super(parent.context) {
        currentUser = CurrentUser.instance
        rightView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lib_chat_message_right_text_cell, parent, false)
        leftView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lib_chat_message_left_text_cell, parent, false)
        sendTextView = rightView.findViewById(R.id.lib_chat_message) as TextView
        selfIcon = rightView.findViewById(R.id.self_avatar) as ImageView
        receiveTextView = leftView.findViewById(R.id.lib_chat_message) as TextView
        receiverIcon = leftView.findViewById(R.id.friend_avatar) as ImageView


    }


    override fun updateMessageCell(messageModel: LibMessageModel){
        if(true) {
            if(rightView.parent != null)
                ((rightView.parent)as ViewGroup).removeView(rightView)
            this.addView(rightView)
            sendTextView.text = messageModel.contentMain
        }else{
            if(leftView.parent != null)
                ((leftView.parent)as ViewGroup).removeView(leftView)
            this.addView(leftView)
            receiveTextView.text = messageModel.contentMain
        }


     //   selfIcon.setImageResource()
    }*/
class LibMessageTextRightCell : RecyclerView.ViewHolder{
    private var txtMessage : TextView
    private var  recipientAvatar : ImageView

    constructor(@NotNull itemView : View): super(itemView){
        txtMessage = itemView.findViewById(R.id.lib_chat_message) as TextView
        recipientAvatar = itemView.findViewById(R.id.friend_avatar) as ImageView
    }

    private fun setRightCellDetails(message: LibMessageModel){
        txtMessage.setText(message.contentMain)

    }
}