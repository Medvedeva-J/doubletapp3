package com.example.data.network

import com.example.data.network.data.HabitDoneServer
import com.example.data.network.data.HabitServer
import com.example.data.network.data.HabitUidServer
import retrofit2.http.*

interface HabitServerAPI {

    @GET("habit")
    suspend fun getHabits(): List<HabitServer>

    @POST("habit_done")
    suspend fun doneHabit(@Body habitDoneDTO: HabitDoneServer)

    @PUT("habit")
    suspend fun saveHabit(@Body habitDTO: HabitServer): HabitUidServer

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun removeHabit(@Body habitUid: HabitUidServer)
}