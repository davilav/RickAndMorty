package com.dfavilav.zararickmorty.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dfavilav.zararickmorty.util.Constants.CHARACTER_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = CHARACTER_DATABASE_TABLE)
data class Character(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var image: String
)
