package com.somoplay.wefungame.libChat.messagePage.AutoInput

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.emoji.KeyboardBar


class AutoInputKeyboard{
    private lateinit var mActivity: Activity
    private lateinit var mContentView: View
    private lateinit var mAutoInputButton: ImageView
    private lateinit var mAutomaticLayout: View

    private lateinit var mKeyboardBar: KeyboardBar
    private lateinit var sp: SharedPreferences

    companion object {
        fun with(activity: Activity): AutoInputKeyboard {
            var autoInputKeyboard = AutoInputKeyboard()
            autoInputKeyboard.mActivity = activity

            return autoInputKeyboard
        }
    }

    fun bindTokeyboardBar(keyboardBar: KeyboardBar): AutoInputKeyboard{
        mKeyboardBar = keyboardBar
        sp = mActivity.getSharedPreferences(mKeyboardBar.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return this
    }
    fun bindToContent(contentView: View): AutoInputKeyboard{
        mContentView = contentView
        return this
    }

    fun bindToAutomaticButton(automaticInputButton: ImageView): AutoInputKeyboard {
        automaticInputButton.setOnClickListener{view->
            mAutoInputButton = automaticInputButton
            if(mAutomaticLayout.isShown){
                //   lockContentHeight()
                hideAutomaticInputLayout()
                //   unLockContentHeightDelayed()
            }else{
                if(mKeyboardBar.isSoftInputShown()){
                 //   lockContentHeight()
                    showAutomaticInputLayout()
                  //  unLockContentHeightDelayed()
                }else{
                    showAutomaticInputLayout()
                }
            }
        }


        return this
    }

    private fun showAutomaticInputLayout(){
        var softInputHeight = mKeyboardBar.getSupportSoftInputHeight()
        if(softInputHeight == 0){
            softInputHeight = mKeyboardBar.getKeyBoardHeight()
        }
        mAutoInputButton.setImageResource(R.drawable.automatic_selected)

        mAutomaticLayout.layoutParams.height = softInputHeight
        mAutomaticLayout.visibility = View.VISIBLE
    }

    private fun hideAutomaticInputLayout(showSoftInput: Boolean =false){
        if(mAutomaticLayout.isShown){
            mAutoInputButton.setImageResource(R.drawable.automatic_normal)
            mAutomaticLayout.visibility = View.GONE
        }
        /*if(showSoftInput){
            showSoftInput()
        }*/
    }

    fun build():AutoInputKeyboard{
        mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return this
    }

    fun interceptBackPress(): Boolean{
        if(mAutomaticLayout.isShown){
            hideAutomaticInputLayout(false)
            return true
        }
        return false
    }

    fun setAutoInputView(autoLayout: View) : AutoInputKeyboard{
        mAutomaticLayout = autoLayout

        return this
    }

}