package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.list_layout.*
import java.lang.reflect.InvocationTargetException

var totalItems = mutableListOf<String>()
var positiveResults = mutableListOf<String>()
var negativeResults = mutableListOf<String>()

class AccuracyDBHandler(context: Context,
                        name: String?, factory: SQLiteDatabase.CursorFactory?,
                        version: Int
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2) {


    override fun onCreate(db: SQLiteDatabase) {
    }

    fun addPositive(context: Context) {

        val db = this.writableDatabase

        db.execSQL("UPDATE accuracy SET positive = positive + 1")
        db.close()

        return
    }

    fun addNegative(context: Context?) {
        val db = this.writableDatabase

        db.execSQL("UPDATE accuracy SET negative = negative + 1")
        db.close()

        return
    }

    fun clearAccuracies(context: Context?) {
        val db = this.writableDatabase

        db.execSQL("UPDATE accuracy SET negative = 0;")
        db.execSQL("UPDATE accuracy SET positive = 0;")
        db.close()

        return
    }

    fun queryDB(context: Context?) {
        totalItems.clear()
        positiveResults.clear()
        negativeResults.clear()

        val db = this.writableDatabase


        var cursor = db.rawQuery("SELECT * FROM ${TABLE}", null)

        while (cursor.moveToNext()) {
            val negative = cursor.getString(0).toFloat()
            val positive = cursor.getString(1).toFloat()
            var sum = negative + positive
            var positivePercent = positive / sum
            var negativePercent = negative / sum

            totalItems.add(sum.toString())
            positiveResults.add(positivePercent.toString())
            positiveResults.add(positive.toString())
            negativeResults.add(negativePercent.toString())
            negativeResults.add(negative.toString())

            return
        }

        db.close()

        return
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                            newVersion: Int) {    }

    companion object {
        internal val DATABASE_NAME = "accuracy.db"
        val TABLE = "accuracy"
    }

}