package com.dfavilav.zararickmorty.data.repository

import androidx.paging.PagingData
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.repository.LocalDataSource
import com.dfavilav.zararickmorty.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
) {

    fun getAllCharacters(): Flow<PagingData<Character>> {
        return remote.getAllCharacters()
    }

    fun searchCharacters(query: String): Flow<PagingData<Character>> {
        return remote.searchCharacters(query)
    }

    suspend fun getSelectedCharacter(id: Int): Character {
        return local.getSelectedCharacter(id)
    }
}