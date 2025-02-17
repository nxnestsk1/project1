package livro.models

import kotlinx.serialization.Serializable

@Serializable
data class Author(val id: Int, val name: String, val bio: String)