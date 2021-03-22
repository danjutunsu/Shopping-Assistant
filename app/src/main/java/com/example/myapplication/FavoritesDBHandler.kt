package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.list_layout.*


class FavoritesDBHandler(context: Context,
                         name: String?, factory: SQLiteDatabase.CursorFactory?,
                         version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {


    override fun onCreate(db: SQLiteDatabase) {

    }

    fun deleteItem(context: Context, pos: Int) {
        var name = myTitles[pos].replace("'", "''")
        val dbHandler = FavoritesDBHandler(context, null, null, 1)
        println(name)

        dbHandler.writableDatabase.execSQL("DELETE FROM recipes WHERE recipeName = '$name';")

        myTitles.removeAt(pos)
        myDetails.removeAt(pos)
        myImages.removeAt(pos)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {    }

    fun addToLiked(context: Context, pos: Int) {
        var name = myTitles[pos].replace("'", "''")
        var ingredients = myDetails[pos].replace("'", "''")
        var instructions = myInstructions[pos].replace("'", "''")

        val dbHandler = FavoritesDBHandler(context, null, null, 1)

        dbHandler.writableDatabase.execSQL("INSERT INTO recipes(RecipeName, Ingredients, Directions) VALUES('$name', '$ingredients', '$instructions')")

        likedRecipes.add(myTitles[pos])
        likedRecipeIngredients.add(myDetails[pos])
        likedInstructions.add(myInstructions[pos])

    }
    fun callFavorites() {
        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        myTitles.clear()
        myDetails.clear()
        myImages.clear()
        myInstructions.clear()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val ingredients = cursor.getString(6)
            val instructions = cursor.getString(7)

            myTitles.add(name)
            myDetails.add(ingredients)
            myInstructions.add(instructions)
            myImages.add(R.drawable.food)
        }

        cursor.close()
        db.close()

    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "favorites.db"
        val TABLE = "recipes"
    }
}