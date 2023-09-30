package com.dfavilav.zararickmorty.domain.use_cases

import com.dfavilav.zararickmorty.data.repository.Repository
import com.dfavilav.zararickmorty.domain.model.Character

class GetSelectedCharacterUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Int): Character {
        return repository.getSelectedCharacter(id)
    }
}