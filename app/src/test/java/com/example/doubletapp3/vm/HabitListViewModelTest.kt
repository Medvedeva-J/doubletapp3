package com.example.doubletapp3.vm

import Constants
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.domain.entity.Habit
import com.example.domain.entity.HabitType
import com.example.domain.entity.Priority
import com.example.domain.uc.HabitsUseCase
import com.example.vm.HabitListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.rules.TestRule
import org.mockito.Mockito

internal class HabitListViewModelTest {

    private lateinit var UCMock: HabitsUseCase
    private lateinit var habitsListVM: HabitListViewModel

    private lateinit var goodHabitTest: Habit
    private lateinit var goodHabitTest2: Habit
    private lateinit var badHabitTest: Habit
    private lateinit var badHabitTest2: Habit

    @Before
    fun setUp(){
        goodHabitTest = Habit(
            id = "1",
            title = "test habit #1",
            description = "description description description",
            type = HabitType.GOOD,
            priority = Priority.HARD,
            days = 3,
            repeat = 5,
            color = 0xffffff,
            editDate = 4,
            doneDates = listOf()
        )

        goodHabitTest2 = Habit(
            id = "1",
            title = "test habit #2",
            description = "description #2",
            priority = Priority.HARD,
            type = HabitType.GOOD,
            repeat = 3,
            days = 5,
            color = 0xffffff,
            editDate = 4,
            doneDates = listOf()
        )


        badHabitTest = Habit(
            id = "1",
            title = "bad test habit #1",
            description = "description",
            priority = Priority.HARD,
            type = HabitType.BAD,
            repeat = 3,
            days = 5,
            color = 0xffffff,
            editDate = 5,
            doneDates = listOf()
        )

        badHabitTest2 = Habit(
            id = "1",
            title = "bad test habit #2",
            description = "description2",
            priority = Priority.MEDIUM,
            type = HabitType.BAD,
            repeat = 3,
            days = 5,
            color = 0xffffff,
            editDate = 4,
            doneDates = listOf()
        )

        UCMock = Mockito.mock(HabitsUseCase::class.java)
        Mockito.`when`(UCMock.habits).thenReturn(MutableLiveData(listOf(goodHabitTest, goodHabitTest2, badHabitTest, badHabitTest2)))

        habitsListVM = HabitListViewModel(UCMock)

    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun applyFilters() {
        val testAnswer = listOf(badHabitTest, badHabitTest2)

        habitsListVM.filter("bad", Constants.SORTING.NEW)
        val afterFiltrate = habitsListVM.getFilteredList()

            Assertions.assertEquals(testAnswer, afterFiltrate)
    }


    @Test
    fun resetFilter() {
        val filtersAfterReset = listOf("", Constants.SORTING.NEW)
        habitsListVM.resetFilters()
        Assertions.assertEquals(filtersAfterReset, habitsListVM.getFilters())
    }
}
