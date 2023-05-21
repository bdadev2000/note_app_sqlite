package com.bdadev.noteappsqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.bdadev.noteappsqlite.database.NoteContract.NoteEntry.COLUMN_NAME_CONTENT
import com.bdadev.noteappsqlite.database.NoteContract.NoteEntry.COLUMN_NAME_TITLE
import com.bdadev.noteappsqlite.database.NoteContract.NoteEntry.TABLE_NAME
import com.bdadev.noteappsqlite.model.NoteModel


class NoteDatabaseManager(context: Context) {
    private val mContext: Context

    private var databaseHelper: NoteDatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    init {
        mContext = context
        databaseHelper = NoteDatabaseHelper(mContext)
        database = databaseHelper?.writableDatabase
    }

    fun fetchNote(): MutableList<NoteModel> {
        val db = databaseHelper?.readableDatabase
        val projection = arrayOf(BaseColumns._ID, COLUMN_NAME_TITLE, COLUMN_NAME_CONTENT)
        val selection = "$COLUMN_NAME_TITLE = ?"
        val selectionArgs = arrayOf("My Title")
        val sortOrder = "$COLUMN_NAME_CONTENT DESC"

        val cursor = db?.query(
            TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val notes = mutableListOf<NoteModel>()
        with(cursor) {
            while (this!!.moveToNext()) {
                val note = NoteModel(
                    getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(
                        getColumnIndexOrThrow(
                            COLUMN_NAME_TITLE
                        )
                    ), getString(getColumnIndexOrThrow(COLUMN_NAME_CONTENT))
                )
                notes.add(note)
            }
        }
        cursor?.close()

        return notes
    }

    fun addNote(title: String, content: String) {
        val db = databaseHelper?.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
            put(COLUMN_NAME_CONTENT, content)
        }

        val newRowId = db?.insert(TABLE_NAME, null, values)
    }


    fun updateNote(id : String,title: String, content: String) {
        val db = databaseHelper?.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
            put(COLUMN_NAME_CONTENT, content)
        }

        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        val count = db?.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }


    fun deleteNote(id: String) {
        // Define 'where' part of query.
        val db = databaseHelper?.writableDatabase

        val selection = "${BaseColumns._ID} LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        // Issue SQL statement.
        val deletedRows = db?.delete(TABLE_NAME, selection, selectionArgs)
    }
}