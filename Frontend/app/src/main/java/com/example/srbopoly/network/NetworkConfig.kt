package com.example.srbopoly.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConfig @Inject constructor() {
    val baseUrl: String = "https://perzistencija-production.up.railway.app/"
    val baseUrlLobbyServer: String = "https://perzistencija-production-6918.up.railway.app/"
}