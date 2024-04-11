package com.example.doubletapp3

import Habit
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp3.databinding.ListItemBinding
import java.text.SimpleDateFormat


class Adapter (private val habitsList: List<Habit>, private val listener: ItemClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val binding = ListItemBinding.bind(itemView)

        private val title: TextView = binding.title
        private val description: TextView = binding.description
        private val priority: TextView = binding.priority
        //private val type: TextView = binding.type
        private val edit_date: TextView = binding.editDate
        private val timing: TextView = binding.timing

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(habit: Habit) {
            title.text = habit.title
            description.text = habit.description
            priority.text = "${habit.priority + 1}"
            //type.text = habit.type
            edit_date.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(habit.edit_date)
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
        holder.itemView.setOnClickListener { listener.onClick(habitsList[position], position)
        this.notifyItemChanged(position)}
    }

    interface ItemClickListener {
        fun onClick(habit: Habit, position: Int)
    }
}