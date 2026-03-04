package com.example.srbopoly.network

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConfig @Inject constructor() {
    val baseUrl: String = "https://backedn-production-5c09.up.railway.app/"
}