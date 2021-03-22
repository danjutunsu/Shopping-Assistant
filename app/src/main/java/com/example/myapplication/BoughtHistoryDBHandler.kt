package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.cart_layout.*
import kotlinx.android.synthetic.main.list_layout.*

var buyHistory = mutableListOf<String>()

class BoughtHistoryDBHandler(context: Context,
                             name: String?, factory: SQLiteDatabase.CursorFactory?,
                             version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {


    override fun onCreate(db: SQLiteDatabase) {

    }

    fun addToHistory(context: Context) {

        val dbHandler = BoughtHistoryDBHandler(context, null, null, 1)
        var db = this.writableDatabase


        commonItems.clear()

        cart.clear()

        val cartDbHandler = CartDBHandler(context, null, null, 1)

        cartDbHandler.callCart()
        cartDbHandler.buildCart()

        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)
        for (element in cart) {
            commonItems.add(element)
            println(commonItems)
        }
        println(commonItems)

        cartDbHandler.callCart()
        cartDbHandler.buildCart()

        if (cart.size > 0) {
            dbHandler.writableDatabase.execSQL("INSERT INTO history('item') VALUES('$commonItems')")
        }
        while (cursor.moveToNext()) {
            val query = cursor.getString(0)

            println(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "buyHistory.db"
        val TABLE = "history"

        val COLUMN_NAME = "item"
    }

}