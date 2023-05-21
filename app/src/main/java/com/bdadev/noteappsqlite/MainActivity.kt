package com.bdadev.noteappsqlite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bdadev.noteappsqlite.database.NoteDatabaseHelper
import com.bdadev.noteappsqlite.database.NoteDatabaseManager
import com.bdadev.noteappsqlite.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: NoteDatabaseHelper
    private lateinit var noteDatabaseManager: NoteDatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        createDatabase()
        setOnActionClick()
    }

    private fun createDatabase() {
        noteDatabaseManager = NoteDatabaseManager(this)
        dbHelper = NoteDatabaseHelper(this@MainActivity)
    }

    private fun setOnActionClick() {
        binding.btnSubmit.setOnClickListener {
            if (binding.inputTitle.text.isEmpty() || binding.inputContent.text.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    R.string.title_and_content_is_require,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //Add note to database
                noteDatabaseManager.addNote(
                    binding.inputTitle.text.toString(),
                    binding.inputContent.text.toString()
                )
                startActivity()
            }
        }
    }

    private fun startActivity() {
        val intent = Intent(this@MainActivity, ListNoteActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
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