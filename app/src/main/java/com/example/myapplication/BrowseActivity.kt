package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.*


var inventory = mutableListOf<String>()
var inventoryGroups = mutableListOf<String>()

lateinit var button : Button

class BrowseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        layoutManager = LinearLayoutManager(this)
        browseRecyclerView.layoutManager = layoutManager

        browseAdapter = BrowseRecyclerAdapter()
        browseRecyclerView.adapter = browseAdapter

        supportActionBar?.title = "Browse Food"

        callInventory()
    }

    var thisNum: Int = 0

    fun changeNum(pos: Int) {
        thisNum = pos
        browseAdapter!!.notifyDataSetChanged()
    }

    private fun callInventory() {
        InventoryDBHandler(this, null,null,1).buildInventory()
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

    fun goToCart(view: View) {
        current = "CartActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }

    fun goToBrowse(view: View) {
        current = "BrowseActivity"

        val intent = Intent(this, BrowseActivity::class.java)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()
        startActivity(intent);
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

