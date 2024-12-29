package livro.repositories

import livro.models.Book

object BookRepository {
    private val books = mutableListOf<Book>() // Lista de livros simulada

    fun addBook(book: Book) {
        books.add(book)
    }

    fun getAllBooks(): List<Book> {
        return books
    }

    fun searchBooks(title: String?, author: String?, genre: String?): List<Book> {
        return books.filter { book ->
            (title == null || book.title.contains(title, ignoreCase = true)) &&
                    (author == null || book.author.contains(author, ignoreCase = true)) &&
                    (genre == null || book.genre.equals(genre, ignoreCase = true))
        }
    }
}