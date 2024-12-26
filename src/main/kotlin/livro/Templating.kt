package livro

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import kotlinx.html.*

fun Route.configureTemplating() {
    // Página inicial
    get("/") {
        call.respondHtml {
            head {
                title { +"Bookstore" }
                style {
                    +"""
                        body { /* estilos */ }
                        /* outros estilos */
                    """
                }
            }
            body {
                div("container") {
                    h1 { +"Welcome to the Bookstore!" }
                    p(classes = "intro-text") { +"Explore our collection or add new books." }
                    nav {
                        ul {
                            li { a(href = "/books-page") { +"View Books" } }
                            li { a(href = "/add-book") { +"Add a New Book" } }
                        }
                    }
                }
            }
        }
    }

    // Lista de livros
    get("/books-page") {
        call.respondHtml {
            head { title { +"Book List" } }
            body {
                div("container") {
                    h2 { +"Book List" }
                    ul("book-list") {
                        books.forEach { book ->
                            li { +"${book.title} by ${book.author} (Genre: ${book.genre})" }
                        }
                    }
                    a("/add-book") { +"Add a New Book" }
                    a("/") { +"Back to Home" }
                }
            }
        }
    }

    // Formulário para adicionar livros
    get("/add-book") {
        call.respondHtml {
            head { title { +"Add a New Book" } }
            body {
                div("container") {
                    h2 { +"Add a New Book" }
                    form(action = "/books", method = FormMethod.post) {
                        p { label { +"ID: " }; textInput(name = "id") { required = true } }
                        p { label { +"Title: " }; textInput(name = "title") { required = true } }
                        p { label { +"Author: " }; textInput(name = "author") { required = true } }
                        p { label { +"Genre: " }; textInput(name = "genre") { required = true } }
                        button(type = ButtonType.submit) { +"Add Book" }
                    }
                    a("/books-page") { +"Back to Book List" }
                    a("/") { +"Back to Home" }
                }
            }
        }
    }

    // Processamento do formulário para adicionar livro
    post("/books") {
        val params = call.receiveParameters()
        val id = params["id"]?.toIntOrNull() ?: 0
        val title = params["title"] ?: ""
        val author = params["author"] ?: ""
        val genre = params["genre"] ?: ""

        if (id > 0 && title.isNotEmpty() && author.isNotEmpty() && genre.isNotEmpty()) {
            books.add(Book(id, title, author, genre))
            call.respondHtml {
                body {
                    h1 { +"Book Added Successfully" }
                    p { +"$title by $author has been added to the bookstore." }
                    a(href = "/") { +"Back to Home" }
                }
            }
        } else {
            call.respondHtml {
                head { title { +"Error" } }
                body {
                    h1 { +"Error" }
                    p { +"Please provide valid book information." }
                    a(href = "/add-book") { +"Back to Add Book" }
                }
            }
        }
    }
}
