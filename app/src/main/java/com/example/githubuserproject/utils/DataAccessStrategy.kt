package com.example.githubuserproject.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuserproject.utils.Resource.Status.ERROR
import kotlinx.coroutines.Dispatchers

fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }