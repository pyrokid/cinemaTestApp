package com.melkov.cinema.di

import com.melkov.cinema.screens.mainscreen.filminfofragment.FilmInfoFragment
import com.melkov.cinema.screens.mainscreen.filmlistfragment.FilmListFragment
import com.melkov.cinema.screens.mainscreen.mainactivity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: FilmListFragment)
    fun inject(fragment: FilmInfoFragment)
}