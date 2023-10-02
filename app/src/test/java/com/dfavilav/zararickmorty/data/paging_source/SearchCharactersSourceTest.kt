package com.dfavilav.zararickmorty.data.paging_source

import androidx.paging.PagingSource
import com.dfavilav.zararickmorty.data.local.RickAndMortyDatabase
import com.dfavilav.zararickmorty.data.remote.FakeRickAndMortyApi
import com.dfavilav.zararickmorty.data.remote.RickAndMortyApi
import com.dfavilav.zararickmorty.domain.model.Character
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import kotlin.test.assertTrue

class SearchCharactersSourceTest {

    private lateinit var rickAndMortyApi: RickAndMortyApi
    private lateinit var characters: List<Character>
    @Mock
    private lateinit var rickAndMortyDatabase: RickAndMortyDatabase

    @Before
    fun setup() {
        rickAndMortyDatabase = mock(RickAndMortyDatabase::class.java)
        rickAndMortyApi = FakeRickAndMortyApi()
        characters = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                image = "",
                status = "Lorem ipsum dolor sit amet...",
                species = "",
                type = "",
                gender = ""
            ),
            Character(
                id = 2,
                name = "Rick Sanchez",
                image = "",
                status = "Lorem ipsum dolor sit amet...",
                species = "",
                type = "",
                gender = ""
            ),
            Character(
                id = 3,
                name = "Rick Sanchez",
                image = "",
                status = "Lorem ipsum dolor sit amet...",
                species = "",
                type = "",
                gender = ""
            )
        )
    }

    @Test
    fun `Search api with empty hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val charactersSource = SearchCharactersSource(rickAndMortyApi = rickAndMortyApi, query = "Rick Sanchez", rickAndMortyDatabase)
            val loadResult = charactersSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = rickAndMortyApi.searchCharacters("").results

            assertTrue { result.isEmpty() }
        }

    @Test
    fun `Search api with non_existing hero name, assert empty heroes list and LoadResult_Page`() =
        runTest {
            val charactersSource = SearchCharactersSource(rickAndMortyApi = rickAndMortyApi, query = "Rick Sanchez", rickAndMortyDatabase)
            val loadResult = charactersSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = rickAndMortyApi.searchCharacters("Unknown").results

            assertTrue { result.isEmpty() }
        }
}





