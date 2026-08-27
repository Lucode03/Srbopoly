package com.example.srbopoly.network

import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.data.SessionExpiredNotifier
import com.example.srbopoly.data.SessionManager
import com.example.srbopoly.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthExpiryInterceptor @Inject constructor(
    private val tokenProvider: AuthTokenProvider,
    private val sessionManager: SessionManager,
    private val notifier: SessionExpiredNotifier,
    @ApplicationScope private val appScope: CoroutineScope
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 401 && tokenProvider.token != null) {
            tokenProvider.clear()
            appScope.launch { sessionManager.clear() }
            notifier.notifySessionExpired()
        }

        return response
    }
}