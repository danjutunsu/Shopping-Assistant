package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

var myTitles = mutableListOf<String>()

var myDetails = mutableListOf<String>()

var myImages = mutableListOf<Int>()

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    init {

    }

    private var details = arrayOf("Item one details", "Item two details", "Item three details",
        "Item four details", "Item five details", "Item six details", "Item seven details", "Item eight details")

    private var images = intArrayOf(R.drawable.android, R.drawable.android, R.drawable.android,
        R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android,
        R.drawable.android)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return myTitles.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = myTitles[position]
        holder.itemDetail.text = myDetails[position]
        holder.itemImage.setImageResource(myImages[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
        }
    }
}