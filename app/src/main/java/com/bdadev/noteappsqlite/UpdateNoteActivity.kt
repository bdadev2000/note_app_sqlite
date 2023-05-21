package com.bdadev.noteappsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bdadev.noteappsqlite.database.NoteDatabaseHelper
import com.bdadev.noteappsqlite.database.NoteDatabaseManager
import com.bdadev.noteappsqlite.databinding.ActivityMainBinding
import com.bdadev.noteappsqlite.databinding.ActivityUpdateNoteBinding
import com.bdadev.noteappsqlite.model.NoteModel

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var dbHelper: NoteDatabaseHelper
    private lateinit var noteDatabaseManager: NoteDatabaseManager
    private lateinit var itemNote: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupView()
        onActionClick()
    }

    private fun onActionClick() {
        binding.btnSubmit.setOnClickListener {
            if (binding.inputTitle.text.isEmpty() || binding.inputContent.text.isEmpty()) {
                Toast.makeText(
                    this@UpdateNoteActivity,
                    R.string.title_and_content_is_require,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //Add note to database
                noteDatabaseManager.updateNote(
                    itemNote.id.toString(),
                    binding.inputTitle.text.toString(),
                    binding.inputContent.text.toString()
                )
                startActivity()
            }
        }
    }

    private fun startActivity() {
        val intent = Intent(this@UpdateNoteActivity, ListNoteActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun setupView() {
        val bundle = intent.extras
        if (bundle != null) {
            itemNote = intent.getParcelableExtra(ListNoteActivity.NOTE_ITEM)!!
            binding.inputTitle.setText(itemNote.title)
            binding.inputContent.setText(itemNote.content)
        }
        noteDatabaseManager = NoteDatabaseManager(this)
        dbHelper = NoteDatabaseHelper(this@UpdateNoteActivity)
    }

    private fun cleanInput() {
        binding.inputTitle.text = null
        binding.inputContent.text = null
    }

    override fun onDestroy() {
        dbHelper.close()
        cleanInput()
        super.onDestroy()
    }
}