package com.example.githubuserproject.data

import com.example.githubuserproject.data.remote.UserApiService
import com.example.githubuserproject.utils.result
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService
) {
    fun getUsers(sinceId: Int, perPage: Int) = result {
        userApiService.getAllUsers(sinceId, perPage)
    }
}