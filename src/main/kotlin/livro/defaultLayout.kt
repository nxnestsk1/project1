package livro

import io.ktor.server.html.*
import kotlinx.html.*

fun HTML.defaultLayout(content: FlowContent.() -> Unit, wishlist: List<Book>) {
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
                .wishlist-section {
                    margin-top: 40px;
                }
                .wishlist ul {
                    list-style-type: none;
                    padding: 0;
                }
                .wishlist ul li {
                    margin: 5px 0;
                }
                .book-list {
                    margin-top: 20px;
                }
                .book-list li {
                    margin: 10px 0;
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
                li { a(href = "/wishlist") { +"View Wishlist" } }
            }
        }
        div("content") {
            content()  // Aqui entra o conteúdo específico de cada página
        }
        footer {
            p { +"© 2024 Bookstore. All Rights Reserved." }
        }

        // Exibição da Wishlist
        if (wishlist.isNotEmpty()) {
            div("wishlist-section") {
                h3 { +"Your Wishlist" }
                ul("wishlist") {
                    wishlist.forEach { book ->
                        li {
                            +"${book.title} by ${book.author} (Genre: ${book.genre})"
                        }
                    }
                }
            }
        }
    }
}
