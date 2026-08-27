package com.example.srbopoly.data.repository
import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.data.SessionManager
import com.example.srbopoly.data.User
import com.example.srbopoly.data.dto.CreateUserRequest
import com.example.srbopoly.data.dto.LoginUserRequest
import com.example.srbopoly.network.apiServices.persistanceService.ApiServiceAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ApiServiceAuth,
    private val tokenProvider: AuthTokenProvider,
    private val sessionManager: SessionManager
) {
    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val response = api.login(LoginUserRequest(username, password))

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        tokenProvider.token = body.token
                        sessionManager.saveUser(body.userId, body.username, body.points, body.token)
                        Result.success(User(body.userId, body.username, body.points))
                    } else {
                        Result.failure(Exception("Korisnik ne postoji"))
                    }
                }

                response.code() == 401 -> {
                    Result.failure(Exception("Pogrešan username ili password"))
                }

                response.code() == 400 -> {
                    Result.failure(Exception("Neispravan zahtev"))
                }

                else -> {
                    Result.failure(Exception("Greška na serveru: ${response.code()}"))
                }
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, password: String): Result<User> {
        return try {
            val response = api.createUser(CreateUserRequest(username, password))

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null) {
                        tokenProvider.token = body.token
                        sessionManager.saveUser(body.userId, body.username, body.points, body.token)
                        Result.success(User(body.userId, body.username, body.points))
                    } else {
                        Result.failure(Exception("Korisnik nije kreiran"))
                    }
                }

                response.code() == 400 -> {
                    Result.failure(Exception("Username već postoji ili neispravni podaci"))
                }

                else -> {
                    Result.failure(Exception("Greška na serveru: ${response.code()}"))
                }
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}