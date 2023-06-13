package dev.toke.ameplus.repositories

import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.AuthResult
import dev.toke.ameplus.models.TokenResponse

interface AuthRepository {
    suspend fun refreshToken(accessToken: String, refreshToken: String): AuthResult<Unit>
    suspend fun signIn(username: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}