package com.bdadev.noteappsqlite.database

import android.provider.BaseColumns

object NoteContract {
    object NoteEntry : BaseColumns{
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_CONTENT = "content"
    }
}