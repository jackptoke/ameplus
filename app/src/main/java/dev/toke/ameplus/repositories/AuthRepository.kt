package dev.toke.ameplus.repositories

import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.AuthResponse

interface AuthRepository {
    suspend fun refreshToken(refreshToken: String): String
    suspend fun signIn(username: String): DataOrException<AuthResponse, Boolean, Exception>
}