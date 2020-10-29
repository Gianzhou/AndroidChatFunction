package com.somoplay.wefungame.libChat.messagePage.emoji

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.media.Image
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.somoplay.wefungame.R

class EmotionKeyboard {
    private val SHARE_PREFERENCE_NAME = " EmotionKeyboard";
    private val SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height"
    private lateinit var mActivity : Activity
    private lateinit var mInputManager: InputMethodManager
    private lateinit var sp: SharedPreferences
    private lateinit var mEmotionLayout: View
    private lateinit var mVoiceInputLayout: View
    private lateinit var mAutomaticLayout: View
    private lateinit var mAddMoreLayout: View
    private lateinit var mEditText: EditText
    private lateinit var mContentView: View

    private lateinit var mEmotionButton: ImageView
    private lateinit var mVoiceInputButton: ImageView
    private lateinit var mAutoInputButton: ImageView
    private lateinit var mAddMoreButton: ImageView

    private lateinit var mKeyboardBar: KeyboardBar

    companion object{
        public fun with(activity: Activity): EmotionKeyboard{
            var emotionInputDetector = EmotionKeyboard()
            emotionInputDetector.mActivity = activity
            emotionInputDetector.mInputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    //        emotionInputDetector.sp = activity.getSharedPreferences(emotionInputDetector.mKeyboardBar.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
            return emotionInputDetector
        }
    }

