package dev.toke.ameplus.network

import dev.toke.ameplus.repositories.AuthRepository
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val pref: AppSharedPreferences,
    private val authRepository: AuthRepository
) {
    fun getAccessToken(): String? = pref.getAccessToken()

    fun getRefreshToken(): String? = pref.getRefreshToken()

    suspend fun refreshToken(refreshToken: String): String = authRepository.refreshToken(refreshToken)

    fun logout() {
        /* .... */
        pref.setAccessToken("")
    }
}