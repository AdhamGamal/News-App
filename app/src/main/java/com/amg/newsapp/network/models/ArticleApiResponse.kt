package com.amg.newsapp.network.models

import com.amg.newsapp.models.Article

data class ArticleApiResponse(
    private val source: SourceApiResponse? = null,
    private val author: String? = null,
    private val title: String? = null,
    private val description: String? = null,
    private val url: String? = null,
    private val urlToImage: String? = null,
    private val publishedAt: String? = null,
    private val content: String? = null
) {
    fun toArticle(): Article {
        return Article(
            source?.name ?: "",
            author ?: "",
            title ?: "",
            description ?: "",
            url ?: "",
            urlToImage ?: "",
            publishedAt ?: "",
            content ?: "",
        )
    }
}