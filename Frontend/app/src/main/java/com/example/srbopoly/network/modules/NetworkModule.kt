package com.example.srbopoly.network.modules

import com.example.srbopoly.network.NetworkConfig
import com.example.srbopoly.network.apiServices.persistanceService.ApiService
import com.example.srbopoly.network.apiServices.persistanceService.ApiServiceAuth
import com.example.srbopoly.network.apiServices.persistanceService.ApiServiceGame
import com.example.srbopoly.network.apiServices.persistanceService.ApiServiceUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("GameRetrofit")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        networkConfig: NetworkConfig): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        @Named("GameRetrofit")retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceAuth(
        @Named("GameRetrofit")retrofit: Retrofit): ApiServiceAuth {
        return retrofit.create(ApiServiceAuth::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceGame(
        @Named("GameRetrofit")retrofit: Retrofit): ApiServiceGame {
        return retrofit.create(ApiServiceGame::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServiceUser(
        @Named("GameRetrofit")retrofit: Retrofit): ApiServiceUser {
        return retrofit.create(ApiServiceUser::class.java)
    }
}