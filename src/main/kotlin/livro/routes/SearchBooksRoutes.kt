package livro.routes

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import livro.controllers.Book
import livro.controllers.BookController

fun Route.searchBooksRoutes() {
    val bookController = BookController()

    // Rota para buscar livros por título
    get("/books/search") {
        val query = call.request.queryParameters["query"] ?: ""

        // Obtém todos os livros e filtra pelo título
        val allBooks = mutableListOf<Book>() // Crie uma lista para armazenar todos os livros
        bookController.getBooks(call) // Chama o método para obter todos os livros
        allBooks.addAll(bookController.books) // Adiciona todos os livros à lista

        val results = allBooks.filter {
            it.title.contains(query, ignoreCase = true)
        }

        if (results.isEmpty()) {
            call.respond(HttpStatusCode.NoContent, "No books found matching the query.")
        } else {
            call.respond(HttpStatusCode.OK, results)
        }
    }
}