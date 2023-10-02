package com.dfavilav.zararickmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.local.dao.CharacterDao
import com.dfavilav.zararickmorty.data.remote.RickAndMortyApi
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalPagingApi
class RemoteDataSourceImplTest {

    @Mock
    lateinit var rickAndMortyApi: RickAndMortyApi

    @Mock
    lateinit var rickAndMortyDatabase: RickAndMortyDatabase

    @Mock
    lateinit var characterDao: CharacterDao

    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(rickAndMortyDatabase.characterDao()).thenReturn(characterDao)
        remoteDataSource = RemoteDataSourceImpl(rickAndMortyApi, rickAndMortyDatabase)
    }

    @Test
    fun getAllCharacters_shouldReturnFlowOfPagingData() {
        val resultFlow = remoteDataSource.getAllCharacters()
        assertNotNull(resultFlow)
    }

    @Test
    fun searchCharacters_shouldReturnFlowOfPagingData() {
        val query = "Rick"
        val fakePagingData = PagingData.from(listOf())
        `when`(rickAndMortyDatabase.characterDao()).thenReturn(characterDao)
        val resultFlow = remoteDataSource.searchCharacters(query)
        assertNotNull(resultFlow)
    }
}
