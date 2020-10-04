package com.zea7ot.whorepresentsyou.data.remote.googleApi

import com.zea7ot.whorepresentsyou.data.remote.BaseDataSource
import javax.inject.Inject

class GoogleApiRemoteDataSource @Inject constructor(
    private val googleApiService: GoogleApiService
) : BaseDataSource() {

    suspend fun getAddresses(latitude: Double, longitude: Double) =
        getResult { googleApiService.getAddress(latLng = "$latitude,$longitude") }
}