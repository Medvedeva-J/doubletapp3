package com.example.doubletapp3

import Habit
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp3.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.FieldPosition

class MainActivity : AppCompatActivity(), Adapter.ItemClickListener {
    val REQUEST_HABIT_CREATED : Int = 1
    val REQUEST_HABIT_EDITED : Int = 2

    private var habitsList: MutableList<Habit> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.listView)
        message = findViewById(R.id.empty_msg)
        message.isVisible = habitsList.size == 0
        var adapter = Adapter(habitsList, this)
        recyclerView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_HABIT_CREATED -> {
                    if (data != null && data.extras!!.containsKey("habit-info")) {
                        var item = data.extras?.get("habit-info") as Habit
                        item.position = habitsList.size
                        habitsList.add(item)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
                REQUEST_HABIT_EDITED -> {
                    if (data != null && data.extras!!.containsKey("habit-info")) {
                        if (data != null && data.extras!!.containsKey("habit-info")) {
                            var item = data.extras?.get("habit-info") as Habit
                            habitsList.set(item.position!!, item)
                            this.recyclerView.adapter?.notifyItemChanged(item.position!!)
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }

        message.isVisible = habitsList.size == 0
    }

    override fun onClick(habit: Habit, position: Int) {
        val sendIntent = Intent(this, EditHabit::class.java
        ).apply { putExtra("habit", habit) }
        startActivityForResult(sendIntent, REQUEST_HABIT_EDITED)
    }

    fun goToHabitCreation(view: View) {
        val sendIntent = Intent(this, CreateHabit::class.java)
        startActivityForResult(sendIntent, REQUEST_HABIT_CREATED)
    }


}