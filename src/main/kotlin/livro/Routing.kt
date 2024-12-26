import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.http.content.*
import kotlinx.serialization.Serializable
import org.slf4j.event.Level
import java.io.File

@Serializable
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String
)

val books = mutableListOf(
    Book(1, "Kotlin in Action", "Dmitry Jemerov", "Programming"),
    Book(2, "Clean Code", "Robert C. Martin", "Programming")
)

fun Application.module() {
    install(ContentNegotiation) { json() }
    install(CallLogging) { level = Level.INFO }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${cause.localizedMessage}")
        }
    }

    // Serve arquivos estáticos
    routing {
        static(remotePath = "/static") {
            // Certifique-se de que o diretório está correto e os arquivos estão na pasta correta
            files(folder = "src/main/resources/static")  // Servindo os arquivos diretamente dessa pasta
        }

        // Página inicial que serve o index.html
        get("/") {
            // Certifique-se de que o arquivo existe no caminho correto
            val indexFile = File("src/main/resources/static/index.html")
            if (indexFile.exists()) {
                call.respondFile(indexFile)  // Responde com o arquivo index.html
            } else {
                call.respond(HttpStatusCode.NotFound, "index.html not found!")
            }
        }

        // Rota para exibir livros
        get("/books") {
            call.respond(books)
        }
    }
}

