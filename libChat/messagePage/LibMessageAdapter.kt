package com.somoplay.wefungame.libChat.messagePage

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messageCell.*
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.SpanStringUtils
import com.somoplay.wefungame.libChat.model.LibMessageModel
import org.w3c.dom.Text
import java.lang.IllegalArgumentException

class LibMessageAdapter (

/*): RecyclerView.Adapter<LibMessageAdapter.LibMessageViewHolder<View>>() {*/
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private lateinit var messageList: MutableList<LibMessageModel>
    //    private val messageList: LiveData<List<LibMessageModel>>
    private lateinit var clickListener: LibMessageItemClickListener
    private lateinit var currentUserId: String
    private lateinit var layoutInflater : LayoutInflater
    private lateinit var context : Context

    companion object{
        private val TYPE_RECEIVE = 2
        private val TYPE_SEND = 1
    }

    constructor( context : Context, messages: MutableList<LibMessageModel>, id: String, listener : LibMessageItemClickListener) : this() {
        this.clickListener = listener
        this.context = context
        this.messageList = messages
        this.currentUserId = id
        this.layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        /*this.messageList = MutableList<LibMessageModel>()*/
    }




    /*inner class LibMessageViewHolder <V : View> ( private val cellView: V ): RecyclerView.ViewHolder(cellView) {

        fun updateMessageCell(messageModel: LibMessageModel){
            if (cellView is LibMessageCellUpdatable){
                (cellView as LibMessageCellUpdatable).updateMessageCell(messageModel)
            }
        }
    }*/
    inner class LibMessageViewHolder(itemView : View, n: Int): BaseViewHolder<LibMessageModel>(itemView){
        private var side = n
        private lateinit var txtMessage: TextView
        private lateinit var avatar : ImageView
        private fun initial() {
            if(side == 1){
                txtMessage = itemView.findViewById(R.id.lib_chat_message)
             /*   avatar = itemView.findViewById(R.id.self_avatar)*/
            }else{
                txtMessage = itemView.findViewById(R.id.lib_chat_message)
                avatar = itemView.findViewById(R.id.friend_avatar)
                txtMessage.setTextColor(Color.parseColor("#000000"))
            }
        }
        override fun bind(item: LibMessageModel) {
           initial()
            txtMessage.setText(SpanStringUtils.getEmotionContent(1,context,txtMessage, item.contentMain))
        }

    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibMessageViewHolder<View> {*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        var view: View
        return when (viewType) { //viewType.absoluteValue
            1 -> {
                view =
                    layoutInflater.inflate(R.layout.lib_chat_message_right_text_cell, parent, false)
                LibMessageViewHolder(view, 1)
            }
            2 -> {
                view =
                    layoutInflater.inflate(R.layout.lib_chat_message_left_text_cell, parent, false)
                LibMessageViewHolder(view, 2)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }



    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
    /*override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {*/
        val oneMessageModel = messageList[position]
        when(holder){
            is LibMessageViewHolder -> holder.bind(oneMessageModel)
            else -> throw IllegalArgumentException()
        }

        holder.itemView.setOnClickListener {
            clickListener.messageItemClicked(messageList[position])
        }
        Log.d("LibmessageAdapter", oneMessageModel.contentMain)
        Log.d("LibmessageAdapter", oneMessageModel.messageCreatedDate.toString())
/*
        if(getItemViewType(position) == TYPE_SEND){
            (holder as LibMessageTextRightCell).
        }*/
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
       /* //查看聊天内容的 文字还是语音还是其他的
        var messageType = messageList[position].messageType
        val senderId = messageList[position].messageUserId

        // if this message is from other, the type is negative
        // 比较用户id，来确定消息是否是自己的
        if (senderId != currentUserId){
      //      messageType *= -1
        }
      //  return messageType
        return 0*/

        if(TextUtils.equals(currentUserId, messageList[position].messageUserId)){
            return TYPE_SEND
        }else
            return TYPE_RECEIVE
    }

    fun setData(newMessages: MutableList<LibMessageModel>) {
        if(newMessages.isNotEmpty()){
            Log.d("LibMessageAdapter", "do something later")
        }else{
            // first initialization
            this.messageList = newMessages
            notifyDataSetChanged()
        }

    }


}

