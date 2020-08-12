package com.melkov.cinema.screens.mainscreen.filmlistfragment

import com.melkov.cinema.data.t.model.Films
import com.melkov.cinema.data.t.model.Response
import com.melkov.cinema.data.t.repository.FilmRepository
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback

@InjectViewState
class FilmListPresenter() : MvpPresenter<FilmListView>() {

    lateinit var filmRepository: FilmRepository
    var genres: MutableSet<String> = mutableSetOf()
    var films: List<Films>? = mutableListOf()

    constructor(filmRepository: FilmRepository) : this() {
        this.filmRepository = filmRepository
    }

    fun loadFilms() {
        filmRepository.getFilmList().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                viewState.showError()
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                films = response.body()?.films?.sortedBy {
                    it.localized_name.toLowerCase()
                }

                for (i in films!!) {
                    genres.addAll(i.genres)
                }

                viewState.setupUI(films!!)
                viewState.setupGenres(genres)
            }
        })
    }

    fun loadFilmsByGenre(genre: String) {
        films?.filter {
            it.genres.contains(genre)
        }?.let {
            viewState.setupUI(it)
        }
    }

}