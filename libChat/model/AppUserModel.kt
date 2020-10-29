package com.somoplay.wefungame.libChat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.FileDescriptor

class AppUserModel {
    @Expose
    @SerializedName("_id")
    private lateinit var id :String

    @Expose
    @SerializedName("registerDate")
    private lateinit var registerDate :String
    @Expose
    @SerializedName("loginDate")
    private lateinit var loginDate :String
    @Expose
    @SerializedName("ownerId")
    private lateinit var ownerId :String
    @Expose
    @SerializedName("authorMainImage")
    private lateinit var authorMainImage :String
    @Expose
    @SerializedName("authorMainImageType")
    private lateinit var authorMainImageType :String
    @Expose
    @SerializedName("authorName")
    private lateinit var authorName :String
    @Expose
    @SerializedName("authorBirthday")
    private lateinit var authorBirthday :String
    @Expose
    @SerializedName("authorGender")
    private lateinit var authorGender :String
    @Expose
    @SerializedName("authorDescription")
    private lateinit var description :String

    override fun toString(): String {
        return description
    }

}