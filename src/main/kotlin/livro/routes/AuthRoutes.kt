package livro.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import livro.controllers.AuthController

fun Route.authRoutes() {
    val authController = AuthController()
    route("/auth") {
        post("/register") { authController.register(call) }
        post("/login") { authController.login(call) }
    }
}
