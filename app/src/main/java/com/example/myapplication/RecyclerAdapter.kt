package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.w3c.dom.Text

internal lateinit var delBtn: ImageView
internal lateinit var addBtn: ImageView
internal lateinit var button: Button


var myTitles = mutableListOf<String>()
var ingredients = mutableListOf<String>()
var myDetails = mutableListOf<String>()
var myInstructions = mutableListOf<String>()
var suggestions = mutableListOf<String>()

var myImages = mutableListOf<Int>()
var position = RecyclerAdapter().getPosition()

var layoutManager: RecyclerView.LayoutManager? = null
var layoutManager2: RecyclerView.LayoutManager? = null
var adapter: RecyclerView.Adapter<CustomViewHolder>? = null
var browseAdapter: RecyclerView.Adapter<BrowseViewHolder>? = null
var cartAdapter: RecyclerView.Adapter<CartViewHolder>? = null



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
//        holder.itemInstructions.text = myInstructions[position]
        holder.itemImage.setImageResource(myImages[position])
    }
}

class CustomViewHolder(val view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var itemInstructions: TextView


    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        itemInstructions = itemView.findViewById(R.id.instructions)


        delBtn = itemView.findViewById(R.id.deleteButton)!!
        addBtn = itemView.findViewById(R.id.addButton)!!

        // sets position on click
        view.setOnClickListener {
            RecyclerAdapter().setPosition(adapterPosition)
        }

        //deletes item when x clicked
        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        //add item when + clicked
        addBtn!!.setOnClickListener {
            MainActivity().addToCart(adapterPosition)
        }

        view.setOnClickListener {
            println("Name: " + myTitles[adapterPosition])
            if (itemInstructions.isVisible) {
                itemInstructions.visibility = View.GONE
            } else
                itemInstructions.visibility = View.VISIBLE

//            MainActivity().addToDetails(adapterPosition)
        }

            //Changes color on click
//            if (clicked) {
//                view.setBackgroundColor(BLACK)
//            }
//            else
//            {
//                view.setBackgroundColor(BLACK)
//            }
//            MainActivity().deleteItem(adapterPosition)
//            adapter?.notifyDataSetChanged()

    }
}

class BrowseRecyclerAdapter : RecyclerView.Adapter<BrowseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseViewHolder {
        val itemView = LayoutInflater.from(parent.context)

        val holder = BrowseViewHolder(itemView.inflate(R.layout.list_layout, parent, false))

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


    override fun onBindViewHolder(holder: BrowseViewHolder, position: Int) {
        holder.itemTitle.text = myTitles[position]
        holder.itemDetail.text = myDetails[position]
//        holder.itemInstructions.text = myInstructions[position]
        holder.itemImage.setImageResource(myImages[position])
        holder.itemSuggestions.text = suggestions[position]
    }
}

class BrowseViewHolder(val view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var itemSuggestions: TextView
    var findMore: TextView
    lateinit var itemName: String


    init {
        button = itemView.findViewById(R.id.button)

        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        itemSuggestions = itemView.findViewById(R.id.suggestion)
        findMore  = itemView.findViewById(R.id.find_more)


        delBtn = itemView.findViewById(R.id.deleteButton)!!
        addBtn = itemView.findViewById(R.id.addButton)!!

//        button.visibility = INVISIBLE

        // sets position on click
        view.setOnClickListener {
            RecyclerAdapter().setPosition(adapterPosition)
        }

        //deletes item when x clicked
        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        //add item when + clicked
        addBtn!!.setOnClickListener {
            MainActivity().addToCart(adapterPosition)
        }

        view.setOnClickListener {
            itemName = (myTitles[adapterPosition])
            println(itemName)
            browseAdapter!!.notifyDataSetChanged()
        }

    }
}

class CartRecyclerAdapter : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)

        val holder = CartViewHolder(itemView.inflate(R.layout.list_layout, parent, false))

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


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.itemTitle.text = myTitles[position]
        holder.itemDetail.text = myDetails[position]
//        holder.itemInstructions.text = myInstructions[position]
        holder.itemImage.setImageResource(myImages[position])
        holder.itemSuggestions.text = suggestions[position]
    }
}

class CartViewHolder(val view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var itemSuggestions: TextView


    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        itemSuggestions = itemView.findViewById(R.id.suggestion)


        delBtn = itemView.findViewById(R.id.deleteButton)!!
        addBtn = itemView.findViewById(R.id.addButton)!!

        // sets position on click
        view.setOnClickListener {
            RecyclerAdapter().setPosition(adapterPosition)
        }

        //deletes item when x clicked
        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        //add item when + clicked
        addBtn!!.setOnClickListener {
            MainActivity().addToCart(adapterPosition)
        }
    }
}
