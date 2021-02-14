package com.example.myapplication

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_layout.*

class MainActivity : AppCompatActivity() {

    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

    }
    var num = 0

    var titles : MutableList<String> = mutableListOf()

    var details : MutableList<String> = mutableListOf("Item one details", "Item two details", "Item three details",
            "Item four details")

    var images : MutableList<Int> = mutableListOf(R.drawable.android, R.drawable.android, R.drawable.android,
            R.drawable.android)

    var newTitles = titles

    var newDetails = details

    var newImages = images

    fun main(view: View) {
        myTitles.add("Item " + myTitles.size.plus(1))
        myDetails.add(myTitles[myTitles.size.minus(1)] + " Details!")
        myImages.add(R.drawable.android)
        adapter!!.notifyDataSetChanged()

        println("My Array::: ")
        println(newTitles)
        println(newDetails)
        println(newImages.size)

        num += 1
        println("num is now: " + num)
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
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent);
    }
}