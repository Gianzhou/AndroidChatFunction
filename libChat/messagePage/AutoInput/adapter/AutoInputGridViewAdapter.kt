package com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.coroutines.coroutineContext

class AutoInputGridViewAdapter: BaseAdapter {
    private lateinit var mContext: Context
    private var mTextNamesList: ArrayList<String>
    private var mItemWidth : Int
    constructor(context: Context, itemName: ArrayList<String>, itemWidth: Int){
        mContext = context
        mTextNamesList = itemName
        mItemWidth = itemWidth
    }
    override fun getView(position: Int, converView: View?, parent: ViewGroup?): View {
        var autoInput = TextView(mContext)
        autoInput.setPadding(mItemWidth/10, mItemWidth/10, mItemWidth/10, mItemWidth/10)
        var params: AbsListView.LayoutParams = AbsListView.LayoutParams(200, 100)
        autoInput.layoutParams = params

        if(position < count) {


            Log.d("emo", "this is from autoInputgridviewAdapter: " + mTextNamesList[position])
            autoInput.text = mTextNamesList[position]

        }
        return autoInput
    }

    override fun getItem(position: Int): Any {
        return mTextNamesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mTextNamesList.size
    }
}