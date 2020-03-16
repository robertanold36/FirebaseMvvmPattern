package com.example.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.Data.MyData
import com.example.newsapp.R
import kotlinx.android.synthetic.main.info_news.view.*

class InfoAdapter(
    var context:Context
):RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {

    private var dataList= mutableListOf<MyData>()

    fun setList(data:MutableList<MyData>){
        dataList=data
    }

    inner class InfoViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bindView(myData: MyData){
            Glide.with(context).load(myData.imageUrl).into(itemView.imageTitle)
            itemView.details.text=myData.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.info_news,parent,false)
        return InfoViewHolder(view)

    }

    override fun getItemCount(): Int {
        return if(dataList.size>0){
            dataList.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {

        val userDetails: MyData =dataList[position]
        holder.bindView(userDetails)

    }
}