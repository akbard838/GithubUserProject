package com.example.githubuserproject.data.remote

import com.example.githubuserproject.data.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("users")
    suspend fun getAllUsers(
        @Query("since") sinceId: Int,
        @Query("per_page") perPage: Int
    ) : Response<List<UserResponse>>
}