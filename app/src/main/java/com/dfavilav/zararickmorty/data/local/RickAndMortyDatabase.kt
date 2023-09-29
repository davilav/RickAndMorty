package com.dfavilav.zararickmorty.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RickAndMortyDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): RickAndMortyDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, RickAndMortyDatabase::class.java)
            } else {
                Room.databaseBuilder(context, RickAndMortyDatabase::class.java, "test_database.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}