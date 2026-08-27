package com.example.srbopoly.network.modules

import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.network.AuthExpiryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClient {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenProvider: AuthTokenProvider,
        authExpiryInterceptor: AuthExpiryInterceptor
    ): okhttp3.OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = okhttp3.Interceptor { chain ->
            val original = chain.request()
            val token = tokenProvider.token
            val request = if (token != null) {
                original.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                original
            }
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(authExpiryInterceptor)
            .addInterceptor(logging)
            .build()
    }
}