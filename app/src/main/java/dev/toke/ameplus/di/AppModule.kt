package dev.toke.ameplus.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.toke.ameplus.network.AppSharedPreferences
import dev.toke.ameplus.network.AuthApi
import dev.toke.ameplus.repositories.AuthRepository
import dev.toke.ameplus.repositories.AuthRepositoryImpl
import dev.toke.ameplus.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context
        .getSharedPreferences(AppSharedPreferences.SHARED_PREFS, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppSharedPreferences(sharedPreferences: SharedPreferences) = AppSharedPreferences(sharedPreferences)

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        Log.d("AppModule", "AuthApi provided")
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, prefs: AppSharedPreferences): AuthRepository {
        Log.d("AppModule", "AuthRepository provided")
        return AuthRepositoryImpl(authApi = api, prefs = prefs)
    }

}