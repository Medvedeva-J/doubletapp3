package com.example.doubletapp3

import HabitsListViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.doubletapp3.databinding.FiltersSlideBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class FragmentFiltersBottomSheet: Fragment() {
    lateinit var binding: FiltersSlideBinding
    lateinit var submitFilters: Button

    private val habitsListVM: HabitsListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submitFilters = binding.submitFilters
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FiltersSlideBinding.inflate(inflater,container,false)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.submitFilters.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                applyFilters(null)
            }
        })

        return binding.root
    }

    fun applyFilters(view: View?) {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        habitsListVM.filter(binding.findByName.text.toString(),
            binding.root.resources.getResourceName(
                binding.filtersRadioGroup.checkedRadioButtonId).last().digitToInt())
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHome().apply {
                arguments = Bundle().apply {
                }
            }
    }
}