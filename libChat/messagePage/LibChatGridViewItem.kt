package com.somoplay.wefungame.libChat.messagePage

class LibChatGridViewItem ( itemName: String, imageSource : Int ){
    private  var name  = itemName
    private var image = imageSource

    fun setName (name : String) {
        this.name = name
    }

    fun getName (): String {
        return this.name
    }

    fun setImageResource (imageSource: Int){
        this.image = imageSource
    }

    fun getImageResource():Int {
        return this.image
    }


}