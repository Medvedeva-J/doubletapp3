package com.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.doubletapp3.R
import com.example.doubletapp3.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var vpAdapter: HabitTypeAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vpAdapter = HabitTypeAdapter(this.requireActivity())
        viewPager2 = binding.viewPager
        viewPager2.adapter = vpAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = resources.getStringArray(R.array.types)[position].toString()
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.fab.setOnClickListener{ goToHabitCreation() }
        return binding.root
    }

    private fun goToHabitCreation() {
        val bundle: Bundle = Bundle()
        bundle.putInt(Constants.KEY_REQUEST_CODE, Constants.REQUEST_HABIT_CREATED)
        findNavController().navigate(R.id.action_fragmentHome_to_fragmentHabitInfo, bundle)
    }
}