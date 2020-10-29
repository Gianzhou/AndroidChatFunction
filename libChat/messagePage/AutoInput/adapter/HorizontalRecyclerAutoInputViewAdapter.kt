package com.somoplay.wefungame.libChat.messagePage.AutoInput.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.AutoInput.model.CategoryModel

class HorizontalRecyclerAutoInputViewAdapter: RecyclerView.Adapter<HorizontalRecyclerAutoInputViewAdapter.AutoInputViewHolder> {
    private var mData: ArrayList<CategoryModel>
    private var mInflater: LayoutInflater
    private var mContext: Context
    private lateinit var mOnClickItemListener: OnClickItemAutoListener

    constructor(context: Context, datas: ArrayList<CategoryModel>){
        this.mData = datas
        this.mContext = context
        mInflater = LayoutInflater.from(context)
    }

    class AutoInputViewHolder : RecyclerView.ViewHolder{
        var mTextBtn: TextView
        constructor(itemView: View):super(itemView){
            mTextBtn = itemView.findViewById(R.id.text_btn) as TextView
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalRecyclerAutoInputViewAdapter.AutoInputViewHolder {
        var view: View = mInflater.inflate(R.layout.autoinput_recyclerview_horizontal_item, parent, false)
        view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white))
        return HorizontalRecyclerAutoInputViewAdapter.AutoInputViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnClickItemListener(listener: OnClickItemAutoListener){
        this.mOnClickItemListener = listener
    }

    override fun onBindViewHolder(holder: HorizontalRecyclerAutoInputViewAdapter.AutoInputViewHolder, position: Int) {
        var model: CategoryModel = mData[position]
        holder.itemView.setPadding(8, 2, 8, 2)
        holder.mTextBtn.setText(model.categoryName)



        if(mOnClickItemListener != null){
            holder.itemView.setOnClickListener(View.OnClickListener {
                var pos: Int = holder.layoutPosition
                mOnClickItemListener.onItemClick(holder.itemView, pos, mData)
            })

            //delete, add, modify and so on
            // add function later
            holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                var pos: Int = holder.layoutPosition
                mOnClickItemListener.onItemLongClick(holder.itemView, pos, mData)
                return@OnLongClickListener false
            })

        }

    }


    public interface OnClickItemAutoListener{
        fun onItemClick(view: View, position: Int, datas: ArrayList<CategoryModel>)

        fun onItemLongClick(view: View, position:Int, datas: ArrayList<CategoryModel>)
    }

}