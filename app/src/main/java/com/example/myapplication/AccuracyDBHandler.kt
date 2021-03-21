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
var sum = 0.0

class AccuracyDBHandler(context: Context,
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

    fun addPositive(context: Context) {

        val db = this.writableDatabase

        db.execSQL("UPDATE accuracy SET positive = positive + 1")

        println("Added to database.")

        db.close()

        return
    }

    fun addNegative(context: Context?) {
        val db = this.writableDatabase

        db.execSQL("UPDATE accuracy SET negative = negative + 1")

        println("Added to database.")

        db.close()

        return
    }

    fun clearDB(context: Context?) {
        val db = this.writableDatabase

        db.execSQL("UPDATE accuracy SET negative = 0;")

        db.execSQL("UPDATE accuracy SET positive = 0;")

        println("Cleared database.")
        db.close()

        return
    }

    fun setSum(input: Double) {
        sum = input
    }

    fun getSum(): Double {
        return sum
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
            setSum(sum.toDouble())

            totalItems.add(sum.toString())
            positiveResults.add(positivePercent.toString())
            positiveResults.add(positive.toString())
            negativeResults.add(negativePercent.toString())
            negativeResults.add(negative.toString())

            println("Negative " + negative)
            println("Positive " + positive)
            return
        }

        println("Queried database.")

        db.close()

        return
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                            newVersion: Int) {

    }

    companion object {

        private val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "accuracy.db"
        val TABLE = "accuracy"

        val COLUMN_TRUE = "positive"
        val COLUMN_FALSE = "negative"
    }

}