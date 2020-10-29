package com.somoplay.wefungame.libChat.messagePage.AutoInput.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gm_message_autoinput_category")
data class CategoryModel (
    @PrimaryKey(autoGenerate = true) val id : Long = 0,
    var categoryName: String
){
    fun isEmpty(): Boolean{
        if(this.categoryName.isEmpty())
            return true
        return false
    }

    fun setName(name: String):CategoryModel{
        this.categoryName = name
        return this
    }

}
