package com.hitesh.snohassigment

import android.content.Context
import androidx.room.*

@Database(entities = [TheProject::class], version = 1)
abstract class TheProjectDatabase : RoomDatabase() {
    abstract fun theProjectDao(): TheProjectDAO

    companion object {
        private var INSTANCE: TheProjectDatabase? = null

        fun getDatabase(context: Context): TheProjectDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(
                            context.applicationContext,
                            TheProjectDatabase::class.java,
                            "SnohDB"
                        ).build()
                }
            }
            return INSTANCE!!
        }
    }
}