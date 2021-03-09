package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.core.view.iterator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.activity_cart.cartRecyclerView
import kotlinx.android.synthetic.main.list_layout.*

var cart = mutableListOf<String>()
var selectedItem = mutableListOf<String>()

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        layoutManager = LinearLayoutManager(this)
        cartRecyclerView.layoutManager = layoutManager

        cartAdapter = CartRecyclerAdapter()
        cartRecyclerView.adapter = cartAdapter

        supportActionBar?.title = "Shopping Cart"

        val dbHandler = CartDBHandler(this, null, null, 1)

        dbHandler.callCart()
    }
    fun callCart(recipeName: String): Recipe?
    {
        val dbHandler = CartDBHandler(this, null, null, 1)



//        for (element in cart) {
//            myTitles.add(element)
//            myDetails.add(element + " Details!")
//            myImages.add(R.drawable.food)
//            cartAdapter!!.notifyDataSetChanged()
//        }
        return null
    }

    fun goHome(view: View) {
        current = "MainActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun addItem(view: View) {
        myTitles.add("Item " + myTitles.size.plus(1))
        myDetails.add(myTitles[myTitles.size.minus(1)] + " Details!")
        myImages.add(R.drawable.food)
        cartAdapter!!.notifyDataSetChanged()
    }

    fun getSuggestion(s: String): String? {
//        return myTitles[position]
        val dbHandler = MyDBHandler(this, null, null, 1)
        val name: String? = dbHandler.findSuggestion(s)
        return name
    }

    fun getSuggestion(view: View) {
        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        suggestion.visibility = GONE

        getSuggestion("Chicken")?.let { myTitles.add(it) }
        myDetails.add(getSuggestion("Chicken") + " Details!")
        myImages.add(R.drawable.food)
        cartAdapter!!.notifyDataSetChanged()
        println(getSuggestion("Chicken"))
    }

    fun goToFavorites(view: View) {
        current = "FavoritesActivity"

        val intent = Intent(this, FavoritesActivity::class.java)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()
        startActivity(intent);
    }

    fun goToBrowse(view: View) {
        current = "BrowseActivity"

        val intent = Intent(this, BrowseActivity::class.java)

        intent.putExtra("Main Activity", "Daniel")
        var b = Bundle()
        b.putBoolean("is active", true)
        intent.putExtras(b)
        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()
        startActivity(intent);
    }

    fun goToSuggestions(view: View) {
        current = "SuggestionsActivity"

        val intent = Intent(this, SuggestionsActivity::class.java)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()
        startActivity(intent);
    }
}