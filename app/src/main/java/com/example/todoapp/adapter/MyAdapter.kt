package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.ApiData
import com.example.todoapp.model.Data


class MyAdapter(private val todoList:List<Data>) :RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val textViewId:TextView=itemView.findViewById(R.id.todoId)
        val textViewDelete:TextView=itemView.findViewById(R.id.todoDeleted)
        val textViewTodo:TextView=itemView.findViewById(R.id.todoTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(parent.context)
           .inflate(R.layout.each_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val apiData:ApiData=todoList.get(position)
//
//        holder.textViewId.text=apiData.data.get(0).id.toString()
//        holder.textViewDelete.text=apiData.data.get(0).isDeleted.toString()
//        holder.textViewTodo.text=apiData.data.get(0).todo

        val data:Data=todoList.get(position)

        holder.textViewId.text=data.id.toString()
        holder.textViewDelete.text=data.isDeleted.toString()
        holder.textViewTodo.text=data.todo.toString()


    }
}