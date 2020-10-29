package com.somoplay.wefungame.libChat.messageCell

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.libChat.model.LibMessageModel

abstract class BaseViewHolder<T>(itemView : View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item : T)
}

interface LibMessageItemClickListener {
    fun messageItemClicked(list: LibMessageModel)
}
interface LibMessageCellUpdatable {
    fun updateMessageCell(messageModel: LibMessageModel)
}