package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

internal lateinit var deleteIt: Button

var current = "MainActivity"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

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

    fun addItem(view: View) {
        myTitles.add("Item " + myTitles.size.plus(1))
        myDetails.add(myTitles[myTitles.size.minus(1)] + " Details!")
        myImages.add(R.drawable.food)
        adapter!!.notifyDataSetChanged()
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

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        adapter!!.notifyDataSetChanged()
        startActivity(intent);
    }


    fun addToCart(pos: Int) {
        cart.add(myTitles[pos])
        myTitles.add(myTitles[pos])
        myDetails.add(myTitles[pos] + " Details!")
        myImages.add(R.drawable.food)
        adapter!!.notifyDataSetChanged()
    }

    fun addToDetails(pos: Int) {
        var tempTitle = myTitles[pos]
        var tempDetails = myDetails[pos]
        var tempInstructions = myInstructions[pos]
        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()
        adapter!!.notifyDataSetChanged()

        myTitles.add(tempTitle)
        myDetails.add(tempDetails)
        myImages.add(R.drawable.food)
        myInstructions.add(tempInstructions)
        adapter!!.notifyDataSetChanged()
    }

    fun openCard(pos: Int) {
        selectedItem.add(myTitles[pos])
        adapter!!.notifyDataSetChanged()

        myTitles.add(selectedItem[0])
    }

    fun goToDetails(view: View) {
        current = "DetailsActivity"

        val intent = Intent(this, DetailsActivity::class.java)

        startActivity(intent)}
}
