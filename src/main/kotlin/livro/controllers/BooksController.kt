package livro.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import livro.models.Book

class BookController {
    private val books = mutableListOf<Book>()

    // Retorna todos os livros
    fun getAllBooks(): List<Book> {
        return books
    }

    // Adiciona um livro
    suspend fun addBook(call: ApplicationCall) {
        val book = call.receive<Book>()
        books.add(book)
        call.respond(mapOf("message" to "Book added successfully!"))
    }

    // Retorna todos os livros para a chamada atual
    suspend fun getBooks(call: ApplicationCall) {
        call.respond(books)
    }

    // Pesquisa livros pelo título
    suspend fun searchBooks(call: ApplicationCall) {
        val query = call.request.queryParameters["query"] ?: ""
        val results = books.filter { it.title.contains(query, ignoreCase = true) }
        call.respond(results)
    }
}
