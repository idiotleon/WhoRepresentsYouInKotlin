package com.zea7ot.whorepresentsyou.data.remote

import com.zea7ot.whorepresentsyou.data.entity.ResMembers
import com.zea7ot.whorepresentsyou.util.ApiQuery
import com.zea7ot.whorepresentsyou.util.ApiUrl
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberService {
    @GET(ApiUrl.GET_MEMBERS)
    suspend fun getAllMembers(
        @Query(ApiQuery.ZIP) zip: String,
        @Query(ApiQuery.OUTPUT) output: String = "json"
    ): Response<ResMembers>

//    companion object {
//        fun create(): WhoRepresentsYouService {
//
//            val interceptor = HttpLoggingInterceptor()
//            if (BuildConfig.DEBUG) {
//                interceptor.level = HttpLoggingInterceptor.Level.BODY
//            }
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//            val retrofit = Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(Constant.BASE_URL)
//                .client(client)
//                .build()
//
//            return retrofit.create(WhoRepresentsYouService::class.java)
//        }
//    }
}