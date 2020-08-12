package com.melkov.cinema.screens.mainscreen.filmlistfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.melkov.cinema.CinemaApp
import com.melkov.cinema.R
import com.melkov.cinema.data.t.model.Films
import com.melkov.cinema.data.t.repository.FilmRepository
import com.melkov.cinema.screens.mainscreen.filminfofragment.FilmInfoFragment
import com.melkov.cinema.screens.mainscreen.mainactivity.MainActivity
import com.melkov.cinema.utils.FilmAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

const val LOCALIZED_NAME = "LOCALIZED_NAME"
const val IMAGE = "IMAGE"
const val ORIG_NAME = "ORIG_NAME"
const val YEAR = "YEAR"
const val RATING = "RATING"
const val DESCRIPTION = "DESCRIPTION"

class FilmListFragment : MvpAppCompatFragment(), FilmListView {

    //region Injects
    @Inject
    lateinit var filmRepository: FilmRepository

    @InjectPresenter
    lateinit var presenter: FilmListPresenter

    @ProvidePresenter
    fun provideFilmListPresenter(): FilmListPresenter {
        return FilmListPresenter(filmRepository = filmRepository)
    }
    //endregion

    private lateinit var progressBar: ProgressBar
    private lateinit var retryButton: Button
    private lateinit var retryAlert: TextView
    private lateinit var genresChoice: GridView
    private lateinit var filmsContainer: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        CinemaApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_film_list, container, false)
        progressBar = v.findViewById(R.id.progress_bar)
        retryButton = v.findViewById(R.id.retry_button)
        retryAlert = v.findViewById(R.id.retry_alert)
        genresChoice = v.findViewById(R.id.genres_choice)
        filmsContainer = v.findViewById(R.id.films_container)

        (activity as MainActivity).toolbarBackpressButton.visibility = View.INVISIBLE

        setOnClickListeners()

        presenter.loadFilms()

        val appToolbarHeader = activity?.findViewById<TextView>(R.id.toolbar_header)
        appToolbarHeader?.text = getString(R.string.string_films)

        return v
    }

    override fun setupGenres(genres: MutableSet<String>) {
        val data = genres.toMutableList()
        context?.let { ArrayAdapter<String>(it, R.layout.item_genre_choice_button, data) }
            ?.let {
                genresChoice.adapter = it
                genresChoice.setOnItemClickListener { adapterView, view, i, l ->
                    presenter.loadFilmsByGenre(data[i])
                }
            }
    }

    private fun setOnClickListeners() {
        retryButton.setOnClickListener {
            presenter.loadFilms()
            progressBar.visibility = View.VISIBLE
            retryButton.visibility = View.GONE
            retryAlert.visibility = View.GONE
        }
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        retryAlert.visibility = View.VISIBLE
        retryButton.visibility = View.VISIBLE
    }

    override fun setupUI(films: List<Films>) {
        progressBar.visibility = View.GONE

        filmsContainer.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = FilmAdapter(films, object : FilmAdapter.OnItemClickListener {
                override fun onItemClickListener(v: View, pos: Int) {
                    openFilmInfoFragment(films[pos])
                }
            })
        }
    }

    fun openFilmInfoFragment(film: Films) {
        val fragment = FilmInfoFragment()
        val args = Bundle()
        args.putString(LOCALIZED_NAME, film.localized_name)
        args.putString(IMAGE, film.image_url)
        args.putString(ORIG_NAME, film.name)
        args.putInt(YEAR, film.year)
        film.rating?.let { args.putDouble(RATING, it) }
        args.putString(DESCRIPTION, film.description)

        fragment.arguments = args

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.fragment_storage, fragment
            )?.addToBackStack(null)?.commit()
    }
}