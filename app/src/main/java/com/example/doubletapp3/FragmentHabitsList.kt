package com.example.doubletapp3

import Habit
import HabitsListViewModel
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp3.databinding.FragmentHabitsListBinding
import Constants as const


const val ARG_OBJECT = "object"

class FragmentHabitsList : Fragment(), Adapter.ItemClickListener {
    private lateinit var binding: FragmentHabitsListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var message: TextView
    var keyRequested: Int = -1

    private val viewModel: HabitsListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.listView
        arguments?.takeIf {it.containsKey(const.KEY_HABIT_TYPE)}?.apply {
            keyRequested = getInt(const.KEY_HABIT_TYPE)
            when (keyRequested) {
                0-> {
                    viewModel.GOOD_LIST.observe(viewLifecycleOwner, Observer {it ->
                        recyclerView.adapter = it.let { Adapter(it, this@FragmentHabitsList) }
                        disableMessage()
                    })
                }
                1 -> {
                    viewModel.BAD_LIST.observe(viewLifecycleOwner, Observer {it ->
                        recyclerView.adapter = Adapter(it, this@FragmentHabitsList)
                        disableMessage()
                    })                }
            }
        }
        message = binding.emptyMsg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
                when (requestCode) {
                const.REQUEST_HABIT_CREATED -> {
                    if (data != null && data.extras!!.containsKey(const.KEY_HABIT_INFO)) {
                        val item = data.extras?.get(const.KEY_HABIT_INFO) as Habit
                        item.position = viewModel.size()
                        viewModel.add(item)
                    }
                }
                const.REQUEST_HABIT_EDITED -> {
                    if (data != null && data.extras!!.containsKey(const.KEY_HABIT_INFO)) {
                        if (data.extras!!.containsKey(const.KEY_HABIT_INFO)) {
                            val item = data.extras?.get(const.KEY_HABIT_INFO) as Habit
                            item.position?.let { viewModel.edit(it, item) }
                        }
                    }
                }
            }
        } else {
            //Toast.makeText(activity, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onClick(habit: Habit, position: Int) {
        val sendIntent = Intent(activity, ActivityCreateHabit::class.java
        ).apply { putExtra(const.KEY_HABIT_INFO, habit)
            putExtra(const.KEY_REQUEST_CODE, const.REQUEST_HABIT_EDITED)}
        startActivityForResult(sendIntent, const.REQUEST_HABIT_EDITED)
    }

    private fun disableMessage() {
        message.isVisible = ((recyclerView.adapter == null) or (recyclerView.adapter?.itemCount == 0))
    }
}