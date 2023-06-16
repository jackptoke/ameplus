package dev.toke.ameplus.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.toke.ameplus.models.TokenResponse
import dev.toke.ameplus.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.IOException

class DataStoreRepository(context: Context) {
    companion object PreferenceKeys {
        val accessToken = stringPreferencesKey(Constants.ACCESS_TOKEN_NAME)
        val username = stringPreferencesKey(Constants.TOKEN_USERNAME)
        val expiration = stringPreferencesKey(Constants.TOKEN_EXPIRATION)
        val message = stringPreferencesKey(Constants.TOKEN_MESSAGE)
        val name = stringPreferencesKey(Constants.TOKEN_NAME)
        val refreshToken = stringPreferencesKey(Constants.TOKEN_REFRESH_TOKEN)
        val statusCode = intPreferencesKey(Constants.TOKEN_STATUS_CODE)
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCE_NAME)
    val pref = context.dataStore

    private val _accessToken = MutableLiveData<TokenResponse?>(null)
    val validAccessToken: LiveData<TokenResponse?>
        get() = _accessToken

    suspend fun saveTokenToDataStore(token: TokenResponse) {
        Log.d("DataStoreRepository", "saveTokenToDataStore - before")
        val tokenString = Json.encodeToString(token)
        _accessToken.value = token
        pref.edit {
            it[accessToken] = tokenString
        }
        Log.d("DataStoreRepository", "saveTokenToDataStore - after")
    }

    suspend fun getTokenFromDataStore() = pref.data
        .catch { exception ->
            if(exception is IOException) {
                exception.localizedMessage?.let { Log.d("DataStoreRepository", it.toString()) }
                emit(emptyPreferences())
            } else {
                exception.localizedMessage?.let { Log.d("DataStoreRepository", it.toString()) }
                throw exception
            }
        }
        .map {preference ->
        Log.d("DataStoreRepository", "getTokenFromDataStore ${ preference[accessToken] }")
        val result = if(preference[accessToken].isNullOrEmpty()) null else Json.decodeFromString<TokenResponse>(preference[accessToken]!!)
        _accessToken.value = result
        result
    }

    suspend fun deleteTokenFromDataStore() {
        Log.d("DataStoreRepository", "deleteTokenFromDataStore - before")
        pref.edit {
            it.remove(accessToken)
        }
        Log.d("DataStoreRepository", "deleteTokenFromDataStore - after")
    }

}