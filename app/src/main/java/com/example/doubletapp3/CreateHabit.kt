package com.example.doubletapp3

import Habit
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar

class CreateHabit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
        var radio: RadioGroup = findViewById(R.id.radioGroup)
        radio.clearCheck()
        var first = radio.children.first() as RadioButton
        first.isChecked = true
    }

    fun SaveHabit(view: View) {
        var title: String = findViewById<EditText>(R.id.title_input).text.toString()
        var description: String = findViewById<EditText>(R.id.description_input).text.toString()
        var priority: Int = findViewById<Spinner>(R.id.priority_selector).selectedItemPosition
        var repeat: Int = findViewById<EditText>(R.id.repeat_input).text.toString().toInt()
        var days: Int = findViewById<EditText>(R.id.days_input).text.toString().toInt()
        var type_val = findViewById<RadioButton>(findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId).text.toString()
        val habit: Habit = Habit(title, description, priority, type_val, repeat, days)
        val intent: Intent = Intent();
        intent.putExtra("habit-info", habit as java.io.Serializable)
        setResult(RESULT_OK, intent);
        finish();
        }
    }