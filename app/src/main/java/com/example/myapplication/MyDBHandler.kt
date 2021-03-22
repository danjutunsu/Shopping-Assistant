package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Color.BLACK
import android.text.method.NumberKeyListener
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import kotlinx.android.synthetic.main.list_layout.*


class MyDBHandler(context: Context?, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
    }

    fun findSuggestion(suggestion: String): String? {
        val db = this.writableDatabase

        val sugg = "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$suggestion%\" ORDER BY RANDOM()"

        db.rawQuery(sugg, null).use {
            if (it.moveToFirst()) {
                val result = it.getString(0)
                return result
            }
        }
        return null
    }

    fun findItemSuggestion(context: Context, suggestion: String): String? {
        InventoryDBHandler(context,null,null,1).buildInventory()
        val db = this.writableDatabase

        val sugg = "SELECT * FROM $TABLE_recipes WHERE $INGREDIENTS LIKE  \"%$suggestion%\" ORDER BY RANDOM()"
        val cursor = db.rawQuery(sugg, null)

        db.rawQuery(sugg, null).use {
            while (cursor.moveToNext()) {
                var ingredient = cursor.getString(6)
                for (element in ingredient) {
                    if (ingredient.substringAfterLast(" ").capitalize() in inventory && (ingredient.substringAfterLast(" ").capitalize() !in asked) && (ingredient.substringAfterLast(" ").capitalize()) != suggestion) {
                        println("found one: " + ingredient.substringAfterLast(" ").capitalize())
                        return ingredient.substringAfterLast(" ").capitalize()
                    }
                }
            }
        }
        return null
    }

    fun findSuggestionIngredients(suggestion: String): String? {
        val db = this.writableDatabase

        val ingredients = "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

        db.rawQuery(ingredients, null).use {
            if (it.moveToFirst()) {
                val result = it.getString(6)
                return result
            }
        }
        return null
    }

    fun lookupRecipe(context: Context, pos: Int) {
        var name = myTitles[pos]
        val dbHandler = MyDBHandler(context, null, null, 1)

        var recipe = dbHandler.findRecipeBrowse(context, name)

        val HistdbHandler = HistoryDBHandler(context, null, null, 1)
        HistdbHandler.addToQuery(context, name)

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

    fun findRecipe(recipeName: String): Recipe? {
        val query =
                "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$recipeName%\""

        var db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var recipe: Recipe? = null

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()
        adapter!!.notifyDataSetChanged()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(6).replace("**", "\n")
            val instructions = cursor.getString(7).replace("**", "\n\n")
            recipe = Recipe(id, name, ingredients, instructions)


            myTitles.add(name)
            myDetails.add(ingredients)
            myInstructions.add(instructions)
            myImages.add(R.drawable.food)
            adapter!!.notifyDataSetChanged()

        }

        cursor.close()
        db.close()

        return recipe
    }

    fun findRecipeBrowse(context: Context, recipeName: String): Recipe? {
        var db = this.writableDatabase

        val query =
                "SELECT * FROM $TABLE_recipes WHERE $INGREDIENTS LIKE  \"%$recipeName%\""

        var cursor = db.rawQuery(query, null)

        var recipe: Recipe? = null

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(6).replace("**", "\n")
            val instructions = cursor.getString(7).replace("**", "\n\n")
            recipe = Recipe(id, name, ingredients, instructions)

            myTitles.add(name)
            myDetails.add(ingredients)
            myInstructions.add(instructions)
            myImages.add(R.drawable.food)
        }

        cursor.close()
        db.close()

        return recipe
    }

    fun findRecipeSuggestions(context: Context, recipeName: String): Recipe? {
        var db = this.writableDatabase

        val query =
            "SELECT * FROM $TABLE_recipes WHERE $INGREDIENTS LIKE  \"%$recipeName%\""

        var cursor = db.rawQuery(query, null)

        var recipe: Recipe? = null

        var i = 0
        var max = 5
        while (cursor.moveToNext()) { // Allows for a maximum search result of 'max'
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(6).replace("**", "\n")
            val instructions = cursor.getString(7).replace("**", "\n\n")
            recipe = Recipe(id, name, ingredients, instructions)

            myTitles.add(name)
            myDetails.add(ingredients)
            myInstructions.add(instructions)
            myImages.add(R.drawable.food)

            i++
            if (i == max) {
                break
            }
        }

        cursor.close()
        db.close()

        return recipe
    }

    fun findOccurrences(input: String) {
        var db = this.writableDatabase

        val query =
            "SELECT * FROM $TABLE_recipes WHERE $INGREDIENTS LIKE \"%$input%\""

        var cursor = db.rawQuery(query, null)

        var i = 0
        while (cursor.moveToNext()) { // Allows for a maximum search result of 'max'
//            println("ingredients: " + cursor.getString(6))
        }

        cursor.close()
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "database.db"
        internal val INVENTORY_DATABASE = "inventory.sql"
        val TABLE_recipes = "recipes"

        val COLUMN_ID = "RecipeID"
        val COLUMN_NAME = "RecipeName"
        val INGREDIENTS = "Ingredients"
    }

}