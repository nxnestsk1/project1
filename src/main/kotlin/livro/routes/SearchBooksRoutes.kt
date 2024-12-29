package livro.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import livro.controllers.BookController

fun Route.searchBooksRoutes() {
    val bookController = BookController()
    route("/books/search") {
        get {
            val query = call.request.queryParameters["query"] ?: ""
            val results = bookController.getAllBooks().filter {
                it.title.contains(query, ignoreCase = true)
            }
            call.respond(results)
        }
    }
}
