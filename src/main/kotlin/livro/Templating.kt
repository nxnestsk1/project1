package livro

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import kotlinx.html.*

fun Route.configureTemplating() {
    // Função para incluir o HUD nas páginas
    fun HTML.defaultLayout(content: FlowContent.() -> Unit) {
        head {
            meta(charset = "UTF-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title { +"Bookstore" }
            style {
                +"""
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f0f0f0;
                        margin: 0;
                        padding: 0;
                        text-align: center;
                        display: flex;
                        flex-direction: column;
                        min-height: 100vh;
                    }
                    header {
                        background-color: #4CAF50;
                        color: white;
                        padding: 10px 0;
                    }
                    h1 {
                        margin: 0;
                        font-size: 36px;
                    }
                    .intro-text {
                        margin-top: 20px;
                        font-size: 18px;
                        color: #555;
                    }
                    nav {
                        margin-top: 30px;
                    }
                    nav ul {
                        list-style-type: none;
                        padding: 0;
                    }
                    nav ul li {
                        display: inline-block;
                        margin: 0 15px;
                    }
                    nav ul li a {
                        text-decoration: none;
                        color: white;
                        background-color: #4CAF50;
                        padding: 12px 25px;
                        border-radius: 5px;
                        transition: background-color 0.3s;
                    }
                    nav ul li a:hover {
                        background-color: #45a049;
                    }
                    footer {
                        margin-top: auto;
                        padding: 20px;
                        background-color: #333;
                        color: white;
                    }
                """.trimIndent()
            }
        }
        body {
            header {
                h1 { +"Welcome to the Bookstore!" }
            }
            p("intro-text") { +"Explore our collection or add a new book to our library!" }
            nav {
                ul {
                    li { a(href = "/books-page") { +"View Books" } }
                    li { a(href = "/add-book") { +"Add a New Book" } }
                }
            }
            div("content") {
                content() // Aqui entra o conteúdo específico de cada página
            }
            footer {
                p { +"© 2024 Bookstore. All Rights Reserved." }
            }
        }
    }

    // Página inicial
    get("/") {
        call.respondHtml {
            defaultLayout {
                h2 { +"Welcome!" }
                p { +"Explore our collection or add a new book using the options above." }
            }
        }
    }

    // Página de lista de livros
    get("/books-page") {
        call.respondHtml {
            defaultLayout {
                h2 { +"Book List" }
                ul("book-list") {
                    books.forEach { book ->
                        li { +"${book.title} by ${book.author} (Genre: ${book.genre})" }
                    }
                }
                a(href = "/add-book") { +"Add a New Book" }
            }
        }
    }

    // Página de formulário para adicionar livros
    get("/add-book") {
        call.respondHtml {
            defaultLayout {
                h2 { +"Add a New Book" }
                form(action = "/books", method = FormMethod.post) {
                    p { label { +"ID: " }; textInput(name = "id") { required = true } }
                    p { label { +"Title: " }; textInput(name = "title") { required = true } }
                    p { label { +"Author: " }; textInput(name = "author") { required = true } }
                    p { label { +"Genre: " }; textInput(name = "genre") { required = true } }
                    button(type = ButtonType.submit) { +"Add Book" }
                }
            }
        }
    }

    // Processamento do formulário para adicionar livros
    post("/books") {
        val params = call.receiveParameters()
        val id = params["id"]?.toIntOrNull() ?: 0
        val title = params["title"] ?: ""
        val author = params["author"] ?: ""
        val genre = params["genre"] ?: ""

        if (id > 0 && title.isNotEmpty() && author.isNotEmpty() && genre.isNotEmpty()) {
            books.add(Book(id, title, author, genre))
            call.respondHtml {
                defaultLayout {
                    h2 { +"Book Added Successfully" }
                    p { +"$title by $author has been added to the bookstore." }
                    a(href = "/") { +"Back to Home" }
                }
            }
        } else {
            call.respondHtml {
                defaultLayout {
                    h2 { +"Error" }
                    p { +"Please provide valid book information." }
                    a(href = "/add-book") { +"Back to Add Book" }
                }
            }
        }
    }
}
