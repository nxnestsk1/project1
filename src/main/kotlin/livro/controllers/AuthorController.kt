package livro.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import livro.models.Author

class AuthorController {
    private val authors = mutableListOf<Author>()

    suspend fun addAuthor(call: ApplicationCall) {
        val jsonBody = call.receiveText()
        val author = Json.decodeFromString<Author>(jsonBody)
        authors.add(author)
        call.respondText("Author added successfully", status = io.ktor.http.HttpStatusCode.Created)
    }

    suspend fun getAuthors(call: ApplicationCall) {
        call.respond(authors)
    }
}