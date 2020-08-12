package com.melkov.cinema.screens.mainscreen.mainactivity

import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.melkov.cinema.CinemaApp
import com.melkov.cinema.R
import com.melkov.cinema.data.t.repository.FilmRepository
import com.melkov.cinema.screens.mainscreen.filmlistfragment.FilmListFragment
import com.melkov.cinema.screens.mainscreen.filmlistfragment.FilmListPresenter
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

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