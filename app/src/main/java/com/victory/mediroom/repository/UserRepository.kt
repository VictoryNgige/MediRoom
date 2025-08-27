package com.victory.mediroom.repository

import com.victory.mediroom.data.UserDao
import com.victory.mediroom.model.User


class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) {
        userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.loginUser(email, password)
    }
}