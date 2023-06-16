package dev.toke.ameplus.repositories

import dev.toke.ameplus.data.AuthResult
import dev.toke.ameplus.models.TokenResponse

interface AuthRepository {
    suspend fun refreshToken(accessToken: String, refreshToken: String): AuthResult<TokenResponse?>
    suspend fun signIn(username: String): AuthResult<TokenResponse?>
    suspend fun signOut(): AuthResult<TokenResponse?>
    suspend fun authenticate(): AuthResult<TokenResponse?>
}