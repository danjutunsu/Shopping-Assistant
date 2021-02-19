package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.red
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_layout.*

internal lateinit var deleteIt: Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter

//        deleteIt = findViewById(R.id.deleteSelected)!!
//
//        deleteIt!!.setOnClickListener {
//            println("Hello there.")
//        }
    }


    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        deleteItem(RecyclerAdapter().getPosition())
        Toast.makeText(applicationContext,
        android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            "Maybe", Toast.LENGTH_SHORT).show()
    }

//    fun clickDelete(view: View) {
////        val button = view.findViewById<Button>(R.id.deleteButton)
//
//        val builder = AlertDialog.Builder(ContextThemeWrapper(this, android.R.style.Holo_SegmentedButton))
//
//        with(builder)
//        {
//            setTitle("Remove Item")
//            setMessage("Are you sure you want to remove this item from your cart?")
//            setPositiveButton("Yes", DialogInterface.OnClickListener(function = positiveButtonClick))
//            setNegativeButton(android.R.string.no, negativeButtonClick)
//            setNeutralButton("Maybe", neutralButtonClick)
//            show()
//        }
//    }

    fun delButtonClicked() {
        myTitles.removeAt(RecyclerAdapter().getPosition())
        myDetails.removeAt(RecyclerAdapter().getPosition())
        myImages.removeAt(RecyclerAdapter().getPosition())
        adapter!!.notifyDataSetChanged()
    }

    fun deleteItem(pos: Int) {
        myTitles.removeAt(pos)
        myDetails.removeAt(pos)
        myImages.removeAt(pos)
        adapter!!.notifyDataSetChanged()
    }

    fun addItem(view: View) {
        myTitles.add("Item " + myTitles.size.plus(1))
        myDetails.add(myTitles[myTitles.size.minus(1)] + " Details!")
        myImages.add(R.drawable.android)
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
            myImages.add(R.drawable.android)
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
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent);
    }

}
