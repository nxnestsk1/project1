package livro.routes

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.io.File

fun Application.configureRouting() {
    routing {
        rootRoute()       // Adiciona a rota para a página inicial
        authRoutes()      // Rotas de autenticação
        authorRoutes()    // Rotas de autores
        bookRoutes()      // Rotas de livros
        wishlistRoutes()  // Rotas de wishlist // Rotas de gêneros
    }
}

// Função de rota para a página inicial
fun Route.rootRoute() {
    get("/") {
        val indexFile = File("src/main/resources/static/index.html")
        if (indexFile.exists()) {
            call.respondFile(indexFile)
        } else {
            call.respondText("Index file not found.", status = HttpStatusCode.NotFound)
        }
    }
}