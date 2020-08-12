package com.melkov.cinema.data.t.network

import com.melkov.cinema.data.t.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface FilmApi {
    @GET("films.json")
    fun getFilmList(): Call<Response>
}