package com.somoplay.wefungame.libChat.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.LibChatGridViewItem

class LibChatGridViewAdapter : BaseAdapter {
    private var context : Context
    private var items  : ArrayList<LibChatGridViewItem>

    constructor(context: Context, items : ArrayList<LibChatGridViewItem>){
        this.context = context
        this.items = items
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       var convertView = convertView

        val item = items[position]
        if (convertView == null){
           val layoutInflater = LayoutInflater.from(context)
           convertView = layoutInflater.inflate(R.layout.grid_item, null)
       }


        val cover = convertView!!.findViewById(R.id.item_cover) as ImageView
        val itemImage = convertView.findViewById(R.id.item_image) as ImageView
        val itemName = convertView.findViewById(R.id.item_name) as TextView

        itemImage.setImageResource(item.getImageResource())
        itemName.setText(item.getName())


        return convertView
    }

    override fun getItem(position: Int): Any {
       return items[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @Override
    override fun getCount() : Int {
        return items.size
    }

}