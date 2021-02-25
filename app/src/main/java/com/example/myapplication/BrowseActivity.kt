package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.activity_browse.name
import kotlinx.android.synthetic.main.list_layout.*

var inventory = mutableListOf<String>("Chicken", "Rice", "Beans", "Milk", "Eggs", "Salt", "Pepper", "Sugar")

class BrowseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        layoutManager = LinearLayoutManager(this)
        browseRecyclerView.layoutManager = layoutManager

        browseAdapter = BrowseRecyclerAdapter()
        browseRecyclerView.adapter = browseAdapter

        callInventory()
        browseAdapter!!.notifyDataSetChanged()
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

    fun lookupRecipeBrowse(view: View) {
        val dbHandler = MyDBHandler(this, null, null, 1)

        var itemName = "chicken"
        val recipe = dbHandler.findRecipeBrowse(itemName)

        var textField = name.text.toString()

        if (textField.isEmpty()) {
            val text = "Field is empty"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        else if (recipe != null) {
            println("RECIPE FOUND. Name is: " + recipe.recipeName + ". ID is: " + recipe.id)
            println("Ingredients are: " + recipe.ingredients)

            //Populate recyclerview
            recipe.recipeName?.let { myTitles.add(it) }
            recipe.ingredients?.let { myDetails.add(it) }
            recipe.instructions?.let { myInstructions.add(it) }
            myImages.add(R.drawable.food)
            browseAdapter!!.notifyDataSetChanged()

            //Toast
            val text = "Searching..."
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        else {
            val text = "Not Match Found"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

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

    fun addItem(view: View) {
        myTitles.add("Item " + myTitles.size.plus(1))
        myDetails.add(myTitles[myTitles.size.minus(1)] + " Details!")
        myImages.add(R.drawable.food)
        browseAdapter!!.notifyDataSetChanged()
    }

    fun getSuggestion(view: View) {
        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        suggestion.visibility = GONE

        getSuggestion("Chicken")?.let { myTitles.add(it) }
        myDetails.add(getSuggestion("Chicken") + " Details!")
        myImages.add(R.drawable.food)
        browseAdapter!!.notifyDataSetChanged()
        println(getSuggestion("Chicken"))
    }

    fun getSpecificSuggestion(view: View) {
        myTitles.clear()
        myDetails.clear()
        myImages.clear()
    }

    fun thisButton(view: View) {
        RecyclerAdapter().setPosition(position)
        lookupRecipeBrowse(view)}
}

