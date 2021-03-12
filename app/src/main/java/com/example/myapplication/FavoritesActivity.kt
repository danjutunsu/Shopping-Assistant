package com.example.myapplication

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.RelativeDateTimeFormatter
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.Adapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.iterator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.list_layout.*
import kotlinx.android.synthetic.main.list_layout.view.*

var favoritesAdapter: RecyclerView.Adapter<FavoritesViewHolder>? = null
var likedRecipes = mutableListOf<String>()
var likedRecipeIngredients = mutableListOf<String>()
var likedInstructions = mutableListOf<String>()

class FavoritesActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        layoutManager = LinearLayoutManager(this)
        favoritesRecyclerView.layoutManager = layoutManager

        favoritesAdapter = FavoritesRecyclerAdapter()
        favoritesRecyclerView.adapter = favoritesAdapter

        supportActionBar?.title = "Favorite Recipes"
        val dbHandler = FavoritesDBHandler(this, null, null, 1)

        dbHandler.callFavorites()
        favoritesAdapter!!.notifyDataSetChanged()
    }

    private fun callFavorites() {
        for (element in likedRecipes) {
            myTitles.add(element)
            myImages.add(R.drawable.food)
        }
        for (element in likedRecipeIngredients) {
            myDetails.add(element)
        }
        for (element in likedInstructions) {
            myInstructions.add(element)
        }
        favoritesAdapter!!.notifyDataSetChanged()
    }

    fun goHome(view: View) {
        current = "MainActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()

        val intent = Intent(this, MainActivity::class.java)
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
        adapter!!.notifyDataSetChanged()
        startActivity(intent);
    }

    fun goToCart(view: View) {
        current = "CartActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent);
    }

    fun goToSuggestions(view: View) {
        current = "SuggestionsActivity"

        val intent = Intent(this, SuggestionsActivity::class.java)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()
        startActivity(intent);}
}
