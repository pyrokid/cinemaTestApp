package com.melkov.cinema.screens.mainscreen.mainactivity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.melkov.cinema.CinemaApp
import com.melkov.cinema.R
import com.melkov.cinema.screens.mainscreen.filmlistfragment.FilmListFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    //region UI
    lateinit var toolbarHeader: TextView
    lateinit var toolbarBackpressButton: ImageView
    //endregion

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        CinemaApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarHeader = findViewById(R.id.toolbar_header)
        toolbarBackpressButton = findViewById(R.id.toolbar_backpress_button)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_storage, FilmListFragment())
            .commit()
    }
}