package livro.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class Book(val id: Int, val title: String, val author: String)

class BookController {
    // Lista de livros
    val books = mutableListOf<Book>()

    // Adiciona um novo livro
    suspend fun addBook(call: ApplicationCall) {
        val parameters = call.receiveParameters()
        val id = parameters["id"]?.toIntOrNull()
        val title = parameters["title"] ?: ""
        val author = parameters["author"] ?: ""

        if (id == null || title.isBlank() || author.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, "All fields are required!")
            return
        }

        if (books.any { it.id == id }) {
            call.respond(HttpStatusCode.Conflict, "A book with ID $id already exists!")
            return
        }

        books.add(Book(id, title, author))
        call.respond(HttpStatusCode.Created, "Book added successfully!")
    }

    // Lista todos os livros
    suspend fun getBooks(call: ApplicationCall) {
        if (books.isEmpty()) {
            call.respond(HttpStatusCode.NoContent, "No books found.")
        } else {
            call.respond(HttpStatusCode.OK, books)
        }
    }

    // Busca um livro por ID
    suspend fun getBookById(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
            return
        }

        val book = books.find { it.id == id }
        if (book == null) {
            call.respond(HttpStatusCode.NotFound, "Book not found.")
        } else {
            call.respond(HttpStatusCode.OK, book)
        }
    }

    // Atualiza um livro existente
    suspend fun updateBook(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
            return
        }

        val parameters = call.receiveParameters()
        val title = parameters["title"] ?: ""
        val author = parameters["author"] ?: ""

        val bookIndex = books.indexOfFirst { it.id == id }
        if (bookIndex == -1) {
            call.respond(HttpStatusCode.NotFound, "Book not found.")
            return
        }

        books[bookIndex] = Book(id, title, author)
        call.respond(HttpStatusCode.OK, "Book updated successfully!")
    }

    // Deleta um livro
    suspend fun deleteBook(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid book ID")
            return
        }

        val bookIndex = books.indexOfFirst { it.id == id }
        if (bookIndex == -1) {
            call.respond(HttpStatusCode.NotFound, "Book not found.")
            return
        }

        books.removeAt(bookIndex)
        call.respond(HttpStatusCode.NoContent, "Book deleted successfully!")
    }

    // Busca livros por título
    suspend fun searchBooks(call: ApplicationCall) {
        val query = call.request.queryParameters["query"] ?: ""

        // Filtra os livros com base no título
        val results = books.filter { it.title.contains(query, ignoreCase = true) }

        if (results.isEmpty()) {
            call.respond(HttpStatusCode.NoContent, "No books found matching the query.")
        } else {
            call.respond(HttpStatusCode.OK, results)
        }
    }
}