package com.example.srbopoly.network
import com.example.srbopoly.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("User/GetByUsername/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<User>

    @POST("User/CreateUser")
    suspend fun createUser(@Body username: String): Response<User>

    @GET("User/GetById/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    @PUT("User/AddPoints/{id}/{points}")
    suspend fun addPoints(
        @Path("id") id: Int,
        @Path("points") points: Int
    ): Response<User>

    @DELETE("User/DeleteById/{id}")
    suspend fun deleteUserById(@Path("id") id: Int): Response<String>

    @GET("User/GetAllUsers")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("User/GetUsers")
    suspend fun getTopUsers(@Query("top") top: Int): Response<List<User>>
}
