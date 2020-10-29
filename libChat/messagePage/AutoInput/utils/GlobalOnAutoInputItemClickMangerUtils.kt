package com.somoplay.wefungame.libChat.messagePage.AutoInput.utils

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter.AutoInputGridViewAdapter

class GlobalOnAutoInputItemClickMangerUtils {

    fun getItemOnClickListener(): AdapterView.OnItemClickListener{
        return AdapterView.OnItemClickListener{ parent: AdapterView<*>, view: View, position: Int, id: Long->
            var itemAdapter = parent.adapter
            if(itemAdapter is AutoInputGridViewAdapter){
                var gridViewAdapter: AutoInputGridViewAdapter = itemAdapter
                if(position <= gridViewAdapter.count){

                    var textString : String = gridViewAdapter.getItem(position).toString()

                    var currentCursorPosition = mEditText.selectionStart
                    var sb = StringBuilder(mEditText.text.toString())
                    sb.insert(currentCursorPosition, textString)

                    mEditText.setText(sb)
                    mEditText.setSelection(currentCursorPosition + textString.length)
                }
            }

        }
    }

    fun getItemOnLongClickListener(): AdapterView.OnItemLongClickListener{
        return AdapterView.OnItemLongClickListener{ parent: AdapterView<*>, view: View, position: Int, id: Long->


        true

        }
    }

    fun attachToEditText(editText: EditText){
        mEditText = editText
    }

    companion object{
        private lateinit var mEditText: EditText
        private lateinit var mContext: Context
        private val LOCK = Any()
        private var instance
                : GlobalOnAutoInputItemClickMangerUtils? = null
        fun getInstance(context: Context): GlobalOnAutoInputItemClickMangerUtils?{
            this.mContext = context
            if(instance == null){
                synchronized(LOCK){
                    if(instance == null){
                        instance = GlobalOnAutoInputItemClickMangerUtils()
                    }
                }
            }
            return instance
        }
    }
}