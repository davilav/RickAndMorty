package com.dfavilav.zararickmorty.data.remote

import com.dfavilav.zararickmorty.domain.model.ApiResponse
import com.dfavilav.zararickmorty.domain.model.Character

class FakeRickAndMortyApi : RickAndMortyApi {

    private val characters = listOf(
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

    override suspend fun getAllCharacters(page: Int): ApiResponse {
        return ApiResponse()
    }

    override suspend fun searchCharacters(name: String): ApiResponse {
        val searchedHeroes = findHeroes(name = name)
        return ApiResponse()
    }

    private fun findHeroes(name: String): List<Character> {
        val founded = mutableListOf<Character>()
        return if (name.isNotEmpty()) {
            characters.forEach { hero ->
                if (hero.name.lowercase().contains(name.lowercase())) {
                    founded.add(hero)
                }
            }
            founded
        } else {
            emptyList()
        }
    }
}
