package com.example.doubletapp3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp3.databinding.ListItemBinding
import java.sql.Date
import java.text.SimpleDateFormat


class Adapter (private val listener: ItemClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {
    var habitsList: MutableList<Habit> = mutableListOf()
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        private val binding = ListItemBinding.bind(itemView)

        lateinit var id: String

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(habit: Habit) {
            id = habit.id.toString()
            binding.title.text = habit.title
            binding.description.text = habit.description
            binding.editDate.text = SimpleDateFormat("MM.dd.yyyy HH:mm").format(Date(habit.edit_date*1000L))
            binding.timing.text = "${habit.repeat} раз/${habit.days} дней"
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Habit>?) {
        habitsList = items as MutableList<Habit>
        notifyDataSetChanged()
    }

}