package com.example.githubuserproject.data

import com.example.githubuserproject.data.remote.CharacterRemoteDataSource
import com.example.githubuserproject.utils.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
) {
    fun getCharacters() = performGetOperation(
        networkCall = { remoteDataSource.getCharacters() }
    )
}