package com.example.ui.habitinfo

import Constants
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.MyApplication
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitType
import com.example.domain.entity.Priority
import com.example.doubletapp3.R
import com.example.doubletapp3.databinding.FragmentHabitInfoBinding
import com.example.ui.ColorPicker
import com.example.vm.HabitViewModel
import java.util.*
import javax.inject.Inject


class FragmentHabitInfo : Fragment() {
    private lateinit var binding: FragmentHabitInfoBinding
    private lateinit var colorPicker: ColorPicker

    @Inject
    lateinit var habitCreateViewModel: HabitViewModel

    private var idHabit: String? = null
    private var doneDateHabit: List<Int> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHabitInfoBinding.inflate(inflater)

        if (isAdded) {
            addColorPicker()
            setObserver()

            if (arguments?.containsKey(Constants.KEY_REQUEST_CODE) == true) {
                if (arguments?.get(Constants.KEY_REQUEST_CODE) == Constants.REQUEST_HABIT_EDITED) {
                    habitCreateViewModel.habit.value = habitCreateViewModel
                        .getHabitByName(requireArguments().getString(Constants.KEY_HABIT_INFO)!!)!!
                }
            }
            loadHabitField()

        }
        return binding.root
    }

    private fun addColorPicker() {
        colorPicker = ColorPicker(requireContext(),
            binding.colorSelectedSquare,
            binding.colorRadiogroup,
            binding.colorpickerInfo)
    }

    private fun loadHabitField(){
        if (habitCreateViewModel.habit.value !== null) {
            binding.titleInput.setText(habitCreateViewModel.habit.value!!.title)
            binding.descriptionInput.setText(habitCreateViewModel.habit.value!!.description)
            binding.daysInput.setText(habitCreateViewModel.habit.value!!.days.toString())
            binding.repeatInput.setText(habitCreateViewModel.habit.value!!.repeat.toString())
            (binding.radioGroup.getChildAt(habitCreateViewModel.habit.value!!.type.ordinal) as RadioButton).isChecked = true
            binding.prioritySelector.setSelection(
                habitCreateViewModel.habit.value!!.priority.ordinal)
            habitCreateViewModel.habit.value!!.color?.let { colorPicker.changeColorSample(it) }
            idHabit = habitCreateViewModel.habit.value!!.id
            doneDateHabit = habitCreateViewModel.habit.value!!.doneDates
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication)
            .appComponent
            .habitCreateSubComponent()
            .build()
            .inject(this)

        super.onAttach(context)
    }

    private fun setObserver(){
        binding.submitHabitButton.setOnClickListener {
            submitHabit()
        }
        habitCreateViewModel.habit.observe(viewLifecycleOwner) {
            binding.titleInput.setText(it?.title)
            binding.descriptionInput.setText(it?.description)
            binding.repeatInput.setText(it?.repeat.toString())
            binding.daysInput.setText(it?.days.toString())
            (binding.radioGroup.getChildAt(it.type.ordinal) as RadioButton).isChecked = true
            binding.prioritySelector.setSelection(it.priority.ordinal)
            it.color?.let { it1 -> colorPicker.changeColorSample(it1) }
        }
    }

    private fun submitHabit(){
        if (checkValidFields()) {
            Toast.makeText(requireContext(),
                "Can't save habit.\nFill the form correctly.",
            Toast.LENGTH_SHORT).show()
        }
        else {
            val checkedType = view?.findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId)
            val result = Habit(
                title = binding.titleInput.text.toString(),
                description = binding.descriptionInput.text.toString(),
                priority = Priority.valueOf(binding.prioritySelector.selectedItem.toString().uppercase()),
                repeat = binding.repeatInput.text.toString().toInt(),
                days = binding.daysInput.text.toString().toInt(),
                type = HabitType.valueOf(checkedType?.text.toString().uppercase()),
                editDate = (Date().time / 1000).toInt(),
                doneDates = doneDateHabit,
                id = habitCreateViewModel.habit.value?.id,
                color = colorPicker.selectedColor!!
            )
            habitCreateViewModel.addHabit(result)
            findNavController().navigate(R.id.action_fragmentHabitInfo_to_fragmentHome)
        }
    }

    private fun checkValidFields(): Boolean {
        return (binding.titleInput.text.isEmpty() ||
                binding.descriptionInput.text.isEmpty()||
                binding.daysInput.text.isEmpty() ||
                binding.repeatInput.text.isEmpty() ||
                binding.radioGroup.checkedRadioButtonId == -1 ||
                binding.prioritySelector.selectedItem.toString().isEmpty() ||
                colorPicker.selectedColor == null)
    }
}