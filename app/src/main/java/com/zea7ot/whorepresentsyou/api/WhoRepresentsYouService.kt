package com.zea7ot.whorepresentsyou.api

import com.zea7ot.whorepresentsyou.BuildConfig
import com.zea7ot.whorepresentsyou.model.ResMembers
import com.zea7ot.whorepresentsyou.util.Constant
import com.zea7ot.whorepresentsyou.util.ApiUrl
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WhoRepresentsYouService {

    @GET(ApiUrl.GET_MEMBERS)
    fun getAllMembers(
        @Query("zip") zip: String,
        @Query("output") output: String = "json"
    ): Observable<ResMembers>

    companion object {
        fun create(): WhoRepresentsYouService {

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .build()

            return retrofit.create(WhoRepresentsYouService::class.java)
        }
    }
}