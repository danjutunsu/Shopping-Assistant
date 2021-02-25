package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.list_layout.*
import kotlinx.android.synthetic.main.list_layout.view.*
import kotlinx.android.synthetic.main.list_layout.*

internal lateinit var delBtn: Button
internal lateinit var addBtn: Button



var myTitles = mutableListOf<String>()

var myDetails = mutableListOf<String>()

var myImages = mutableListOf<Int>()
var position = 0

var layoutManager: RecyclerView.LayoutManager? = null
var adapter: RecyclerView.Adapter<CustomViewHolder>? = null


class RecyclerAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)


        val holder = CustomViewHolder(itemView.inflate(R.layout.list_layout, parent, false))


        return holder
    }

    override fun getItemCount(): Int {
        return myTitles.size

    }
    fun setPosition(newPosition:Int) {
        position = newPosition
    }

    fun getPosition(): Int {
        return position
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemTitle.text = myTitles[position]
        holder.itemDetail.text = myDetails[position]
        holder.itemImage.setImageResource(myImages[position])
    }
}

class CustomViewHolder(val view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView


    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)

        delBtn = itemView.findViewById(R.id.deleteButton)!!
        addBtn = itemView.findViewById(R.id.addButton)!!


        //deletes item when x clicked
        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        //add item when + clicked
        addBtn!!.setOnClickListener {
            MainActivity().addToCart(adapterPosition)
        }

        view.setOnClickListener {
            RecyclerAdapter().setPosition(adapterPosition)

            //Changes color on click
//            if (clicked) {
//                view.setBackgroundColor(BLACK)
//            }
//            else
//            {
//                view.setBackgroundColor(BLACK)
//            }
//            MainActivity().deleteItem(adapterPosition)
            adapter?.notifyDataSetChanged()

        }
    }
}

class CartViewHolder(val view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView


    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)

        delBtn = itemView.findViewById(R.id.deleteButton)!!

        //deletes item when x clicked
        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        view.setOnClickListener {
            RecyclerAdapter().setPosition(adapterPosition)

            //Changes color on click
//            if (clicked) {
//                view.setBackgroundColor(BLACK)
//            }
//            else
//            {
//                view.setBackgroundColor(BLACK)
//            }
//            MainActivity().deleteItem(adapterPosition)
            adapter?.notifyDataSetChanged()

        }
    }
}