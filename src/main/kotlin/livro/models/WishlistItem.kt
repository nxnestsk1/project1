package livro.models

import kotlinx.serialization.Serializable

@Serializable
data class WishlistItem(val id: Int, val bookId: Int) // ID do item e ID do livro