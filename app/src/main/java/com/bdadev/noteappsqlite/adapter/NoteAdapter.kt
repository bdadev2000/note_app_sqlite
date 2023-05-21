package com.bdadev.noteappsqlite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bdadev.noteappsqlite.R
import com.bdadev.noteappsqlite.model.NoteModel

class NoteAdapter(context: Context, private val dataSet: ArrayList<NoteModel>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var onClickDeleteNoteItem : ((NoteModel)->Unit)? = null
    var onClickUpdateNoteItem : ((NoteModel)->Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView
        val txtContent: TextView

        init {
            txtTitle = view.findViewById(R.id.txt_title)
            txtContent = view.findViewById(R.id.txt_content)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_note, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.txtTitle.text = dataSet[position].title
        viewHolder.txtContent.text = dataSet[position].content
        viewHolder.itemView.setOnLongClickListener{
            onClickDeleteNoteItem?.invoke(dataSet[position])
            dataSet.removeAt(position)
            notifyItemRemoved(position)
            true
        }

        viewHolder.itemView.setOnClickListener {
            onClickUpdateNoteItem?.invoke(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

}