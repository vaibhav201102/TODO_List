package com.vaibhavjoshi.todolist.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.todolist.database.TodoList
import com.vaibhavjoshi.todolist.databinding.LayoutNotesRowBinding

@SuppressLint("NotifyDataSetChanged")
class NotesAdapter(var hostList: List<TodoList>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var onItemEditClick: ((TodoList) -> Unit)? = null
    var onItemDeleteClick: ((TodoList,Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutNotesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = hostList[position]
        with(holder) {
            binding.noteText.text = currentItem.listNoteText

            binding.noteEdit.setOnClickListener {
                onItemEditClick?.invoke(currentItem)
            }

            binding.noteDelete.setOnClickListener {
                onItemDeleteClick?.invoke(currentItem,position)
            }

        }
    }

    override fun getItemCount(): Int = hostList.size

    inner class ViewHolder(val binding: LayoutNotesRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateList(list: List<TodoList>?) {
        hostList = list ?: emptyList()
        notifyDataSetChanged()
    }

}