package com.practise.zenup.frags.todo.adopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.practise.zenup.R

class ToDoAdopter(private val list: MutableList<DocumentSnapshot>, private val onItemCheck : (DocumentSnapshot) -> Unit)
    : RecyclerView.Adapter<ToDoAdopter.ToDoViewHolder>(){

    inner class ToDoViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val username: AppCompatTextView = itemView.findViewById<AppCompatTextView>(R.id.name)
        val statusBox : CheckBox = itemView.findViewById(R.id.todo_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdopter.ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_tile, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdopter.ToDoViewHolder, position: Int) {
        holder.username.text = list[position].get("ToDo").toString()
        holder.statusBox.isChecked = list[position].get("Status") as Boolean
        holder.statusBox.setOnClickListener { onItemCheck(list[position]) }
    }

    override fun getItemCount(): Int = list.size
}