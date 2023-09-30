package com.dfavilav.zararickmorty.domain.use_cases

data class UseCases(
    val getAllCharactersUseCase: GetAllCharactersUseCase,
    val searchCharactersUseCase: SearchCharactersUseCase,
    val getSelectedCharacterUseCase: GetSelectedCharacterUseCase
)
