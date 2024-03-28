package com.example.doubletapp3

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.doubletapp3.databinding.ActivityCreateHabitBinding
import Constants as cs

class ActivityCreateHabit : AppCompatActivity() {
    private lateinit var binding: ActivityCreateHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        when (this.intent.extras?.get(cs.KEY_REQUEST_CODE) as Int) {
            cs.REQUEST_HABIT_CREATED -> {
                supportActionBar?.title = "Создать привычку"
            }

            cs.REQUEST_HABIT_EDITED -> {
                supportActionBar?.title = "Редактировать привычку"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

