package com.example.srbopoly.data.dto

data class CreateUserRequest(
    val username: String,
    val password: String
)

data class LoginUserRequest(
    val username: String,
    val password: String
)