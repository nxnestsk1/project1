package livro.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import livro.controllers.AuthorController

fun Route.authorRoutes() {
    val authorController = AuthorController()
    route("/authors") {
        post("/") { authorController.addAuthor(call) }
        get("/") { authorController.getAuthors(call) }
        get("/{id}") { authorController.getAuthorById(call) }
        put("/{id}") { authorController.updateAuthor(call) }
        delete("/{id}") { authorController.deleteAuthor(call) }
    }
}