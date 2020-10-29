package com.somoplay.wefungame.libChat.messagePage.AutoInput.model

import android.graphics.drawable.Drawable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "gm_message_autoinput_item",
        foreignKeys = [ForeignKey(entity = CategoryModel::class,parentColumns = ["id"],childColumns = ["categoryId"], onDelete = CASCADE)])
   //     indices = [Index(value = ["text_content","category_id"], unique = true)]

data class TextModel (
    @PrimaryKey(autoGenerate = true) val id: Long=0,
     var textContent: String,
     var  categoryId: Long
){

    fun isEmpty(): Boolean{
        if (this.textContent.isEmpty()){
            return true
        }
        return false
    }

    fun setName(name: String): TextModel{
        this.textContent = name
        return this
    }

    fun getName(): String{
        return this.textContent
    }

    fun getCateId(): Long{
        return this.categoryId
    }

}