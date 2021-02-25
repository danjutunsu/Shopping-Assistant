package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDBHandler(context: Context, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
//        val CREATE_RECIPES_TABLE = ("CREATE TABLE " +
//                TABLE_recipes + "("
//                + COLUMN_ID + " INTEGER PRIMARY KEY," +
//                COLUMN_NAME
//                + " TEXT," + ")")
//        db.execSQL(CREATE_RECIPES_TABLE)
    }

    fun findSuggestion(suggestion: String): String? {
        val sugg = "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$suggestion%\" ORDER BY RANDOM()"
        val db = this.writableDatabase

        db.rawQuery(sugg, null).use {
            if (it.moveToFirst()) {
                val result = it.getString(0)
                return result
            }
        }
        return null
    }

    fun findRecipe(recipeName: String): Recipe? {
        val query =
                "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$recipeName%\""

        val idQuery =
                "SELECT RecipeID FROM $TABLE_recipes WHERE $COLUMN_NAME =  \"$recipeName\""


        val db = this.writableDatabase

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
            val ingredients = cursor.getString(8)
            val instructions = cursor.getString(9)
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

        return recipe
    }

    fun findRecipeBrowse(recipeName: String): Recipe? {

        val query =
                "SELECT * FROM $TABLE_recipes WHERE $COLUMN_NAME LIKE  \"%$recipeName%\""

        val idQuery =
                "SELECT RecipeID FROM $TABLE_recipes WHERE $COLUMN_NAME =  \"$recipeName\""


        val db = this.writableDatabase

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
        browseAdapter!!.notifyDataSetChanged()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(8)
            val instructions = cursor.getString(9)
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

        return recipe
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {

    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "database.db"
        val TABLE_recipes = "recipes"

        val COLUMN_ID = "RecipeID"
        val COLUMN_NAME = "RecipeName"
        val INGREDIENTS = "Ingredients"
    }

}