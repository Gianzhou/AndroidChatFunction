package com.somoplay.wefungame.libChat.messagePage.emoji

import android.util.Log
import android.view.ViewGroup
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions

class EmojiKeyboardListener() : EmojIconActions.KeyboardListener {

    override fun onKeyboardClose() {
        var a = 123;
        Log.d("emo", "onkeyboardclosekjkj()")
    }

    override fun onKeyboardOpen() {
        var a = 321
        Log.d("emo", "onkeyboardopebvbhvhv()")
    }
}