package livro

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.Level
import kotlinx.serialization.json.Json
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import livro.routes.configureRouting // Importa a função de configuração de rotas

fun main() {
    embeddedServer(Netty, port = 8082) {
        module() // Chama a função 'module' para inicializar a aplicação
    }.start(wait = true)
}

fun Application.module() {
    // Instalar plugins necessários
    install(CallLogging) {
        level = Level.INFO
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "An error occurred: ${cause.localizedMessage}")
            )
        }
    }

    // Instalar o ContentNegotiation para serialização e desserialização JSON
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    // Configurar rotas
    configureRouting() // Chama a função de configuração de rotas
}