package com.dfavilav.zararickmorty.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dfavilav.zararickmorty.util.Constants.CHARACTER_REMOTE_KEYS_DATABASE_TABLE

@Entity(tableName = CHARACTER_REMOTE_KEYS_DATABASE_TABLE)
data class CharacterRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?
)
