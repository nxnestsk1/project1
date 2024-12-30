package livro.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class Author(val id: Int, val name: String, val bio: String)

class AuthorController {
    private val authors = mutableListOf<Author>()

    suspend fun addAuthor(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val id = parameters["id"]?.toIntOrNull()
        val name = parameters["name"] ?: ""
        val bio = parameters["bio"] ?: ""

        if (id == null || name.isBlank() || bio.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "All fields are required!")
            return
        }

        if (authors.any { it.id == id }) {
            call.respond(HttpStatusCode.Conflict, "An author with ID $id already exists!")
            return
        }

        authors.add(Author(id, name, bio))
        call.respond(HttpStatusCode.Created, "Author added successfully!")
    }

    suspend fun getAuthors(call: ApplicationCall) {
        if (authors.isEmpty()) {
            call.respond(HttpStatusCode.NoContent, "No authors found.")
        } else {
            call.respond(HttpStatusCode.OK, authors)
        }
    }

    suspend fun getAuthorById(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid author ID.")
            return
        }

        val author = authors.find { it.id == id }
        if (author == null) {
            call.respond(HttpStatusCode.NotFound, "Author not found.")
        } else {
            call.respond(HttpStatusCode.OK, author)
        }
    }

    suspend fun updateAuthor(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid author ID.")
            return
        }

        val parameters = call.receiveParameters()
        val name = parameters["name"] ?: ""
        val bio = parameters["bio"] ?: ""

        val authorIndex = authors.indexOfFirst { it.id == id }
        if (authorIndex == -1) {
            call.respond(HttpStatusCode.NotFound, "Author not found.")
            return
        }

        authors[authorIndex] = Author(id, name, bio)
        call.respond(HttpStatusCode.OK, "Author updated successfully!")
    }

    suspend fun deleteAuthor(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid author ID.")
            return
        }

        val authorIndex = authors.indexOfFirst { it.id == id }
        if (authorIndex == -1) {
            call.respond(HttpStatusCode.NotFound, "Author not found.")
            return
        }

        authors.removeAt(authorIndex)
        call.respond(HttpStatusCode.NoContent, "Author deleted successfully!")
    }
}