package com.hitesh.snohassigment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hitesh.snohassigment.R
import com.hitesh.snohassigment.TheProject

class TaskAdapter(private val mList: List<TheProject>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.task.text = itemsViewModel.task
        holder.description.text = itemsViewModel.description
        holder.date.text = itemsViewModel.date
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val task: TextView = ItemView.findViewById(R.id.tv_task)
        val description: TextView = ItemView.findViewById(R.id.tv_description)
        val date: TextView = ItemView.findViewById(R.id.tv_date)
    }

}