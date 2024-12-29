package livro.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import livro.models.WishlistItem

class WishlistController {
    private val wishlist = mutableListOf<WishlistItem>()

    suspend fun addToWishlist(call: ApplicationCall) {
        val jsonBody = call.receiveText()
        val item = Json.decodeFromString<WishlistItem>(jsonBody)
        wishlist.add(item)
        call.respondText("Item added to wishlist", status = io.ktor.http.HttpStatusCode.Created)
    }

    suspend fun removeFromWishlist(call: ApplicationCall) {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val removed = wishlist.removeIf { it.id == id }
            if (removed) {
                call.respondText("Item removed from wishlist", status = io.ktor.http.HttpStatusCode.OK)
            } else {
                call.respondText("Item not found in wishlist", status = io.ktor.http.HttpStatusCode.NotFound)
            }
        } else {
            call.respondText("Invalid ID", status = io.ktor.http.HttpStatusCode.BadRequest)
        }
    }

    suspend fun getWishlist(call: ApplicationCall) {
        call.respond(wishlist)
    }
}