package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseOpenHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteAssetHelper(context, name, factory, version) {
    private val DATABASE_NAME: String = "db.db"
    private var DATABASE_VERSION = 1

    public override fun getDatabaseName(): String {
        return super.getDatabaseName()
    }

}
