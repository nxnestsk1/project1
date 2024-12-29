package livro.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import kotlinx.html.*
import livro.controllers.BookController

fun Application.bookRoutes() {
    val bookController = BookController()

    routing {
        route("/books") {
            // Rota para obter todos os livros
            get {
                bookController.getBooks(call)
            }

            // Rota para adicionar um novo livro
            post {
                bookController.addBook(call)
            }

            // Rota para buscar livros com base nos parâmetros de query
            get("/search") {
                val query = call.request.queryParameters["query"] ?: ""
                if (query.isBlank()) {
                    call.respond(
                        mapOf(
                            "message" to "Query parameter is required for searching books.",
                            "example" to "/books/search?query=yourSearchTerm"
                        )
                    )
                } else {
                    bookController.searchBooks(call, query)
                }
            }

            // Rota para listar todos os livros no formato HTML
            get("/html") {
                call.respondHtml {
                    head {
                        title { +"Books List" }
                    }
                    body {
                        h1 { +"Books List" }
                        ul {
                            bookController.getAllBooks().forEach { book ->
                                li { +"${book.title} by ${book.author} (Genre: ${book.genre})" }
                            }
                        }
                    }
                }
            }
        }
    }
}
