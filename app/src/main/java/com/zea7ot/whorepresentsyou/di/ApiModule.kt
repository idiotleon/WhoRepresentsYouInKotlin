package com.zea7ot.whorepresentsyou.di

import com.zea7ot.whorepresentsyou.BuildConfig
import com.zea7ot.whorepresentsyou.data.remote.googleApi.GoogleApiService
import com.zea7ot.whorepresentsyou.data.remote.partyMember.PartyMemberService
import com.zea7ot.whorepresentsyou.util.ApiUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiUrl.Member.BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    fun provideWhoRepresentsYouService(retrofit: Retrofit): PartyMemberService =
        retrofit.create(PartyMemberService::class.java)

    @Provides
    fun provideGoogleApiService(retrofit: Retrofit): GoogleApiService =
        retrofit.create(GoogleApiService::class.java)
}