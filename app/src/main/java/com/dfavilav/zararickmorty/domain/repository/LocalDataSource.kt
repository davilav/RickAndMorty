package com.dfavilav.zararickmorty.domain.repository

import com.dfavilav.zararickmorty.domain.model.Character

interface LocalDataSource {

    suspend fun getSelectedCharacter(id: Int): Character
}