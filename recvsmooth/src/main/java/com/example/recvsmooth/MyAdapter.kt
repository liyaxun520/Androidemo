package com.example.recvsmooth

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(val items : List<String>,var context:Context,val itemClick:(String) ->Unit) :RecyclerView.Adapter<MyAdapter.MyViewHodler>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHodler {
        val view= LayoutInflater.from(context).inflate(R.layout.list_item,p0,false)
        return MyViewHodler(view,itemClick)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: MyViewHodler, p1: Int) {
       p0.bind(items[p1])

    }

    inner class MyViewHodler(itemView: View?,val itemClickListener:(String) ->Unit) : RecyclerView.ViewHolder(itemView!!) {
//        val categoryName=itemView?.findViewById<TextView>(R.id.categoryName)
        fun bind(str: String){
            itemView!!.categoryName.text = str
            itemView.setOnClickListener{
                itemClickListener(str)
            }
        }
    }
}