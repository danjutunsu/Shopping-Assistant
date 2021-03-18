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
import java.io.IOException
import java.lang.reflect.InvocationTargetException

var searchedRecipes = mutableListOf<String>()

class HistoryDBHandler(context: Context,
                       name: String?, factory: SQLiteDatabase.CursorFactory?,
                       version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {


    override fun onCreate(db: SQLiteDatabase) {

    }

    fun buildHistory() {
        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)

            searchedRecipes.add(name)
        }

        cursor.close()
        db.close()

        return
    }

    fun addToQuery(context: Context, input: String) {

        val dbHandler = HistoryDBHandler(context, null, null, 1)

        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        try {
            dbHandler.writableDatabase.execSQL("INSERT INTO history('query') VALUES('$input')")
        } catch (e: InvocationTargetException) {
            println(e.message)
        }

        searchedRecipes.add(input)

        println("Added " + input + " to history database.")

        while (cursor.moveToNext()) {
            val query = cursor.getString(0)

            println(query)
        }

        cursor.close()
        db.close()

        return
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {

    }

    fun clearSearches(context: Context) {
        val dbHandler = HistoryDBHandler(context, null, null, 1)

        dbHandler.writableDatabase.execSQL("DELETE FROM history WHERE 'query' NOT NULL")
    }

    fun searchDB(context: Context?) {
        var db = this.writableDatabase

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            searchedRecipes.add(name)
        }

        cursor.close()
        db.close()

        for (element in searchedRecipes) {
            println(element)
        }
        return
    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "history.db"
        val TABLE = "history"

        val COLUMN_NAME = "query"
    }

}