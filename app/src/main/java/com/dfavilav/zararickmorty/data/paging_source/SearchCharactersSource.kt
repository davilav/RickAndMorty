package com.dfavilav.zararickmorty.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.remote.RickAndMortyApi
import com.dfavilav.zararickmorty.domain.model.Character
import java.lang.Exception

class SearchCharactersSource(
    private val rickAndMortyApi: RickAndMortyApi,
    private val query: String,
    private val rickAndMortyDatabase: RickAndMortyDatabase
) : PagingSource<Int, Character>() {

    private val characterDao = rickAndMortyDatabase.characterDao()

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val apiResponse = rickAndMortyApi.searchCharacters(query)
            val characters = apiResponse.results
            rickAndMortyDatabase.withTransaction { characterDao.addCharacters(characters) }
            LoadResult.Page(
                data = characters.ifEmpty { emptyList() },
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}