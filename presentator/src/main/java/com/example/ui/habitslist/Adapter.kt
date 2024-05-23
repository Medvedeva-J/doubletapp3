package com.example.ui.habitslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Habit
import com.example.doubletapp3.R
import com.example.doubletapp3.databinding.ListItemBinding
import com.example.vm.HabitListViewModel
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class Adapter(private val listener: FragmentHabitsList,
              private val habitListViewModel: HabitListViewModel): ListAdapter<Habit, Adapter.ViewHolder>(HABIT_COMPARATOR) {
    private var habitsList: MutableList<Habit> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemBinding.bind(itemView)

        lateinit var id: String

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(habit: Habit) {
            id = habit.id.toString()
            binding.title.text = habit.title
            binding.description.text = habit.description
            binding.editDate.text =
                SimpleDateFormat("MM.dd.yyyy HH:mm").format(Date(habit.editDate * 1000L))
            binding.timing.text = "${habit.repeat} раз/${habit.days} дней"
            binding.priority.text = habit.priority.name[0].toString()
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
        holder.itemView.setOnClickListener {
            listener.onClick(habitsList[position], position)
            this.notifyItemChanged(position)
        }
        holder.itemView.findViewById<Button>(R.id.complete_habit_btn).setOnClickListener {
            habitListViewModel.completeHabit(holder.id)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<Habit>?) {
        habitsList = items as MutableList<Habit>
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(habit: Habit, position: Int)

        fun onLongClick(habit: Habit)
    }

    companion object {
        private val HABIT_COMPARATOR = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
