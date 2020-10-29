package com.somoplay.wefungame.libChat.model

import androidx.room.Embedded
import androidx.room.Relation

data class LibChatAndMessage (
    @Embedded val chat : LibChatModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "chatConversation"
    )
    val messageModelList: MutableList<LibMessageModel>
)