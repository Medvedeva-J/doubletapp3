package com.example.doubletapp3

import retrofit2.http.*

interface HabitServerAPI {

    @GET("habit")
    suspend fun getHabits(): List<HabitServer>

    @POST("habit_done")
    suspend fun doneHabit(@Body habitDoneDTO: HabitDone)

    @PUT("habit")
    suspend fun saveHabit(@Body habitDTO: HabitServer): HabitUidServer

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun removeHabit(@Body habitUid: HabitUidServer)
}