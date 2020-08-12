package com.melkov.cinema.screens.mainscreen.filminfofragment

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.melkov.cinema.R
import com.melkov.cinema.screens.mainscreen.filmlistfragment.FilmListFragment
import com.melkov.cinema.screens.mainscreen.filmlistfragment.ORIG_NAME
import com.melkov.cinema.screens.mainscreen.filmlistfragment.RATING
import com.melkov.cinema.screens.mainscreen.mainactivity.MainActivity
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment

class FilmInfoFragment : MvpAppCompatFragment(), FilmInfoView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = this.arguments
        val v = inflater.inflate(R.layout.fragment_film_info, container, false)

        val filmImage = v.findViewById<ImageView>(R.id.film_image)

        (activity as MainActivity).toolbarBackpressButton.visibility = View.VISIBLE
        (activity as MainActivity).toolbarBackpressButton.setOnClickListener {
            closeFragment()
        }

        args?.let {
            (activity as MainActivity).toolbarHeader.text = it.getString("LOCALIZED_NAME")
            v.findViewById<TextView>(R.id.film_title).text = it.getString(ORIG_NAME)
            v.findViewById<TextView>(R.id.film_year).text = "Год: " + it.getInt("YEAR").toString()
            v.findViewById<TextView>(R.id.film_rating).text = setRatingColor(it.getDouble(RATING))
            v.findViewById<TextView>(R.id.film_description).text = it.getString("DESCRIPTION")
            Picasso.get().load(it.getString("IMAGE")).centerCrop().resize(500, 500)
                .error(R.drawable.ic_no_film_image).into(filmImage)
        }

        return v
    }

    private fun setRatingColor(rating: Double): SpannableString {
        val spannable = SpannableString("Рейтинг: " + rating.toString())

        spannable.setSpan(
            ForegroundColorSpan(getRatingColor(rating)),
            9,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            ForegroundColorSpan(context!!.resources.getColor(getRatingColor(rating))),
            9,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    private fun getRatingColor(rating: Double): Int {
        when (rating) {
            in 0.1..4.0 -> return R.color.ratingRed
            in 4.0..7.0 -> return R.color.ratingYellow
            in 7.0..10.0 -> return R.color.ratingGreen
            else -> return R.color.colorWhite
        }
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_storage, FilmListFragment())?.addToBackStack(null)?.commit()
    }
}