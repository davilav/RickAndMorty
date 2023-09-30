package com.dfavilav.zararickmorty.domain.use_cases

import androidx.paging.PagingData
import com.dfavilav.zararickmorty.data.repository.Repository
import com.dfavilav.zararickmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

class SearchCharactersUseCase(
    private val repository: Repository
) {

    operator fun invoke(query: String): Flow<PagingData<Character>> {
        return repository.searchCharacters(query)
    }
}