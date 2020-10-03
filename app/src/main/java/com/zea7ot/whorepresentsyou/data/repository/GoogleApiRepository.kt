package com.zea7ot.whorepresentsyou.data.repository

import com.zea7ot.whorepresentsyou.data.remote.googleapi.GoogleApiRemoteDataSource
import javax.inject.Inject

class GoogleApiRepository @Inject constructor(
    private val remoteDataSource: GoogleApiRemoteDataSource
) {
    suspend fun getAddresses(latitude: Double, longitude: Double) =
        remoteDataSource.getAddresses(latitude, longitude)
}