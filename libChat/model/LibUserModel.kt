package com.somoplay.wefungame.libChat.model

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.somoplay.wefungame.util.LibImageUtils

@Entity(tableName = "gm_users", indices = arrayOf(Index(value=["userId","firstName","lastName"], unique = true)))
data class LibUserModel (
    @PrimaryKey(autoGenerate = true) var id : Long = 0,
    var userId : String, //the unique userId (system generate?)b
    var lastName : String,
    var firstName : String,
    var email : String,
    var Birth : String?,
    var description: String?,
    var avatar : String
 //   var avatar : Bitmap?
    // the type of avatar set to string temporarily
)
/*
{
    fun setImage(image: Bitmap, context: Context) {
        id?.let {
            LibImageUtils.saveBitmapToFile(
                context, image,
                LibChatModel.generateImageFilename(it)
            )
        }
    }

}

 */