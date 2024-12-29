package routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import livro.controllers.AuthController
import livro.controllers.AuthorController
import livro.controllers.BookController
import livro.controllers.WishlistController
import java.io.File
import io.ktor.http.HttpStatusCode
import io.ktor.server.html.*
import kotlinx.html.*



fun Application.configureRouting() {
    routing {
        rootRoute()       // Adiciona a rota para a página inicial
        authRoutes()      // Rotas de autenticação
        authorRoutes()    // Rotas de autores
        bookRoutes()      // Rotas de livros
        wishlistRoutes()  // Rotas de wishlist
        searchBooksRoutes() // Rota de busca de livros
    }
}

// Função de rota para a página inicial
fun Route.rootRoute() {
    get("/") {
        val indexFile = File("src/main/resources/static/index.html")
        if (indexFile.exists()) {
            call.respondFile(indexFile)
        } else {
            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title { +"Book API - Home" }
                }
                body {
                    h1 { +"Welcome to the Book API!" }
                    p { +"Explore the API by visiting the routes below:" }
                    ul {
                        li { a("/auth/register") { +"Register" } }
                        li { a("/auth/login") { +"Login" } }
                        li { a("/authors") { +"Authors" } }
                        li { a("/books") { +"Books" } }
                        li { a("/wishlist") { +"Wishlist" } }
                        li { a("/books/search") { +"Search Books" } }
                    }
                }
            }
        }
    }
}

// Função de rotas de autenticação
fun Route.authRoutes() {
    val authController = AuthController()
    route("/auth") {
        post("/register") { authController.register(call) }
        post("/login") { authController.login(call) }
    }
}

// Função de rotas de autores
fun Route.authorRoutes() {
    val authorController = AuthorController()
    route("/authors") {
        post("/") { authorController.addAuthor(call) }
        get("/") { authorController.getAuthors(call) }
    }
}

// Função de rotas de livros
fun Route.bookRoutes() {
    val bookController = BookController()
    route("/books") {
        post("/") { bookController.addBook(call) }
        get("/") { bookController.getBooks(call) }
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

// Função de rotas de wishlist
fun Route.wishlistRoutes() {
    val wishlistController = WishlistController()
    route("/wishlist") {
        post("/") { wishlistController.addToWishlist(call) }
        delete("/{id}") { wishlistController.removeFromWishlist(call) }
        get("/") { wishlistController.getWishlist(call) }
    }
}

// Função de rotas de pesquisa de livros
fun Route.searchBooksRoutes() {
    val bookController = BookController()
    route("/books/search") {
        get {
            bookController.searchBooks(call)
        }
    }
}
