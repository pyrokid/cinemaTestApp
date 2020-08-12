package com.melkov.cinema.data.t.repository

import com.melkov.cinema.data.t.model.Response
import com.melkov.cinema.data.t.network.FilmApi
import retrofit2.Call

class FilmRepository(private val filmApi: FilmApi) {

    fun getFilmList(): Call<Response> {
        return filmApi.getFilmList()
    }

}