package com.somoplay.wefungame.libChat.httpModel

import org.json.JSONObject
import java.util.*

class AppHttpResultJSON : AppHttpResult<JSONObject>() {
     override fun getData(): JSONObject {
        val data = AppJsonDataAdapter.objectToJSONObject(super.getData())
        return data ?: JSONObject("hello")

    }
}