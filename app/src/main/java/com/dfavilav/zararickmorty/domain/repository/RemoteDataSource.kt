package com.dfavilav.zararickmorty.domain.repository

import androidx.paging.PagingData
import com.dfavilav.zararickmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllCharacters(): Flow<PagingData<Character>>
    fun searchCharacters(query: String): Flow<PagingData<Character>>
}