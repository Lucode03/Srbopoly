package com.example.srbopoly.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConfig @Inject constructor() {
    val baseUrl: String = "https://program-headdress-spree.ngrok-free.dev/"
    val baseUrlLobbyServer: String = "https://program-headdress-spree.ngrok-free.dev/"

    val baseUrlGameServer: String = "https://program-headdress-spree.ngrok-free.dev/"
}