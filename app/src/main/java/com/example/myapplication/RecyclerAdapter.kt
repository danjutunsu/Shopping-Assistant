package com.example.myapplication

import android.content.Intent
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

internal lateinit var delBtn: ImageView
internal lateinit var addBtn: ImageView
internal lateinit var likeBtn: ImageView
internal lateinit var likeButton: ImageView
internal lateinit var findBtn: Button

var myTitles = mutableListOf<String>()
var ingredients = mutableListOf<String>()
var myDetails = mutableListOf<String>()
var myInstructions = mutableListOf<String>()
var suggestions = mutableListOf<String?>()
var myImages = mutableListOf<Int>()
var position = BrowseRecyclerAdapter().getPosition()
var layoutManager: RecyclerView.LayoutManager? = null
var adapter: RecyclerView.Adapter<CustomViewHolder>? = null
var browseAdapter: RecyclerView.Adapter<BrowseViewHolder>? = null
var cartAdapter: RecyclerView.Adapter<CartViewHolder>? = null

class RecyclerAdapter : RecyclerView.Adapter<CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context)

        val holder = CustomViewHolder(itemView.inflate(R.layout.main_list_layout, parent, false))

        return holder
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
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
        holder.itemInstructions.text = myInstructions[position]

    }


}

class CustomViewHolder(view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var itemInstructions: TextView
    var instructionsHeader: TextView
    var cardView: CardView

    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        itemInstructions = itemView.findViewById(R.id.instructions)
        instructionsHeader = itemView.findViewById(R.id.instructionsHeader)
        cardView = itemView.findViewById(R.id.card_view)
        delBtn = itemView.findViewById(R.id.deleteButton)!!
        likeButton = itemView.findViewById(R.id.likeButtonMain)!!
        itemInstructions.visibility = GONE
        instructionsHeader.visibility = GONE

        // sets position on click
        view.setOnClickListener {
            RecyclerAdapter().setPosition(adapterPosition)
        }

        //deletes item when x clicked
        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        //add item to likedItems list
        likeButton!!.setOnClickListener {
            val dbHandler = FavoritesDBHandler(view.context, null, null, 1)

            dbHandler.addToLiked(view.context, adapterPosition)
        }

        var changed = 0

        var defaultCardColor = cardView.cardBackgroundColor.defaultColor

        // sets position on click
        view.setOnClickListener {
            BrowseRecyclerAdapter().setPosition(adapterPosition)
            if (changed == 0) {
                cardView.setBackgroundColor(parseColor("#ba0000"))
                instructionsHeader.visibility = VISIBLE
                itemInstructions.visibility = VISIBLE
                changed = 1
            } else if (changed == 1) {
                cardView.setBackgroundColor(defaultCardColor)
                instructionsHeader.visibility = GONE
                itemInstructions.visibility = GONE
                changed = 0
            }
        }
    }
}

class BrowseRecyclerAdapter() : RecyclerView.Adapter<BrowseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val holder = BrowseViewHolder(itemView.inflate(R.layout.browse_layout, parent, false))

        return holder
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
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
        holder.itemImage.setImageResource(myImages[position])
    }
}

class BrowseViewHolder(view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var itemSuggestions: TextView
    var cardView: CardView

    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        itemSuggestions = itemView.findViewById(R.id.suggestion)
        cardView = itemView.findViewById(R.id.card_view)

        addBtn = itemView.findViewById(R.id.addButton)!!
        findBtn = itemView.findViewById(R.id.findRecipes)!!
        var changed = 0
        var defaultCardColor = cardView.cardBackgroundColor.defaultColor

        // sets position on click
        view.setOnClickListener {
            BrowseRecyclerAdapter().setPosition(adapterPosition)

            BrowseActivity().changeNum(adapterPosition)
            if (changed == 0) {
                cardView.setBackgroundColor(parseColor("#ba0000"))
                changed = 1
            } else if (changed == 1) {
                cardView.setBackgroundColor(defaultCardColor)
                changed = 0
            }
        }

        addBtn!!.setOnClickListener {
            val dbHandler = CartDBHandler(view.context, null, null, 1)

            dbHandler.addToCart(view.context, adapterPosition)
        }

        findBtn!!.setOnClickListener {
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)

            val dbHandler = MyDBHandler(view.context, null, null, 1)
            dbHandler.lookupRecipe(view.context, adapterPosition)
            browseAdapter!!.notifyDataSetChanged()
        }
    }
}


class SuggestionsRecyclerAdapter() : RecyclerView.Adapter<SuggestionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val holder = SuggestionsViewHolder(itemView.inflate(R.layout.main_list_layout, parent, false))

        return holder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
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

    override fun onBindViewHolder(holder: SuggestionsViewHolder, position: Int) {
        holder.itemTitle.text = myTitles[position]
        holder.itemDetail.text = myDetails[position]
        holder.itemInstructions.text = myInstructions[position]
        holder.itemImage.setImageResource(myImages[position])
    }
}

