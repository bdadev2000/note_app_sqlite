package com.bdadev.noteappsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bdadev.noteappsqlite.adapter.NoteAdapter
import com.bdadev.noteappsqlite.database.NoteDatabaseManager
import com.bdadev.noteappsqlite.databinding.ActivityListNoteBinding
import com.bdadev.noteappsqlite.databinding.ActivityMainBinding
import com.bdadev.noteappsqlite.model.NoteModel

class ListNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListNoteBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var listNote: ArrayList<NoteModel>
    private lateinit var noteDatabaseManager: NoteDatabaseManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initDataNote()
        setupView()
        onActionClick()
    }

    private fun initDataNote() {
        listNote = ArrayList()
        noteDatabaseManager = NoteDatabaseManager(this)
        try {
            listNote.addAll(noteDatabaseManager.fetchNote())
        } catch (_: java.lang.Exception) {
            Toast.makeText(this@ListNoteActivity, R.string.data_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupView() {
        adapter = NoteAdapter(this, listNote)
        binding.rcvNote.adapter = adapter
        binding.rcvNote.layoutManager = LinearLayoutManager(this)
    }

    private fun onActionClick() {
        binding.btnCreateNote.setOnClickListener {
            val intent = Intent(this@ListNoteActivity, MainActivity::class.java)
            startActivity(intent)
        }

        adapter.onClickDeleteNoteItem = {note->
            noteDatabaseManager.deleteNote(note.id.toString())
        }

        adapter.onClickUpdateNoteItem = {note->
            val intent = Intent(this@ListNoteActivity, UpdateNoteActivity::class.java)
            intent.putExtra(NOTE_ITEM,note)
            startActivity(intent)
        }
    }
    companion object{
        val NOTE_ITEM = "NOTE_ITEM"

    }
}