package com.dfavilav.zararickmorty.domain.use_cases

import androidx.paging.PagingData
import com.dfavilav.zararickmorty.data.repository.Repository
import com.dfavilav.zararickmorty.domain.model.Character
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Character>> {
        return repository.getAllCharacters()
    }
}
