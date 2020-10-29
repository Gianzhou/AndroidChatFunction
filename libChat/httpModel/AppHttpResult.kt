package com.somoplay.wefungame.libChat.httpModel

open class AppHttpResult<out T> {
    private lateinit var code: String
    private lateinit var message: String
    private var data: T = TODO()

     open fun getData() : T{
        return data
    }



    override fun toString(): String {
        var stringBuilder = StringBuilder()
        stringBuilder.append("message=").append(message).append(" code=").append(code)
        if(data != null){
            stringBuilder.append(" data:").append(data.toString())
        }
        return stringBuilder.toString()
    }
}