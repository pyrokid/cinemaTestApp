package com.melkov.cinema.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.melkov.cinema.data.t.network.FilmApi
import com.melkov.cinema.data.t.repository.FilmRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideFilmRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideFilmApi(retrofit: Retrofit): FilmApi =
        retrofit.create(FilmApi::class.java)

    @Provides
    @Singleton
    fun provideFilmRepository(filmApi: FilmApi): FilmRepository = FilmRepository(filmApi)

}