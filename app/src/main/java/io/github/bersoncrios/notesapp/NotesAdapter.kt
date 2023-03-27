package io.github.bersoncrios.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.bersoncrios.notesapp.databinding.NoteRowBinding

class NotesAdapter(val context: Context, var notesList:List<Note>)
    : RecyclerView.Adapter<NotesAdapter.MyViewHolder>()
{

    lateinit var binding: NoteRowBinding

    // View Holder
    class MyViewHolder(var binding: NoteRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notes: Note){
            binding.note = notes
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = NoteRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notesList.size
}