package com.example.doubletapp3

import Habit
import HabitViewModel
import android.content.Intent
import Constants as const
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.doubletapp3.databinding.FragmentHabitInfoBinding
import java.text.SimpleDateFormat
import java.util.Calendar

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentHabitInfo : Fragment() {
    private lateinit var binding: FragmentHabitInfoBinding
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: HabitViewModel

    private lateinit var radio: RadioGroup
    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var repeatInput: EditText
    private lateinit var daysInput: EditText
    private lateinit var prioritySelector: Spinner
    private lateinit var submitButton: Button
    private var currentHabit: Habit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        currentHabit = activity?.intent?.extras?.get(const.KEY_HABIT_INFO) as Habit?
//        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return currentHabit?.let { HabitViewModel(Model(), it) } as T
//            }
//        }).get(HabitViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleInput = binding.titleInput
        descriptionInput = binding.descriptionInput
        repeatInput = binding.repeatInput
        daysInput = binding.daysInput
        prioritySelector = binding.prioritySelector
        radio = binding.radioGroup
        radio.clearCheck()
        submitButton = binding.submitHabitButton

        currentHabit = activity?.intent?.extras?.get(const.KEY_HABIT_INFO) as Habit?


//        viewModel.habit.observe(this, Observer { habit ->
//            titleInput.setText(habit?.title)
//            descriptionInput.setText(habit?.description)
//            repeatInput.setText(habit?.repeat.toString())
//            daysInput.setText(habit?.days.toString())
//
//            for (child in radio.children) {
//                val rb = child as RadioButton
//                child.isChecked = rb.text == habit?.type
//            }
//
//            habit?.priority?.let { prioritySelector.setSelection(it) }
//        })


        submitButton.setOnClickListener { submitHabit() }

        when (activity?.intent?.extras?.get(const.KEY_REQUEST_CODE) as Int) {
            const.REQUEST_HABIT_CREATED -> {
                val first = radio.children.first() as RadioButton
                first.isChecked = true
            }

            const.REQUEST_HABIT_EDITED -> {
                currentHabit?.let {
                    titleInput.setText(currentHabit!!.title)
                    descriptionInput.setText(currentHabit!!.description)
                    repeatInput.setText(currentHabit!!.repeat.toString())
                    daysInput.setText(currentHabit!!.days.toString())

                    for (child in radio.children) {
                        val rb = child as RadioButton
                        child.isChecked = rb.text == currentHabit!!.type
                    }

                    prioritySelector.setSelection(currentHabit!!.priority)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHabitInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun submitHabit() {
        val title: String = titleInput.text.toString()
        val description: String = descriptionInput.text.toString()
        val priority: Int = prioritySelector.selectedItemPosition
        val repeat: Int = repeatInput.text.toString().toInt()
        val days: Int = daysInput.text.toString().toInt()
        val type = view?.findViewById<RadioButton>(radio.checkedRadioButtonId)?.text.toString()
        val edit_date = Calendar.getInstance().time
        val result = Habit(title, description, priority, type, repeat, days, edit_date, currentHabit?.position)
        val intent = Intent();
        intent.putExtra(const.KEY_HABIT_INFO, result as Parcelable)
        activity?.setResult(AppCompatActivity.RESULT_OK, intent)
        activity?.finish()
    }
}