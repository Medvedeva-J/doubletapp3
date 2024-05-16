package com.example.doubletapp3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.doubletapp3.databinding.FragmentHabitInfoBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import Constants as const


class FragmentHabitInfo : Fragment() {
    private lateinit var binding: FragmentHabitInfoBinding
    private val habitVM: HabitViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.containsKey(const.KEY_REQUEST_CODE) == true) {
            if (arguments?.get(const.KEY_REQUEST_CODE) == const.REQUEST_HABIT_EDITED) {
                habitVM.habit.observe(viewLifecycleOwner) {
                    binding.titleInput.setText(it?.title)
                    binding.descriptionInput.setText(it?.description)
                    binding.repeatInput.setText(it?.repeat.toString())
                    binding.daysInput.setText(it?.days.toString())
                    (binding.radioGroup.getChildAt(it.type.ordinal) as RadioButton).isChecked = true
                    binding.prioritySelector.setSelection(it.priority.ordinal)
                }
            }
            else if (arguments?.get(const.KEY_REQUEST_CODE) == const.REQUEST_HABIT_CREATED) {
                val first = binding.radioGroup.children.first() as RadioButton
                first.isChecked = true
            }
        }
        binding.submitHabitButton.setOnClickListener { submitHabit() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SimpleDateFormat")
    private fun submitHabit() {
        val checkedType = view?.findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId)
        val result = Habit (
            title = binding.titleInput.text.toString(),
            description = binding.descriptionInput.text.toString(),
            priority = Priority.valueOf(binding.prioritySelector.selectedItem.toString().uppercase()),
            repeat = binding.repeatInput.text.toString().toInt(),
            days = binding.daysInput.text.toString().toInt(),
            type = HabitType.valueOf(checkedType?.text.toString().uppercase()),
            edit_date = (Date().time / 1000).toInt(),
            doneDates = emptyList(),
            id = habitVM.habit.value?.id
        )
        habitVM.addHabit(result)
        findNavController().navigate(R.id.action_fragmentHabitInfo_to_fragmentHome)
    }
}