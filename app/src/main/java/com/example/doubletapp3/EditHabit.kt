package com.example.doubletapp3

import Habit
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import java.util.EnumSet.range

class EditHabit : AppCompatActivity() {
    lateinit var currentHabit: Habit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_habit)

        currentHabit = this.intent.extras?.get("habit") as Habit
        findViewById<EditText>(R.id.title_input).setText(currentHabit.title)
        findViewById<EditText>(R.id.description_input).setText(currentHabit.description)
        findViewById<EditText>(R.id.repeat_input).setText(currentHabit.repeat.toString())
        findViewById<EditText>(R.id.days_input).setText(currentHabit.days.toString())

        var radio: RadioGroup = findViewById(R.id.radioGroup)
        radio.clearCheck()
        for (child in radio.children) {
            var rb = child as RadioButton
                child.isChecked = rb.text == currentHabit.type
        }

        var spinner: Spinner = findViewById(R.id.priority_selector)
        spinner.setSelection(currentHabit.priority)
        }

    fun UpdateHabit(view: View) {
        var title: String = findViewById<EditText>(R.id.title_input).text.toString()
        var description: String = findViewById<EditText>(R.id.description_input).text.toString()
        var priority: Int = findViewById<Spinner>(R.id.priority_selector).selectedItemPosition
        var repeat: Int = findViewById<EditText>(R.id.repeat_input).text.toString().toInt()
        var days: Int = findViewById<EditText>(R.id.days_input).text.toString().toInt()
        var type_val = findViewById<RadioButton>(findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId).text.toString()
        val result: Habit = Habit(title, description, priority, type_val, repeat, days, currentHabit.position)
        val intent: Intent = Intent();
        intent.putExtra("habit-info", result as java.io.Serializable)
        setResult(RESULT_OK, intent);
        finish();
    }
}