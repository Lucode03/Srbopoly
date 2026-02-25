package com.example.srbopoly.data.repository
import com.example.srbopoly.data.User
import com.example.srbopoly.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun checkUserExists(username: String): Result<User> {
        return try {
            val response = api.getUserByUsername(username)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Korisnik ne postoji"))
                }
            } else {
                if (response.code() == 404)
                    Result.failure(Exception("Ne postoji korisnik sa datim korisničkim imenom"))
                else
                    Result.failure(Exception("Greška na serveru: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}