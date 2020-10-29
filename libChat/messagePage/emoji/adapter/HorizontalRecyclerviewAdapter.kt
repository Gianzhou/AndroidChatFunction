package com.somoplay.wefungame.libChat.messagePage.emoji.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.somoplay.wefungame.R
import com.somoplay.wefungame.libChat.messagePage.emoji.ImageModel
import com.somoplay.wefungame.libChat.messagePage.emoji.utils.ScreenUtils

class HorizontalRecyclerviewAdapter:
    RecyclerView.Adapter<HorizontalRecyclerviewAdapter.ViewHolder> {

    private var datas: ArrayList<ImageModel>
    private var mInflater: LayoutInflater
    private var context: Context
    private lateinit var onClickItemListener: OnClickItemListener

    constructor(context: Context, datas: ArrayList<ImageModel>){
        this.datas = datas
        this.context = context
        mInflater = LayoutInflater.from(context)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = mInflater.inflate(R.layout.recyclerview_horizontal_item, parent, false)
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var model: ImageModel = datas[position]
        holder.itemView.setPadding(8, 2, 8, 2)


        if(onClickItemListener != null){
            holder.itemView.setOnClickListener(View.OnClickListener {
                var pos: Int = holder.layoutPosition
                onClickItemListener.onItemClick(holder.itemView, pos, datas)
            })

            holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                var pos: Int = holder.layoutPosition
                onClickItemListener.onItemLongClick(holder.itemView, pos, datas)
                return@OnLongClickListener false
            })

        }

        /**
         * 动态计算底部tab的宽度。
         */
        var width: Int=ScreenUtils.getScreenWidth(context)
        var itemW: Float = width/10f
        var lp : ViewGroup.LayoutParams = holder.imageBtn.layoutParams
        lp.width = itemW.toInt()
        lp.height = itemW.toInt()

        holder.imageBtn.setImageDrawable(model.icon)
    /*    if(model.isSelected){
            holder.imageBtn.setBackgroundColor(ContextCompat.getColor(context,R.color.bg_horizontal_btn_selected))
        }else{
    //        holder.imageBtn.setBackgroundColor(R.color.bg_horizontal_btn_normal)
         //   holder.imageBtn.setBackgroundColor(ContextCompat.getColor(context,R.color.bg_horizontal_btn_normal))
        }*/

    }
    class ViewHolder : RecyclerView.ViewHolder{
        var imageBtn: ImageView
        constructor(itemView: View):super(itemView){
            imageBtn = itemView.findViewById(R.id.image_btn) as ImageView
        }
    }

    fun setOnClickItemListener(listener: OnClickItemListener){
        this.onClickItemListener = listener
    }

    public interface OnClickItemListener {
        fun onItemClick(view:View, position: Int, datas: ArrayList<ImageModel>)

        fun onItemLongClick(view:View, position:Int, datas: ArrayList<ImageModel>)

    }
}