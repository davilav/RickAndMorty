package com.dfavilav.zararickmorty.data.repository

import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.local.dao.CharacterDao
import com.dfavilav.zararickmorty.domain.model.Character
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class LocalDataSourceImplTest {
    @Mock
    lateinit var rickAndMortyDatabase: RickAndMortyDatabase

    @Mock
    lateinit var characterDao: CharacterDao

    lateinit var localDataSource: LocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(rickAndMortyDatabase.characterDao()).thenReturn(characterDao)
        localDataSource = LocalDataSourceImpl(rickAndMortyDatabase)
    }

    @Test
    fun getSelectedCharacter_shouldReturnCharacter() {
        val characterId = 1
        val mockCharacter = Character(
            id = 1,
            name = "Rick Sanchez",
            image = "",
            status = "Lorem ipsum dolor sit amet...",
            species = "",
            type = "",
            gender = ""
        )

        `when`(characterDao.getSelectedCharacter(characterId)).thenReturn(mockCharacter)
        runBlocking {
            val resultCharacter = localDataSource.getSelectedCharacter(characterId)
            verify(characterDao).getSelectedCharacter(characterId)
            assertEquals(mockCharacter, resultCharacter)
        }
    }
}
