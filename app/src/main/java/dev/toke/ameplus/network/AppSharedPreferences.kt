package dev.toke.ameplus.network

import android.content.SharedPreferences
import android.media.session.MediaSession.Token
import android.util.Log
import com.google.gson.Gson
import dev.toke.ameplus.models.TokenResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    init {
        Log.d("AppSharedPreferences", "Constructor")
    }

    fun getAccessToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)

    fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
    }

    fun getUserData(): TokenResponse? {
        val data: String? = sharedPreferences.getString(USER_DATA, null)
        var userData: TokenResponse? = null
        if(data != null)
            userData = Json.decodeFromString<TokenResponse>(data)
        return userData
    }

    fun setUserData(userData: TokenResponse) {
        val dataString: String = Json.encodeToString(userData)
        sharedPreferences.edit().putString(USER_DATA, dataString).apply()
    }

    fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)

    fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    companion object {
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
        const val USER_DATA = "user_data"
    }
}