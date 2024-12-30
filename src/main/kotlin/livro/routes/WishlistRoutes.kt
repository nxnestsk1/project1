package livro.routes

import io.ktor.server.routing.*
import io.ktor.server.application.*
import livro.controllers.WishlistController

fun Route.wishlistRoutes() {
    val wishlistController = WishlistController()
    route("/wishlist") {
        post("/") { wishlistController.addToWishlist(call) }
        delete("/{id}") { wishlistController.removeFromWishlist(call) }
        get("/") { wishlistController.getWishlist(call) }
    }
}