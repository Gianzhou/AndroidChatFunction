package com.somoplay.wefungame.libChat.chatPage

import android.app.Application
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.libChat.model.LibChatModel
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.SpanStringUtils
import com.somoplay.wefungame.libChat.viewModel.ChatViewModel
import com.somoplay.wefungame.libChat.viewModel.MessageViewModel
import com.somoplay.wefungame.main.CurrentUser
import kotlinx.coroutines.newSingleThreadContext
import java.text.SimpleDateFormat
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class LibChatAdapter (
    private val chatLists: MutableList<LibChatModel>,
    private val clickListener: LibChatItemClickListener,
    private val chatViewModel: ChatViewModel,
    private val messageViewModel: MessageViewModel,
    private val mContext: Context
        ): RecyclerView.Adapter<LibChatAdapter.ListChatViewHolder>() {

    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()
    interface LibChatItemClickListener {

        fun chatItemClicked(list: LibChatModel)
    }

    inner class ListChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatCellName = itemView.findViewById(R.id.chatCellName) as TextView
        val chatCellMessage = itemView.findViewById(R.id.chatCellMessage) as TextView
        val chatCellTime = itemView.findViewById(R.id.chatCellTime) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lib_chat_cell, parent,false)
        Log.d("LibChatAdapter", "this is onCreteViewHolder")
        return ListChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListChatViewHolder, position: Int) {
        val chat = chatLists[position]
        var message: String
        if(chat.contentMain.length > 30)
            message = chat.contentMain.subSequence(0,30) as String + " ....."
        else
            message = chat.contentMain
       /* executorService.execute{
            val content = messageViewModel.getLastMessageContent(chat.id)
            if(chat.contentMain != content){
               Log.d("LibChatAdapter", content)
                update(position)
            }
        }
*/
        holder.chatCellName.text = chat.targetId
        holder.chatCellMessage.text =
            SpanStringUtils.getEmotionContent(1,mContext,holder.chatCellMessage, message)
        holder.chatCellTime.text = (SimpleDateFormat("MM/dd/yy").format(chat.timestamp)).toString()

        holder.itemView.setOnClickListener {
            clickListener.chatItemClicked(chatLists[position])
        }
        Log.d("LibChatAdapter", chat.userId)
        /*chatViewModel.update(chat)*/


    }

    override fun getItemCount(): Int {
        return chatLists.size
    }

    fun addList(list: LibChatModel) {
        chatLists.add(list)
        notifyDataSetChanged()
    }
    fun update(position: Int){
        val chat = chatLists[position]
        chatViewModel.update(chat)
    }


}

