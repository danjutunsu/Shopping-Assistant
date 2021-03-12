package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.list_layout.*


class CartDBHandler(context: Context,
                    name: String?, factory: SQLiteDatabase.CursorFactory?,
                    version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {


    override fun onCreate(db: SQLiteDatabase) {

    }

    fun lookupRecipe(context: Context, pos: Int) {
        var name = myTitles[pos]
        val dbHandler = MyDBHandler(context, null, null, 1)

        println("_____SEARCHING FOR: " + name + "________")

        var recipe = dbHandler.findRecipeBrowse(context, name)

        if (recipe != null) {
            println("RECIPE FOUND. Name is: " + recipe.recipeName + ". ID is: " + recipe.id)
            println("Ingredients are: " + recipe.ingredients)

            //Populate recyclerview
            recipe.recipeName?.let { myTitles.add(it) }
            recipe.ingredients?.let { myDetails.add(it) }
            recipe.instructions?.let { myInstructions.add(it) }
            myImages.add(R.drawable.food)

            //Toast
            val text = "Searching..."
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }
        else {
            val text = "No Match Found"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(context, text, duration)
            toast.show()
        }
    }

    fun deleteItem(context: Context, pos: Int) {
        var name = myTitles[pos]
        val dbHandler = CartDBHandler(context, null, null, 1)
        println(name)
        dbHandler.writableDatabase.execSQL("DELETE FROM cart WHERE food_name = '$name';")

        myTitles.removeAt(pos)
        myDetails.removeAt(pos)
        myImages.removeAt(pos)
        cartAdapter?.notifyDataSetChanged()
    }

    fun addToCart(context: Context, pos: Int) {
        var name = myTitles[pos]
        var details = myDetails[pos]
        val dbHandler = CartDBHandler(context, null, null, 1)

        dbHandler.writableDatabase.execSQL("INSERT INTO cart('food_name', 'group') VALUES('$name', '$details');")
        cart.add(myTitles[pos])
        myTitles.add(myTitles[pos])
        myDetails.add(myTitles[pos] + " Details!")
        myImages.add(R.drawable.food)
        myInstructions.add("INSTRUCTIONS")
        adapter!!.notifyDataSetChanged()
    }

    fun callCart() {
        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()
        cartAdapter!!.notifyDataSetChanged()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val group = cursor.getString(1)


            myTitles.add(name)
            myDetails.add(group)
            myImages.add(R.drawable.food)
            cartAdapter!!.notifyDataSetChanged()

        }

        cursor.close()
        db.close()

        return
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {

    }

    fun buildCart() {
        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()
        cartAdapter!!.notifyDataSetChanged()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getString(1)


            myTitles.add(name)
            myDetails.add(id)
            myImages.add(R.drawable.food)
            cartAdapter!!.notifyDataSetChanged()

        }

        cursor.close()
        db.close()


        println("CART CALLED")
        return
    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "cart.db"
        val TABLE = "cart"

        val COLUMN_NAME = "food_name"
    }

}