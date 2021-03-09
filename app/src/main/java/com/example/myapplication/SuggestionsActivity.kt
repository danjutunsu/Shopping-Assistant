package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_suggestions.*
import kotlinx.android.synthetic.main.list_layout.*
import kotlinx.android.synthetic.main.list_layout.item_detail
import kotlinx.android.synthetic.main.main_list_layout.*

var suggestionsAdapter: RecyclerView.Adapter<SuggestionsViewHolder>? = null


class SuggestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)

        layoutManager = LinearLayoutManager(this)
        suggestionsRecyclerView.layoutManager = layoutManager

        suggestionsAdapter = SuggestionsRecyclerAdapter()
        suggestionsRecyclerView.adapter = suggestionsAdapter
        supportActionBar?.title = "Suggestions"

        for (element in cart) {
            searchSuggestions(element)
        }
//        for (element in cart)
//            for (i in myDetails.indices) {
//                println(myDetails[i])
//                if ("garlic" in myDetails[i]) {
//                    println("garlic in " + myDetails[i] + "[" + i + "]")
//                } else {
//                    println("garlic not in " + myDetails[i] + "[" + i + "]")
//                }
//            }
    }

    fun searchSuggestions(input: String) {
        MyDBHandler(this, null,null,1).findRecipe(input)
    }

    fun getSuggestion(s: String): String? {
//        return myTitles[position]
        val dbHandler = MyDBHandler(this, null, null, 1)
        val name: String? = dbHandler.findSuggestion(s)
        title = name
        return name
    }

    fun getIngredients(s: String): String? {
//        return myTitles[position]
        val dbHandler = MyDBHandler(this, null, null, 1)
        val name: String? = dbHandler.findSuggestionIngredients(s)
        return name
    }

    fun lookupSuggestedRecipes(view: View) {
        val dbHandler = MyDBHandler(this, null, null, 1)

        var name: String = ""

        var recipe = dbHandler.findRecipeBrowse(this, name)

        if (recipe != null) {
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
            val text = "No Match Found"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    fun deleteItem(pos: Int) {
        myTitles.removeAt(pos)
        myDetails.removeAt(pos)
        myImages.removeAt(pos)
        if (current == "CartActivity") {
        cart.removeAt(pos)
        }
        adapter!!.notifyDataSetChanged()
    }

    fun addToLiked(pos: Int) {
        if (current.equals("MainActivity")) {
            likedRecipes.add(myTitles[pos])
            likedRecipeIngredients.add(myDetails[pos])
        }
        println(likedRecipes)
    }

    fun lookupRecipe(view: View) {
        val dbHandler = MyDBHandler(this, null, null, 1)

        val recipe = dbHandler.findRecipe(name.text.toString())

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
            adapter!!.notifyDataSetChanged()

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

    fun goToCart(view: View) {
        current = "CartActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        val intent = Intent(this, CartActivity::class.java)
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

    fun openCard(pos: Int) {
        selectedItem.add(myTitles[pos])
        adapter!!.notifyDataSetChanged()

        myTitles.add(selectedItem[0])
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

    fun goHome(view: View) {
        current = "MainActivity"

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
