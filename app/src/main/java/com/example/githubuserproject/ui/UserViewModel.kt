package com.example.githubuserproject.ui

import androidx.lifecycle.ViewModel
import com.example.githubuserproject.data.UserRepository
import com.example.githubuserproject.data.response.UserResponse
import com.example.githubuserproject.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUsers(sinceId: Int, perPage: Int): Flow<ApiResponse<List<UserResponse>?>> {
        return userRepository.getUsers(sinceId, perPage)
    }
//    val users = userRepository.getUsers(10, 100)
}
