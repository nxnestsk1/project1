package livro.repositories

import livro.models.WishlistItem

object WishlistRepository {
    private val wishlist = mutableListOf<WishlistItem>() // Lista de desejos simulada

    fun addItem(item: WishlistItem) {
        wishlist.add(item)
    }

    fun removeItem(id: Int): Boolean {
        return wishlist.removeIf { it.id == id }
    }

    fun getAllItems(): List<WishlistItem> {
        return wishlist
    }
}