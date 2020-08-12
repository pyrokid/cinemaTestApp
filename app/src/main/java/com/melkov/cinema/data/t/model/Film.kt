package com.melkov.cinema.data.t.model

data class Response(
    val films: List<Films>
)

data class Films(
    val id: Int,
    val localized_name: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    val image_url: String?,
    val description: String,
    val genres: List<String>
)