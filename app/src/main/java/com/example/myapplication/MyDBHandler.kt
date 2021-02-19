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


        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val id = cursor.getInt(1)
            val ingredients = cursor.getString(8)
            recipe = Recipe(id, name, ingredients)


            myTitles.clear()
            myDetails.clear()
            myImages.clear()
            adapter!!.notifyDataSetChanged()

            myTitles.add(name)
            myDetails.add(ingredients)
            myImages.add(R.drawable.android)
            adapter!!.notifyDataSetChanged()
        }

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
    }
}