    public fun bindToContent(contentView: View): EmotionKeyboard{
        mContentView = contentView
        return this
    }
    fun bindToKeyboardBar(keyboardBar: KeyboardBar): EmotionKeyboard{
        mKeyboardBar = keyboardBar
        sp = mActivity.getSharedPreferences(mKeyboardBar.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    public fun bindToEditText(editText: EditText):EmotionKeyboard{
        mEditText = editText
        mEditText.requestFocus()
        hideVoiceInputLayout()
        hideAddMoreLayout()
   //     hideAutomaticInputLayout()
        Log.d("emo", "this is inside binToEditText")
        mEditText.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP ){
                lockContentHeight()
                if(mEmotionLayout.isShown) {

                    hideEmotionLayout(true)

                }
                if(mVoiceInputLayout.isShown){
                    hideVoiceInputLayout(true)
                }
    /*            if(mAutomaticLayout.isShown){
                    hideAutomaticInputLayout(true)
                }
     */           if(mAddMoreLayout.isShown){
                    hideAddMoreLayout(true)
                }
                mEditText.postDelayed(Runnable {
                    unLockContentHeightDelayed()
                }, 200L)
Log.d("emo", "this is from bindToEditText")
            }
            return@OnTouchListener false
        } )
        return this
    }
    fun bindToEmotionButton(emotionButton: ImageView): EmotionKeyboard{
        emotionButton.setOnClickListener(View.OnClickListener{view ->
            mEmotionButton = emotionButton
            if(mEmotionLayout.isShown){
                lockContentHeight()
                hideEmotionLayout(true)
                unLockContentHeightDelayed()
            }else{
                if(mKeyboardBar.isSoftInputShown()){
                    lockContentHeight()
                    showEmotionLayout()
                    unLockContentHeightDelayed()
                }else{
                    showEmotionLayout()
                }
            }


        })
        return this
    }
    fun bindToVoiceButton(voiceInputButton: ImageView): EmotionKeyboard{
        voiceInputButton.setOnClickListener { view->
            mVoiceInputButton = voiceInputButton
            if(mVoiceInputLayout.isShown){
            //    lockContentHeight()
                hideVoiceInputLayout()
            //    unLockContentHeightDelayed()
            }else{
                if(mKeyboardBar.isSoftInputShown()){
                    lockContentHeight()
                    showVoiceInputLayout()
                    unLockContentHeightDelayed()
                }else{
                    showVoiceInputLayout()
                }

            }

        }

        return this
    }
    fun bindToAutomaticButton(automaticInputButton: ImageView): EmotionKeyboard{
        automaticInputButton.setOnClickListener{view->
            mAutoInputButton = automaticInputButton
            if(mAutomaticLayout.isShown){
             //   lockContentHeight()
                hideAutomaticInputLayout()
             //   unLockContentHeightDelayed()
            }else{
                if(mKeyboardBar.isSoftInputShown()){
                    lockContentHeight()
                    showAutomaticInputLayout()
                    unLockContentHeightDelayed()
                }else{
                    showAutomaticInputLayout()
                }

            }
        }


        return this
    }
    fun bindToAddMoreButton(addmoreButton: ImageView): EmotionKeyboard{
        addmoreButton.setOnClickListener{view->
            mAddMoreButton = addmoreButton
            if(mAddMoreLayout.isShown){
            //    lockContentHeight()
                hideAddMoreLayout()
             //   unLockContentHeightDelayed()
            }else{
                if(mKeyboardBar.isSoftInputShown()){
                    lockContentHeight()
                    showAddMoreLayout()
                    unLockContentHeightDelayed()
                }else{
                    showAddMoreLayout()
                }
            }
        }

        return this
    }


    fun setEmotionView(emotionView: View, voiceView: View, addmoreView: View ): EmotionKeyboard{
        Log.d("emo", "this is from emotionkeyboard-setEmotionView")
        mEmotionLayout = emotionView
        mVoiceInputLayout = voiceView
     //   mAutomaticLayout = automaticView
        mAddMoreLayout = addmoreView
        return this
    }
    fun build():EmotionKeyboard{
        mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        hideSoftInput()
        return this
    }

    fun interceptBackPress(): Boolean{
        if(mEmotionLayout.isShown){
            hideEmotionLayout(false)
            return true
        }
        return false
    }



    private fun showEmotionLayout(){
        var softInputHeight = mKeyboardBar.getSupportSoftInputHeight()
        if(softInputHeight == 0){
            softInputHeight = mKeyboardBar.getKeyBoardHeight()
        }
        mEmotionButton.setImageResource(R.drawable.stickers_selected)
        hideSoftInput()
        hideVoiceInputLayout()
 //       hideAutomaticInputLayout()
        hideAddMoreLayout()
        mEmotionLayout.layoutParams.height = softInputHeight
        mEmotionLayout.visibility = View.VISIBLE
    }

    private fun hideEmotionLayout(showSoftInput: Boolean = false){
        if(mEmotionLayout.isShown){
            mEmotionButton.setImageResource(R.drawable.stickers_normal)
            mEmotionLayout.visibility = View.GONE
            if(showSoftInput){
                showSoftInput()
            }
        }
    }
    private fun showVoiceInputLayout(){
        var softInputHeight : Int = mKeyboardBar.getSupportSoftInputHeight()
        if(softInputHeight == 0){
            softInputHeight = mKeyboardBar.getKeyBoardHeight()
        }
        hideEmotionLayout()
        hideSoftInput()
        hideAddMoreLayout()
 //       hideAutomaticInputLayout()
        mVoiceInputButton.setImageResource(R.drawable.speak_selected)
        mVoiceInputLayout.layoutParams.height = softInputHeight
        mVoiceInputLayout.visibility = View.VISIBLE
        Log.d("emo", "this is from EmotionKeyBoard: showVoiceInputLayout; softInputHeight=$softInputHeight  height = " + mVoiceInputLayout.layoutParams.height)
    }
    private fun hideVoiceInputLayout(showSoftInput: Boolean = false){
        if(mVoiceInputLayout.isShown){
            mVoiceInputButton.setImageResource(R.drawable.speak_normal)
            mVoiceInputLayout.visibility = View.GONE
            if(showSoftInput){
                showSoftInput()
            }
        }
    }

    private fun showAutomaticInputLayout(){
        var softInputHeight = mKeyboardBar.getSupportSoftInputHeight()
        if(softInputHeight == 0){
            softInputHeight = mKeyboardBar.getKeyBoardHeight()
        }
        mAutoInputButton.setImageResource(R.drawable.automatic_selected)
        hideSoftInput()
        hideVoiceInputLayout()
        hideEmotionLayout()
        hideAddMoreLayout()
        mAutomaticLayout.layoutParams.height = softInputHeight
        mAutomaticLayout.visibility = View.VISIBLE
    }

    private fun hideAutomaticInputLayout(showSoftInput: Boolean =false){
        if(mAutomaticLayout.isShown){
            mAutoInputButton.setImageResource(R.drawable.automatic_normal)
            mAutomaticLayout.visibility = View.GONE
        }
        if(showSoftInput){
            showSoftInput()
        }
    }

    private fun showAddMoreLayout(){
        var softInputHeight = mKeyboardBar.getSupportSoftInputHeight()
        if(softInputHeight == 0){
            softInputHeight = mKeyboardBar.getKeyBoardHeight()
        }
        mAddMoreButton.setImageResource(R.drawable.more_selected)
        hideSoftInput()
        hideVoiceInputLayout()
        hideEmotionLayout()
 //       hideAutomaticInputLayout()
        mAddMoreLayout.layoutParams.height = softInputHeight
        mAddMoreLayout.visibility = View.VISIBLE
    }

    private fun hideAddMoreLayout(showSoftInput: Boolean = false){
        if(mAddMoreLayout.isShown){
            mAddMoreButton.setImageResource(R.drawable.more)
            mAddMoreLayout.visibility = View.GONE
        }
        if(showSoftInput){
            showSoftInput()
        }
    }

    private fun lockContentHeight(){
        var params : LinearLayout.LayoutParams = mContentView.layoutParams as LinearLayout.LayoutParams
        params.height = mContentView.height
        params.weight = 0.0F
    }

    private fun unLockContentHeightDelayed(){
        mEditText.postDelayed({
            (mContentView.layoutParams as LinearLayout.LayoutParams).weight = 1.0F
        }, 200)
    }

    private fun showSoftInput(){
        mEditText.requestFocus()
        mEditText.post {
            mInputManager.showSoftInput(mEditText, 0)
        }
    }

    private fun hideSoftInput(){
        mInputManager.hideSoftInputFromWindow(mEditText.windowToken,0)
    }

    /*private fun isSoftInputShown(): Boolean{
        return getSupportSoftInputHeight() != 0
    }

    private fun getSupportSoftInputHeight(): Int{
        var r: Rect = Rect()

        mActivity.window.decorView.getWindowVisibleDisplayFrame(r)

        var screenHeight = mActivity.window.decorView.rootView.height
        var softInputHeight = screenHeight - r.bottom

        *//**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         *//*
       if(Build.VERSION.SDK_INT >= 20){
           softInputHeight -= getSoftButtonsBarHeight()
       }
        if(softInputHeight < 0){
            Log.w("emo","EmotionKeyboard--Warning: value of softInputHeight is below zero!")
        }
        if(softInputHeight> 0){
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT , softInputHeight).apply()
        }

        return softInputHeight
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getSoftButtonsBarHeight(): Int{
        //这个方法获取可能不是真实屏幕的高度
        var metrics: DisplayMetrics = DisplayMetrics()
        mActivity.windowManager.defaultDisplay.getMetrics(metrics)
        var usableHeight = metrics.heightPixels

        //获取当前屏幕的真实高度
        mActivity.windowManager.defaultDisplay.getRealMetrics(metrics)
        var realHeight: Int = metrics.heightPixels
        if(realHeight > usableHeight){
            return realHeight - usableHeight
        }else{
            return 0
        }

    }*/

    /*private fun getKeyBoardHeight(): Int{
        return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 787)
    }*/



}