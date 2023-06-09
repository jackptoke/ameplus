package dev.toke.ameplus.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.toke.ameplus.network.AMEPartsApi
import dev.toke.ameplus.network.AppSharedPreferences
import dev.toke.ameplus.network.AuthApi
import dev.toke.ameplus.network.AuthInterceptor
import dev.toke.ameplus.network.SessionManager
import dev.toke.ameplus.repositories.AuthRepository
import dev.toke.ameplus.repositories.AuthRepositoryImpl
import dev.toke.ameplus.utils.Constants
import okhttp3.OkHttpClient
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
    fun provideAuthRepository(authApi: AuthApi, sessionManager: SessionManager): AuthRepository = AuthRepositoryImpl(authApi, sessionManager)

    @Singleton
    @Provides
    fun provideSessionManager(
        appSharedPreferences: AppSharedPreferences,
        authRepository: AuthRepository
    ): SessionManager = SessionManager(
        pref = appSharedPreferences,
        authRepository = authRepository
    )

    @Singleton
    @Provides
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor = AuthInterceptor(sessionManager = sessionManager)

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_BASE_URL_DEV)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideAMEPartsApi(retrofit: Retrofit): AMEPartsApi {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

}