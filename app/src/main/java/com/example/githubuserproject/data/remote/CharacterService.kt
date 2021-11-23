package com.example.githubuserproject.data.remote

import com.example.githubuserproject.data.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterService {
    @GET("users")
    suspend fun getAllCharacters() : Response<List<UserResponse>>
}