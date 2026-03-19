package com.example.srbopoly.data.repository
import com.example.srbopoly.data.User
import com.example.srbopoly.data.dto.CreateUserRequest
import com.example.srbopoly.data.dto.LoginUserRequest
import com.example.srbopoly.network.ApiService
import com.example.srbopoly.network.apiServices.ApiServiceAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ApiServiceAuth
) {
    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val response = api.login(LoginUserRequest(username, password))

            when {
                response.isSuccessful -> {
                    val user = response.body()
                    if (user != null) {
                        Result.success(user)
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
                    val user = response.body()
                    if (user != null) {
                        Result.success(user)
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