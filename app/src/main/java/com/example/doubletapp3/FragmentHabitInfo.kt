package com.example.doubletapp3

import HabitViewModel
import HabitsListViewModel
import Constants as const
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.doubletapp3.databinding.FragmentHabitInfoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentHabitInfo : Fragment() {
    private lateinit var binding: FragmentHabitInfoBinding
    private var param1: String? = null
    private var param2: String? = null
    private val habitVM: HabitViewModel by activityViewModels()
    private val listVM: HabitsListViewModel by activityViewModels()

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleInput = binding.titleInput
        descriptionInput = binding.descriptionInput
        repeatInput = binding.repeatInput
        daysInput = binding.daysInput
        prioritySelector = binding.prioritySelector
        radio = binding.radioGroup
        submitButton = binding.submitHabitButton

        if (arguments?.containsKey(const.KEY_REQUEST_CODE) == true) {
            if (arguments?.get(const.KEY_REQUEST_CODE) == const.REQUEST_HABIT_EDITED) {
                habitVM.habit.observe(viewLifecycleOwner, Observer {
                    titleInput.setText(it?.title)
                    descriptionInput.setText(it?.description)
                    repeatInput.setText(it?.repeat.toString())
                    daysInput.setText(it?.days.toString())

                    for (child in radio.children) {
                        val rb = child as RadioButton
                        child.isChecked = rb.text == it?.type
                    }
                })
            }
            else if (arguments?.get(const.KEY_REQUEST_CODE) == const.REQUEST_HABIT_CREATED) {
                val first = radio.children.first() as RadioButton
                first.isChecked = true
            }
        }
        submitButton.setOnClickListener { submitHabit() }
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
        val edit_date = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().time)
        if (arguments?.get(const.KEY_REQUEST_CODE) == const.REQUEST_HABIT_CREATED) {
            val result = Habit(title, description, priority, type, repeat, days, edit_date, currentHabit?.position)
            GlobalScope.launch(Dispatchers.IO) {
                (requireActivity() as ActivityMain).db.habitsDao().insert(result)
            }
        }
        else if (arguments?.get(const.KEY_REQUEST_CODE) == const.REQUEST_HABIT_EDITED) {
            val result = Habit(title, description, priority, type, repeat, days, edit_date, currentHabit?.position)
            result.id = habitVM.habit.value!!.id
            GlobalScope.launch(Dispatchers.IO) {
                (requireActivity() as ActivityMain).db.habitsDao().update(result)
            }
        }
        val a = parentFragmentManager.beginTransaction()
        a.replace(R.id.main_container, FragmentHome()).commit()
    }
}