package com.example.githubuserproject.data.remote

import com.example.githubuserproject.base.BaseDataSource
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
): BaseDataSource() {

    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
}