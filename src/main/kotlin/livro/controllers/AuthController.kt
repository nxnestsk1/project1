package livro.controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import livro.models.User

class AuthController {
    private val users = mutableListOf<User>()

    suspend fun register(call: ApplicationCall) {
        val jsonBody = call.receiveText()
        val user = Json.decodeFromString<User>(jsonBody)
        users.add(user)
        call.respondText("User  registered successfully", status = HttpStatusCode.Created)
    }

    suspend fun login(call: ApplicationCall) {
        val jsonBody = call.receiveText()
        val user = Json.decodeFromString<User>(jsonBody)
        if (users.any { it.username == user.username && it.password == user.password }) {
            call.respondText("Login successful", status = HttpStatusCode.OK)
        } else {
            call.respondText("Invalid username or password", status = HttpStatusCode.Unauthorized)
        }
    }
}