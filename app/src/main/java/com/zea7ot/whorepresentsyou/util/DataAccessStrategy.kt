package com.zea7ot.whorepresentsyou.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.zea7ot.whorepresentsyou.util.Resource.Status.*
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

private const val TAG = "DataAccessStrategy"

fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            Timber.d("successful")
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }