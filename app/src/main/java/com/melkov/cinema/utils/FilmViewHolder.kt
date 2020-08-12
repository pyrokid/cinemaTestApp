package com.melkov.cinema.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melkov.cinema.R
import com.melkov.cinema.data.t.model.Films
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_film_preview.view.*

class FilmAdapter(private val films: List<Films>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(film: Films) {
            itemView.film_name.text = film.localized_name
            Picasso.get().load(film.image_url).centerCrop().resize(300, 300)
                .error(R.drawable.ic_no_film_image).into(itemView.film_image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film_preview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        films.getOrNull(position)?.let { film ->
            holder.bind(film)
        }

        holder.itemView.setOnClickListener { v ->
            listener.onItemClickListener(v, holder.layoutPosition)
        }
    }

    override fun getItemCount(): Int = films.size

    interface OnItemClickListener {
        fun onItemClickListener(v: View, pos: Int)
    }

}