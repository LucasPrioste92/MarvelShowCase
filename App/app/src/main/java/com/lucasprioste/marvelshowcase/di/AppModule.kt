package com.lucasprioste.marvelshowcase.di

import com.lucasprioste.marvelshowcase.core.MarvelApiInterceptor
import com.lucasprioste.marvelshowcase.data.remote.MarvelApi
import com.lucasprioste.marvelshowcase.data.repository.MarvelRepositoryIMP
import com.lucasprioste.marvelshowcase.data.repository.SessionRepositoryIMP
import com.lucasprioste.marvelshowcase.domain.repository.MarvelRepository
import com.lucasprioste.marvelshowcase.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMarvelApi(): MarvelApi{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(MarvelApiInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(MarvelApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesMarvelRepository(api: MarvelApi): MarvelRepository{
        return MarvelRepositoryIMP(api = api)
    }

    @Provides
    @Singleton
    fun providesSessionRepository(): SessionRepository{
        return SessionRepositoryIMP()
    }
}