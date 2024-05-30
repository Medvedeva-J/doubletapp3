package com.example.ui.habitslist

import Constants
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.MyApplication
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitType
import com.example.doubletapp3.R
import com.example.doubletapp3.databinding.FragmentHabitsListBinding
import com.example.vm.HabitListViewModel
import com.example.vm.HabitListViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class FragmentHabitsList : Fragment(), Adapter.ItemClickListener {
    private lateinit var binding: FragmentHabitsListBinding
    private lateinit var recyclerView: RecyclerView
    private var keyRequested: HabitType = HabitType.GOOD
    private lateinit var habitListViewModel: HabitListViewModel
    @Inject
    lateinit var habitListViewModelFactory: HabitListViewModelFactory

    companion object;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHabitsListBinding.inflate(inflater)
        recyclerView = binding.listView

        if (isAdded) {
            createViewModel()
            habitListViewModel.loadHabit()
        }
        habitListViewModel.habitsList.observe(viewLifecycleOwner) {
            habitListViewModel.updateAllHabits()
            habitListViewModel.update()
        }
        arguments?.takeIf {it.containsKey(Constants.KEY_HABIT_TYPE)}?.apply {
            keyRequested = HabitType.createByType(requireArguments().getInt(Constants.KEY_HABIT_TYPE))
            when (keyRequested) {
                HabitType.GOOD-> {
                    habitListViewModel.goodList.observe(viewLifecycleOwner) {
                        val adapter = getOrCreateAdapter(recyclerView)
                        adapter.updateItems(it)
                        disableMessage()
                    }
                }
                HabitType.BAD -> {
                    habitListViewModel.badList.observe(viewLifecycleOwner) {
                        val adapter = getOrCreateAdapter(recyclerView)
                        adapter.updateItems(it)
                        disableMessage()
                    }
                }
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication)
            .appComponent
            .habitListSubComponent()
            .build()
            .inject(this)

        super.onAttach(context)
    }

    private fun createViewModel(){
        habitListViewModel = ViewModelProvider(requireActivity(), habitListViewModelFactory)[
                HabitListViewModel::class.java
        ]
    }

    private fun getOrCreateAdapter(recyclerView: RecyclerView): Adapter {
        return if (recyclerView.adapter != null && recyclerView.adapter is Adapter) {
            recyclerView.adapter as Adapter
        } else {
            val bindableRecyclerAdapter = Adapter(this@FragmentHabitsList, habitListViewModel)
            recyclerView.adapter = bindableRecyclerAdapter
            bindableRecyclerAdapter
        }
    }

    private fun disableMessage() {
        binding.emptyMsg.isVisible = ((recyclerView.adapter == null) or (recyclerView.adapter?.itemCount == 0))
    }

    override fun onClick(habit: Habit, position: Int) {
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_REQUEST_CODE, Constants.REQUEST_HABIT_EDITED)
        bundle.putString(Constants.KEY_HABIT_INFO, habit.id)
        findNavController().navigate(R.id.action_fragmentHome_to_fragmentHabitInfo, bundle)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onLongClick(habit: Habit) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            setTitle("Удалить привычку")
            setMessage("Точно хотите удалить привычку?")
            setPositiveButton("Да") { _: DialogInterface?, _: Int ->
                GlobalScope.launch(Dispatchers.IO) {
                    habit.id?.let { habitListViewModel.removeHabit(it) }
                }
            }
            setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }
}