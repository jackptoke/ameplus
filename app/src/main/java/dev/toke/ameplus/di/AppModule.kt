package dev.toke.ameplus.di

import android.content.Context
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.toke.ameplus.network.AMEPartsApi
import dev.toke.ameplus.network.AmePlusApi
import dev.toke.ameplus.network.ProductApi
import dev.toke.ameplus.network.SortingApi
import dev.toke.ameplus.repositories.AuthRepository
import dev.toke.ameplus.repositories.AuthRepositoryImpl
import dev.toke.ameplus.repositories.DataStoreRepository
import dev.toke.ameplus.repositories.PartsRepository
import dev.toke.ameplus.repositories.SortingRepository
import dev.toke.ameplus.repositories.SortingRepositoryImpl
import dev.toke.ameplus.services.ExoPlayerService
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
    fun provideRetrofit(): Retrofit {
        Log.d("AppModule", "AuthApi provided")
        return Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAmePlusApi(retrofit: Retrofit): AmePlusApi {
        return retrofit.create(AmePlusApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAMEPartsApi(retrofit: Retrofit): AMEPartsApi {
        Log.d("AppModule", "provideAMEPartsApi - kicked in")
        return retrofit.create(AMEPartsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSortingApi(retrofit: Retrofit): SortingApi {
        return retrofit.create(SortingApi::class.java)
    }


    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository = DataStoreRepository(context)

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AmePlusApi, dataStoreRepo: DataStoreRepository): AuthRepository = AuthRepositoryImpl(authApi = authApi, dataStoreRepository = dataStoreRepo)

    @Provides
    @Singleton
    fun providePartsRepository(
        partApi: AMEPartsApi,
        authRepo: AuthRepositoryImpl): PartsRepository
    = PartsRepository(partApi = partApi, authRepo = authRepo)

    @Provides
    @Singleton
    fun provideExoPlayerService(exoPlayer: ExoPlayer): ExoPlayerService = ExoPlayerService(exoPlayer)

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer = ExoPlayer.Builder(context).build()


    @Provides
    @Singleton
    fun provideSortingRepository(sortingApi: SortingApi, authRepo: AuthRepositoryImpl): SortingRepository = SortingRepositoryImpl(sortingApi = sortingApi, authRepo = authRepo)
}