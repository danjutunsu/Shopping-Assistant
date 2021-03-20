package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.list_layout.*

var stats = mutableListOf<String>()
var statsGroup = mutableListOf<String>()
var statsInstructions = mutableListOf<String>()

class StatsActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        layoutManager = LinearLayoutManager(this)
        statsRecyclerView.layoutManager = layoutManager

        statsAdapter = StatsRecyclerAdapter()

        statsRecyclerView.adapter = statsAdapter
        for (element in stats) {
            myTitles.add(element)
            myImages.add(R.drawable.food)
        }

        for (element in statsInstructions) {
            myInstructions.add(element)
        }

        for (element in statsGroup) {
            myDetails.add(element)
        }

        if (myTitles[0] in cart) {
            println("It's in the cart!")
        }

        supportActionBar?.title = "Stats"
    }

    fun openCard(pos: Int) {
        selectedItem.add(myTitles[pos])
        adapter!!.notifyDataSetChanged()

        myTitles.add(selectedItem[0])
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
        statsAdapter!!.notifyDataSetChanged()
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
        startActivity(intent)
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
        startActivity(intent)
    }

    fun goToSuggestions(view: View) {
        current = "SuggestionsActivity"

        val intent = Intent(this, SuggestionsActivity::class.java)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        startActivity(intent)
    }

    fun buyItems(view: View) {
        if (cart.size > 0) {
            BoughtHistoryDBHandler(this,null,null,1).addToHistory(view.context)
        }
    }

    fun goToCart(view: View) {
        current = "CartActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent);
    }
}