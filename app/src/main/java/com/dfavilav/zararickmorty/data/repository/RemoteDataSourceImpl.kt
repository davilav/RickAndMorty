package com.dfavilav.zararickmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.paging_source.CharacterRemoteMediator
import com.dfavilav.zararickmorty.data.paging_source.SearchCharactersSource
import com.dfavilav.zararickmorty.data.remote.RickAndMortyApi
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.repository.RemoteDataSource
import com.dfavilav.zararickmorty.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val rickAndMortyApi: RickAndMortyApi,
    private val rickAndMortyDatabase: RickAndMortyDatabase
) : RemoteDataSource {

    private val characterDao = rickAndMortyDatabase.characterDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllCharacters(): Flow<PagingData<Character>> {
        val pagingSourceFactory = { characterDao.getAllCharacters() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CharacterRemoteMediator(
                rickAndMortyApi,
                rickAndMortyDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchCharacters(query: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchCharactersSource(
                    rickAndMortyApi,
                    query,
                    rickAndMortyDatabase
                )
            }
        ).flow
    }
}