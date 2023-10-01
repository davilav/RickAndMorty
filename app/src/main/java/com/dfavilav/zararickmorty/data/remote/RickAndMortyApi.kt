package com.dfavilav.zararickmorty.data.remote

import com.dfavilav.zararickmorty.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("/api/character")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/api/character")
    suspend fun searchCharacters(
        @Query("name") name: String
    ): ApiResponse
}