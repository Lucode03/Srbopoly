package com.example.srbopoly.data.repository

import com.example.srbopoly.data.User
import com.example.srbopoly.network.apiServices.persistanceService.ApiServiceUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRangListRepository @Inject constructor(
    private val usersRangListApi: ApiServiceUser,
) {
    private var currentOffset = 0
    private val pageSize = 5
    var hasMore = true
        private set


    suspend fun getNextUsers(): List<User> {
        if (!hasMore)
            return emptyList()

        val response = usersRangListApi.getUsersPaged(
            start = currentOffset,
            count = pageSize
        )

        if (response.isSuccessful) {
            val users = response.body() ?: emptyList()

            if (users.size < pageSize) {
                hasMore = false
            }

            currentOffset += users.size
            return users
        }

        throw Exception("Failed to load users")
    }

    fun resetPaging() {
        currentOffset = 0
    }
}