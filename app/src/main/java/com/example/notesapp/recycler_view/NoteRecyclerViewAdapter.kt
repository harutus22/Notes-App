package com.example.notesapp.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.room.Note

class NoteRecyclerViewAdapter(private val onItemClicked: (Note) -> Unit) :
    ListAdapter<Note, NoteRecyclerViewAdapter.NoteHolder>(StringDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteHolder {
        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class NoteHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.title.text = note.name
            binding.date.text = note.createdAt
            binding.root.setOnClickListener {
                onItemClicked(note)
            }
        }
    }

    class StringDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem == newItem
        }
    }
}