package livro.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import livro.controllers.BookController

fun Route.bookRoutes() {
    val bookController = BookController()

    route("/books") {
        post("/") { bookController.addBook(call) }
        get("/") { bookController.getBooks(call) }
        get("/{id}") { bookController.getBookById(call) }
        put("/{id}") { bookController.updateBook(call) }
        delete("/{id}") { bookController.deleteBook(call) }
    }
}