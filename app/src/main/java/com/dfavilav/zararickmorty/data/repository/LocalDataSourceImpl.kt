package com.dfavilav.zararickmorty.data.repository

import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.repository.LocalDataSource

class LocalDataSourceImpl(rickAndMortyDatabase: RickAndMortyDatabase) : LocalDataSource {
    private val characterDao = rickAndMortyDatabase.characterDao()
    override suspend fun getSelectedCharacter(id: Int): Character {
        return characterDao.getSelectedCharacter(id)
    }
}