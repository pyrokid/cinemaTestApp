package com.melkov.cinema.screens.mainscreen.filmlistfragment

import com.melkov.cinema.data.t.model.Films
import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface FilmListView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showError()
    @StateStrategyType(SkipStrategy::class)
    fun setupUI(films: List<Films>)
    @StateStrategyType(SkipStrategy::class)
    fun setupGenres(genres: MutableSet<String>)
}