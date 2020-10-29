package com.somoplay.wefungame.libChat.messageCell

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.model.LibMessageModel

class LibMessageTextLeftCell : RecyclerView.ViewHolder {

    private var txtMessage: TextView
 /*   private var selfAvatar : ImageView*/

    constructor(@NonNull itemView: View) : super(itemView) {
        txtMessage = itemView.findViewById(R.id.lib_chat_message) as TextView
        /*selfAvatar = itemView.findViewById(R.id.self_avatar) as ImageView*/
    }

    private fun setLeftCellDetails(message: LibMessageModel) {
        txtMessage.setText(message.contentMain)

    }

}