package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.list_layout.*


class InventoryDBHandler(context: Context,
    name: String?, factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {


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

        val sugg = "SELECT * FROM $TABLE WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

        val cursor = db.rawQuery(sugg, null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val category = cursor.getString(1)
            myTitles.add(name)
            myDetails.add(category)
            myImages.add(R.drawable.food)
        }
        return null
    }

    fun findSuggestionIngredients(suggestion: String): String? {
        val db = this.writableDatabase

        val ingredients = "SELECT * FROM $TABLE WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

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

        val instructions = "SELECT * FROM $TABLE WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

        db.rawQuery(instructions, null).use {
            if (it.moveToFirst()) {
                val result = it.getString(9)
                return result
            }
        }
        return null
    }

    fun findRecipe(recipeName: String): Recipe? {
        val query =
                "SELECT * FROM $TABLE WHERE $COLUMN_NAME LIKE  \"%$recipeName%\""

        val idQuery =
                "SELECT RecipeID FROM $TABLE WHERE $COLUMN_NAME =  \"$recipeName\""


        var db = this.writableDatabase

        val cursors = db.rawQuery(idQuery, null)

        println("id is: " + cursors)

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
        cursors.close()
        db.close()

        return recipe
    }

    fun findRecipeBrowse(recipeName: String): Recipe? {

        val query =
                "SELECT * FROM $TABLE WHERCO$COLUMN_NAME LIKE  \"%$recipeName%\""

        var db = this.writableDatabase

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
        browseAdapter!!.notifyDataSetChanged()

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
            browseAdapter!!.notifyDataSetChanged()
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
        internal val DATABASE_NAME = "inventory.db"
        val TABLE = "inventory"

        val COLUMN_NAME = "food_name"
    }

}