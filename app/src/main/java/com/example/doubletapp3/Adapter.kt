package com.example.doubletapp3

import HabitsListViewModel
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp3.databinding.ListItemBinding
import kotlin.properties.Delegates


class Adapter (private val listener: ItemClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {
    var habitsList: MutableList<Habit> = mutableListOf()
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val binding = ListItemBinding.bind(itemView)

        lateinit var id: String
        private val title: TextView = binding.title
        private val description: TextView = binding.description
        private val priority: TextView = binding.priority
        private val edit_date: TextView = binding.editDate
        private val timing: TextView = binding.timing

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(habit: Habit) {
            id = habit.id
            title.text = habit.title
            description.text = habit.description
            priority.text = "${habit.priority + 1}"
            edit_date.text = habit.edit_date
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
        holder.bind(habitsList[position])
        holder.itemView.setOnLongClickListener {
            listener.onLongClick(habitsList[position])
            return@setOnLongClickListener true
        }
        holder.itemView.setOnClickListener { listener.onClick(habitsList[position], position)
        this.notifyItemChanged(position)}
    }

    interface ItemClickListener {
        fun onClick(habit: Habit, position: Int)

        fun onLongClick(habit: Habit)
    }

    fun updateItems(items: List<Habit>?) {
        habitsList = items as MutableList<Habit>
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        if(habitsList.isNotEmpty()) {
            habitsList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
        if(habitsList.size == 0){
            habitsList = mutableListOf()
        }

    }
}