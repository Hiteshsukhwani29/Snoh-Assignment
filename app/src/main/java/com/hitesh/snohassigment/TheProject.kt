package com.hitesh.snohassigment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "yes_thats_me")
data class TheProject(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val code: Long,
    @ColumnInfo(name = "task")
    val task: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "description")
    val description: String
)
