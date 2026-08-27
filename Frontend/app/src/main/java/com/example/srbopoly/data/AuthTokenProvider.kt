package com.example.srbopoly.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenProvider @Inject constructor() {
    @Volatile
    var token: String? = null

    fun clear() {
        token = null
    }
}