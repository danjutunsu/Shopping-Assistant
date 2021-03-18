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


var thisNum: Int = 0

lateinit var button : Button

fun increaseNum(view: View, add: Int) {
    thisNum += add
}

class BrowseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        layoutManager = LinearLayoutManager(this)
        browseRecyclerView.layoutManager = layoutManager

        browseAdapter = BrowseRecyclerAdapter()
        browseRecyclerView.adapter = browseAdapter

        supportActionBar?.title = "Browse Food"

        searchInventory("a")
    }

    fun searchInventory(input: String) {
        InventoryDBHandler(this, null,null,1).findSuggestion(input)
    }

    var thisNum: Int = 0

    fun changeNum(pos: Int) {
        thisNum = pos
        browseAdapter!!.notifyDataSetChanged()
        println(thisNum)
    }

    private fun callInventory() {
        for (element in inventory) {
            myTitles.add(element)
            myDetails.add(element + " Details!")
            myImages.add(R.drawable.food)
            suggestions.add("Used in: " + getSuggestion(element))
            browseAdapter!!.notifyDataSetChanged()
        }
    }

    fun getSuggestion(s: String): String? {
//        return myTitles[position]
        val dbHandler = MyDBHandler(this, null, null, 1)
        val name: String? = dbHandler.findSuggestion(s)
        return name
    }

    fun getSpecificSuggestion(s: String): Recipe? {
//        return myTitles[position]
        val dbHandler = MyDBHandler(this, null, null, 1)
        val name: Recipe? = dbHandler.findRecipe(s)
        return name
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

    fun addToCart(pos: Int) {

        var name = myTitles[pos]
        var details = myDetails[pos]
        val dbHandler = CartDBHandler(this, null, null, 1)

        var db = dbHandler.writableDatabase

        db.execSQL("INSERT INTO cart('food_name', 'group') VALUES($name, $details")
        cart.add(myTitles[pos])
        myTitles.add(myTitles[pos])
        myDetails.add(myTitles[pos] + " Details!")
        myImages.add(R.drawable.food)
        myInstructions.add("INSTRUCTIONS")
        adapter!!.notifyDataSetChanged()
    }

    fun getSpecificSuggestion(view: View) {
        myTitles.clear()
        myDetails.clear()
        myImages.clear()
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

