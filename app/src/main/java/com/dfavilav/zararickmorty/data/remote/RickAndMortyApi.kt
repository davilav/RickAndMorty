package com.dfavilav.zararickmorty.data.remote

import com.dfavilav.zararickmorty.domain.model.ApiResponse
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("/api/character")
    fun getAllCharacters(): ApiResponse

    @GET("/api/character")
    fun searchCharacters(): ApiResponse
}