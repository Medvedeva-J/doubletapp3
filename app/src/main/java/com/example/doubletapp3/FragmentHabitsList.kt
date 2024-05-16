package com.example.doubletapp3

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp3.databinding.FragmentHabitsListBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import Constants as const

class FragmentHabitsList : Fragment(), Adapter.ItemClickListener {
    private lateinit var binding: FragmentHabitsListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var message: TextView
    private var keyRequested: Int = -1
    private val habitsListVM: HabitsListViewModel by activityViewModels()
    private val habitVM: HabitViewModel by activityViewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater,container,false)

        recyclerView = binding.listView
        arguments?.takeIf {it.containsKey(const.KEY_HABIT_TYPE)}?.apply {
            keyRequested = getInt(const.KEY_HABIT_TYPE)
            when (keyRequested) {
                0-> {
                    habitsListVM.goodList.observe(viewLifecycleOwner) {
                        val adapter = getOrCreateAdapter(recyclerView)
                        adapter.updateItems(it)
                        disableMessage()
                    }
                }
                1 -> {
                    habitsListVM.badList.observe(viewLifecycleOwner) {
                        val adapter = getOrCreateAdapter(recyclerView)
                        adapter.updateItems(it)
                        disableMessage()
                    }
                }
            }
        }
        message = binding.emptyMsg

        return binding.root
    }

    override fun onClick(habit: Habit, position: Int) {
        val bundle = Bundle()
        bundle.putInt(const.KEY_REQUEST_CODE, const.REQUEST_HABIT_EDITED)
        bundle.putInt("item-pos", position)
        habitVM.set(habit)
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
                    habit.id?.let { habitsListVM.removeHabit(it) }
                }
            }
            setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    private fun getOrCreateAdapter(recyclerView: RecyclerView): Adapter {
        return if (recyclerView.adapter != null && recyclerView.adapter is Adapter) {
            recyclerView.adapter as Adapter
        } else {
            val bindableRecyclerAdapter = Adapter(this@FragmentHabitsList)
            recyclerView.adapter = bindableRecyclerAdapter
            bindableRecyclerAdapter
        }
    }

    private fun disableMessage() {
        message.isVisible = ((recyclerView.adapter == null) or (recyclerView.adapter?.itemCount == 0))
    }
}