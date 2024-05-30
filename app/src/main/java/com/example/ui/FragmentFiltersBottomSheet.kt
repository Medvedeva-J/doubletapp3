package com.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.MyApplication
import com.example.doubletapp3.databinding.FiltersSlideBinding
import com.example.vm.HabitListViewModel
import com.example.vm.HabitListViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior


class FragmentFiltersBottomSheet: Fragment() {
    private lateinit var binding: FiltersSlideBinding
    private lateinit var habitListViewModel: HabitListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FiltersSlideBinding.inflate(inflater)

        if (isAdded) {
            setHabitListViewModel()
            setListeners()
        }

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        return binding.root
    }

    private fun setHabitListViewModel(){
        val appComponent = (requireActivity().application as MyApplication).appComponent

        val habitUseCase = appComponent.getHabitUseCase()
        habitListViewModel = ViewModelProvider(requireActivity(), HabitListViewModelFactory(habitUseCase))[
                 HabitListViewModel::class.java
        ]
    }

    private fun setListeners() {
        binding.submitFilters.setOnClickListener {
            BottomSheetBehavior.from(binding.bottomSheet).apply {
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            applyFilters()
        }

        binding.resetFilters.setOnClickListener {
            resetFilters()
        }

        binding.findByName.addTextChangedListener {
            habitListViewModel.filter(binding.findByName.text.toString(),
                binding.root.resources.getResourceName(
                    binding.filtersRadioGroup.checkedRadioButtonId).last().digitToInt()) }

        binding.filtersRadioGroup.setOnCheckedChangeListener { _, _ ->
            applyFilters()
        }
    }

    private fun applyFilters() {
        habitListViewModel.filter(binding.findByName.text.toString(),
            binding.root.resources.getResourceName(
                binding.filtersRadioGroup.checkedRadioButtonId).last().digitToInt())
    }

    private fun resetFilters() {
        binding.findByName.setText("")
        binding.filtersRadioButton1.isChecked = true
    }
}