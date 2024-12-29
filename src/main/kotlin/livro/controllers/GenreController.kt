package livro.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import livro.models.Genre

class GenreController {
    private val genres = mutableListOf<Genre>()

    suspend fun getAllGenres(call: ApplicationCall) {
        val jsonResponse = Json.encodeToString(genres)
        call.respondText(jsonResponse, contentType = io.ktor.http.ContentType.Application.Json)
    }

    suspend fun addGenre(call: ApplicationCall) {
        val jsonBody = call.receiveText()
        val genre = Json.decodeFromString<Genre>(jsonBody)
        genres.add(genre)
        call.respondText("Genre added successfully", status = io.ktor.http.HttpStatusCode.Created)
    }
}