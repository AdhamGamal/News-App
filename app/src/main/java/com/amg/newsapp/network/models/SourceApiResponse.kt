package com.amg.newsapp.network.models

data class SourceApiResponse(
    private val id: String? = null,
    private val name: String? = null
) {
    fun toSource(): Source {
        return Source(
            id ?: "",
            name ?: "",
        )
    }
}

data class Source(
    val id: String = "",
    val name: String = ""
)