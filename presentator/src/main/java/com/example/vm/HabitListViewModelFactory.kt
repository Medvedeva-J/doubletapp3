package com.example.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.uc.HabitsUseCase
import javax.inject.Inject

class HabitListViewModelFactory @Inject constructor(
    val useCase: HabitsUseCase,

    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return HabitListViewModel(useCase) as T
    }
}