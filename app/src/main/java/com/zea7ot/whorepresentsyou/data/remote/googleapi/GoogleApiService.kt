package com.zea7ot.whorepresentsyou.data.remote.googleapi

import com.zea7ot.whorepresentsyou.MainApplication
import com.zea7ot.whorepresentsyou.R
import com.zea7ot.whorepresentsyou.data.entity.ResAddress
import com.zea7ot.whorepresentsyou.util.ApiQuery
import com.zea7ot.whorepresentsyou.util.ApiUrl
import com.zea7ot.whorepresentsyou.util.DefaultValue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GoogleApiService {
    @GET
    suspend fun getAddress(
        @Url baseUrl: String = "${ApiUrl.GoogleApi.BASE_URL_MAP_GEOCODE}/${ApiUrl.GoogleApi.GET_ADDRESSES}",
        @Query(ApiQuery.LAT_LNG) latLng: String,
        @Query(ApiQuery.RESULT_TYPE) resultType: String = DefaultValue.ApiQuery.RESULT_TYPE_POSTAL_CODE,
        @Query(ApiQuery.API_KEY) key: String = MainApplication.applicationContext().resources.getString(
            R.string.google_api_key
        )
    ): Response<ResAddress>
}