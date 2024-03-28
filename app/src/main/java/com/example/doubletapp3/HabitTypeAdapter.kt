package com.example.doubletapp3

import Constants
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HabitTypeAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FragmentHabitsList()
        fragment.arguments = Bundle().apply {
            putInt(Constants.KEY_HABIT_TYPE, position)
        }
        return fragment
    }
}