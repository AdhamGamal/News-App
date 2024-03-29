package com.amg.newsapp.network.models

import com.amg.newsapp.models.Source

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