class SuggestionsViewHolder(view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var cardView: CardView
    var itemInstructions: TextView
    var instructionsHeader: TextView

    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        cardView = itemView.findViewById(R.id.card_view)
        itemInstructions = itemView.findViewById(R.id.instructions)
        instructionsHeader = itemView.findViewById(R.id.instructionsHeader)
        delBtn = itemView.findViewById(R.id.deleteButton)!!
        likeBtn = itemView.findViewById(R.id.likeButtonMain)

        itemInstructions.visibility = GONE
        instructionsHeader.visibility = GONE

        var changed = 0
        var defaultCardColor = cardView.cardBackgroundColor.defaultColor

        // sets position on click
        view.setOnClickListener {
            SuggestionsRecyclerAdapter().setPosition(adapterPosition)
            if (changed == 0) {
                cardView.setBackgroundColor(parseColor("#ba0000"))
                instructionsHeader.visibility = VISIBLE
                itemInstructions.visibility = VISIBLE
                changed = 1
            } else if (changed == 1) {
                cardView.setBackgroundColor(defaultCardColor)
                instructionsHeader.visibility = GONE
                itemInstructions.visibility = GONE
                changed = 0
            }
        }

        delBtn!!.setOnClickListener {
            MainActivity().deleteItem(adapterPosition)
        }

        likeBtn!!.setOnClickListener {
            val dbHandler = FavoritesDBHandler(view.context, null, null, 1)

            dbHandler.addToLiked(view.context, adapterPosition)
        }
    }
}

class FavoritesRecyclerAdapter() : RecyclerView.Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = LayoutInflater.from(parent.context)

        val holder = FavoritesViewHolder(itemView.inflate(R.layout.favorites_layout, parent, false))

//        findButton = holder.itemView.findViewById(R.id.findRecipes)
//        findButton.setOnClickListener(this)

        return holder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
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

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.itemTitle.text = myTitles[position]
        holder.itemDetail.text = myDetails[position]
        holder.itemInstructions.text = myInstructions[position]
        holder.itemImage.setImageResource(myImages[position])

    }
}

class FavoritesViewHolder(view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var cardView: CardView
    var itemInstructions: TextView
    var instructionsHeader: TextView


    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        cardView = itemView.findViewById(R.id.card_view)
        itemInstructions = itemView.findViewById(R.id.instructions2)
        instructionsHeader = itemView.findViewById(R.id.instructionsHeader)
        delBtn = itemView.findViewById(R.id.deleteButton)!!
        itemInstructions.visibility = GONE
        instructionsHeader.visibility = GONE
        var changed = 0
        var defaultCardColor = cardView.cardBackgroundColor.defaultColor

        // sets position on click
        view.setOnClickListener {
            FavoritesRecyclerAdapter().setPosition(adapterPosition)
            if (changed == 0) {
                cardView.setBackgroundColor(parseColor("#ba0000"))
                instructionsHeader.visibility = VISIBLE
                itemInstructions.visibility = VISIBLE
                changed = 1
            } else if (changed == 1) {
                cardView.setBackgroundColor(defaultCardColor)
                instructionsHeader.visibility = GONE
                itemInstructions.visibility = GONE
                changed = 0
            }
        }

        delBtn!!.setOnClickListener {
            val dbHandler = FavoritesDBHandler(view.context, null, null, 1)

            dbHandler.deleteItem(view.context, adapterPosition)
            favoritesAdapter!!.notifyDataSetChanged()
        }
    }
}

class CartRecyclerAdapter : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context)

        val holder = CartViewHolder(itemView.inflate(R.layout.cart_layout, parent, false))

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
    }
}

class CartViewHolder(val view: View): ViewHolder(view) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView
    var cardView: CardView

    init {
        itemImage = itemView.findViewById(R.id.item_image)
        itemTitle = itemView.findViewById(R.id.item_title)
        itemDetail = itemView.findViewById(R.id.item_detail)
        cardView = itemView.findViewById(R.id.card_view)
        delBtn = itemView.findViewById(R.id.deleteButton)!!
        findBtn = itemView.findViewById(R.id.findRecipes)


        var changed = 0
        var defaultCardColor = cardView.cardBackgroundColor.defaultColor

        // sets position on click
        view.setOnClickListener {
            CartRecyclerAdapter().setPosition(adapterPosition)
            if (changed == 0) {
                cardView.setBackgroundColor(parseColor("#ba0000"))
                changed = 1
            } else if (changed == 1) {
                cardView.setBackgroundColor(defaultCardColor)
                changed = 0
            }
        }

        delBtn!!.setOnClickListener {
            val dbHandler = CartDBHandler(view.context, null, null, 1)
            dbHandler.deleteItem(view.context, adapterPosition)

            cartAdapter!!.notifyDataSetChanged()
        }

        findBtn!!.setOnClickListener {
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)

            val dbHandler = CartDBHandler(view.context, null, null, 1)

            dbHandler.lookupRecipe(view.context, adapterPosition)
            cartAdapter!!.notifyDataSetChanged()
        }
    }
}