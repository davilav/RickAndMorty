package com.dfavilav.zararickmorty.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dfavilav.zararickmorty.data.local.dao.CharacterDao
import com.dfavilav.zararickmorty.data.local.dao.CharacterRemoteKeysDao
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.model.CharacterRemoteKeys

@Database(entities = [Character::class, CharacterRemoteKeys::class], version = 1)
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

    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
}