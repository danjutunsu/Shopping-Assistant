package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.Color.BLACK
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import kotlinx.android.synthetic.main.list_layout.*


class MyDBHandler(context: Context?, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
//        val CREATE_RECIPES_TABLE = ("CREATE TABLE " +
//                TABLE_recipes + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY," +
//                COLUMN_NAME
//                + " TEXT," + ")")
//        db.execSQL(CREATE_RECIPES_TABLE)
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

    fun findSuggestionIngredients(suggestion: String): String? {
        val db = this.writableDatabase

        val ingredients = "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

        db.rawQuery(ingredients, null).use {
            if (it.moveToFirst()) {
                val result = it.getString(8)
                return result
            }
        }
        return null
    }

    fun findSuggestionInstructions(suggestion: String): String? {
        val db = this.writableDatabase

        val instructions = "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

        db.rawQuery(instructions, null).use {
            if (it.moveToFirst()) {
                val result = it.getString(9)
                return result
            }
        }
        return null
    }

    fun lookupRecipe(context: Context, pos: Int) {
        var name = myTitles[pos]
        val dbHandler = MyDBHandler(context, null, null, 1)

        var recipe = dbHandler.findRecipeBrowse(context, name)

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

//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst()
//
//            val name = cursor.getString(0)
//            val id = cursor.getInt(1)
//            val ingredients = cursor.getString(8)
//            recipe = Recipe(id, name, ingredients)
//            cursor.close()
//        }


        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()
        adapter!!.notifyDataSetChanged()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(8).replace("**", "\n")
            val instructions = cursor.getString(9).replace("**", "\n\n")
            recipe = Recipe(id, name, ingredients, instructions)


            myTitles.add(name)
            myDetails.add(ingredients)
            myInstructions.add(instructions)
            myImages.add(R.drawable.food)
            adapter!!.notifyDataSetChanged()

        }


//        val c = cursor
//        var buffer = StringBuffer()
//        val name = cursor.getString(0)
//        val id = cursor.getInt(1)
//        val ingredients = cursor!!.getString(8)
//        recipe = Recipe(id, name, ingredients)
//
//        while (cursor.moveToNext()) {
//            buffer.append(" " + name)
//        }
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

//        if (cursor.moveToFirst()) {
//            cursor.moveToFirst()
//
//            val name = cursor.getString(0)
//            val id = cursor.getInt(1)
//            val ingredients = cursor.getString(8)
//            recipe = Recipe(id, name, ingredients)
//            cursor.close()
//        }


        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(8).replace("**", "\n")
            val instructions = cursor.getString(9).replace("**", "\n\n")
            recipe = Recipe(id, name, ingredients, instructions)

            myTitles.add(name)
            myDetails.add(ingredients)
            myInstructions.add(instructions)
            myImages.add(R.drawable.food)
        }


//        val c = cursor
//        var buffer = StringBuffer()
//        val name = cursor.getString(0)
//        val id = cursor.getInt(1)
//        val ingredients = cursor!!.getString(8)
//        recipe = Recipe(id, name, ingredients)
//
//        while (cursor.moveToNext()) {
//            buffer.append(" " + name)
//        }
        cursor.close()
        db.close()

        return recipe
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {

    }

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