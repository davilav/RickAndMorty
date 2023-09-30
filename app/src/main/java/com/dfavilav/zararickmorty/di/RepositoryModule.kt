package com.dfavilav.zararickmorty.di

import com.dfavilav.zararickmorty.data.repository.Repository
import com.dfavilav.zararickmorty.domain.use_cases.GetAllCharactersUseCase
import com.dfavilav.zararickmorty.domain.use_cases.GetSelectedCharacterUseCase
import com.dfavilav.zararickmorty.domain.use_cases.SearchCharactersUseCase
import com.dfavilav.zararickmorty.domain.use_cases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            GetAllCharactersUseCase(repository),
            SearchCharactersUseCase(repository),
            GetSelectedCharacterUseCase(repository)
        )
    }
}