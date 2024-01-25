package com.example.noteappfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter():RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var noteList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false))
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.SetNoteData(note)
    }

    fun setData( list: MutableList<Note>){
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private var titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private var descriptionTextView : TextView = itemView.findViewById(R.id.description_textview)

        fun SetNoteData(data: Note) {
            titleTextView.text = data.title
            descriptionTextView.text = data.description
        }

    }

}