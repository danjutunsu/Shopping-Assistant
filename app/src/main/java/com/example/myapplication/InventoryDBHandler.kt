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
    }

    fun findSuggestion(suggestion: String) {
        val db = this.writableDatabase

        val sugg = "SELECT * FROM $TABLE WHERE $COLUMN_NAME LIKE  \"%$suggestion%\""

        val cursor = db.rawQuery(sugg, null)

        while (cursor.moveToNext()) {
            val category = cursor.getString(1)
            statsGroup.add(category)
        }
    }

    fun buildInventory() {
        inventory.clear()

        val db = this.writableDatabase

        val sugg = "SELECT * FROM $TABLE"

        val cursor = db.rawQuery(sugg, null)

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val category = cursor.getString(1)
            myTitles.add(name)
            myDetails.add(category)
            myImages.add(R.drawable.food)
            inventory.add(name)
            inventoryGroups.add(category)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "inventory.db"
        val TABLE = "inventory"

        val COLUMN_NAME = "food_name"
    }

}