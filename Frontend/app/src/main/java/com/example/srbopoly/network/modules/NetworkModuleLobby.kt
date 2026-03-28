package com.example.srbopoly.network.modules

import com.example.srbopoly.network.NetworkConfig
import com.example.srbopoly.network.apiServices.lobbyService.ApiServiceLobby
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleLobby {
    @Provides
    @Singleton
    @Named("LobbyRetrofit")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        networkConfig: NetworkConfig
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfig.baseUrlLobbyServer)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiServiceLobby(
        @Named("LobbyRetrofit") retrofit: Retrofit): ApiServiceLobby {
        return retrofit.create(ApiServiceLobby::class.java)
    }
}