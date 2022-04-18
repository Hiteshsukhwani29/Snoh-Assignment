package com.hitesh.snohassigment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TheProjectDAO {

    @Insert
    suspend fun insertTask(theProject: TheProject)

    @Query("SELECT * from yes_thats_me")
    fun getTask(): LiveData<List<TheProject>>
}