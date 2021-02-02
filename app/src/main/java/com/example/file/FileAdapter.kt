package com.example.file

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FilesAdapter(context: Context,
                  private val files:List<File>,
                  private val onClick:(File)->Unit)
    : RecyclerView.Adapter<FilesAdapter.FileViewHolder>(){

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesAdapter.FileViewHolder {
        val view = inflater.inflate(R.layout.list_file_row,parent,false)
        val viewHolder = FileViewHolder(view)
        view.setOnClickListener{
            onClick(files[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FilesAdapter.FileViewHolder, position: Int) {
        holder.fileName.text = files[position].name
    }

    override fun getItemCount(): Int = files.size

    class FileViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val fileName = view.findViewById<TextView>(R.id.fileName)
    }
}