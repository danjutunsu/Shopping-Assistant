package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cart.*

var stats = mutableListOf<String>()
var asked = mutableListOf<String>()
var original = mutableListOf<String>()
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

    fun clearSearches(view: View) {
        val dbHandler = HistoryDBHandler(view.context, null, null, 1)
        val accuracyDBHandler = AccuracyDBHandler(view.context,null,null,1)
        dbHandler.clearSearches(view.context)
        accuracyDBHandler.clearAccuracies(view.context)
    }
}