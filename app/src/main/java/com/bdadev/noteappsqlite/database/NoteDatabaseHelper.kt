package com.bdadev.noteappsqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class NoteDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS ${NoteContract.NoteEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${NoteContract.NoteEntry.COLUMN_NAME_TITLE} TEXT," +
                "${NoteContract.NoteEntry.COLUMN_NAME_CONTENT} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${NoteContract.NoteEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Note.db"
    }
}