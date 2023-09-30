package com.dfavilav.zararickmorty.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.remote.RickAndMortyApi
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.model.CharacterRemoteKeys
import com.dfavilav.zararickmorty.util.Constants.CACHE_TIMEOUT
import com.dfavilav.zararickmorty.util.Constants.MINUTE
import com.dfavilav.zararickmorty.util.Constants.ONE
import com.dfavilav.zararickmorty.util.Constants.THOUSAND
import com.dfavilav.zararickmorty.util.Constants.ZERO_LONG
import com.dfavilav.zararickmorty.util.extractPageNumber

@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val rickAndMortyApi: RickAndMortyApi,
    private val rickAndMortyDatabase: RickAndMortyDatabase
) : RemoteMediator<Int, Character>() {

    private val characterDao = rickAndMortyDatabase.characterDao()
    private val characterRemoteKeysDao = rickAndMortyDatabase.characterRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = characterRemoteKeysDao.getRemoteKeys(ONE)?.lastUpdated ?: ZERO_LONG

        val diffInMinutes = (currentTime - lastUpdated) / THOUSAND / MINUTE
        return if (diffInMinutes.toInt() <= CACHE_TIMEOUT) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = rickAndMortyApi.getAllCharacters(page)
            if (response.results.isNotEmpty()) {
                rickAndMortyDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        characterDao.deleteAllCharacters()
                        characterRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.info?.prev?.extractPageNumber()
                    val nextPage = response.info?.next?.extractPageNumber()
                    val keys = response.results.map { hero ->
                        CharacterRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = System.currentTimeMillis()
                        )
                    }
                    characterRemoteKeysDao.addAllRemoteKeys(keys)
                    characterDao.addCharacters(response.results)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.info?.next == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterRemoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                characterRemoteKeysDao.getRemoteKeys(character.id)
            }
    }
}