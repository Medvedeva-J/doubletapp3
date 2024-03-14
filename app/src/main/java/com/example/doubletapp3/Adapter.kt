package com.example.doubletapp3

import Habit
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.GnssAntennaInfo.Listener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class Adapter (private val habitsList: List<Habit>, val listener: ItemClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val priority: TextView = itemView.findViewById(R.id.priority)
        val type: TextView = itemView.findViewById(R.id.type)
        val timing: TextView = itemView.findViewById(R.id.timing)

        fun bind(habit: Habit, listener: ItemClickListener) {
            title.text = habit.title
            description.text = habit.description
            priority.text = "Приоритет ${habit.priority + 1}"
            type.text = habit.type
            timing.text = "${habit.repeat} раз/${habit.days} дней"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return habitsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habitsList[position], listener)
        holder.itemView.setOnClickListener { listener.onClick(habitsList[position], position)
        this.notifyItemChanged(position)}
    }

    interface ItemClickListener {
        fun onClick(habit: Habit, position: Int)
    }
}