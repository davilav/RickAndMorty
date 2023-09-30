package com.dfavilav.zararickmorty.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dfavilav.zararickmorty.domain.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    fun getAllCharacters(): PagingSource<Int, Character>

    @Query("SELECT * FROM character_table WHERE id=:heroId")
    fun getSelectedCharacter(heroId: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacters(heroes: List<Character>)

    @Query("DELETE FROM character_table")
    suspend fun deleteAllCharacters()
}