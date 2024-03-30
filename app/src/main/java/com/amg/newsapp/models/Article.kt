package com.amg.newsapp.models

import com.amg.newsapp.database.models.ArticleEntity

data class Article(
    val source: String = "",
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = "",
    var articleId: Long = DEFAULT_ID
) {
    fun toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            source,
            author,
            title,
            description,
            url,
            urlToImage,
            publishedAt,
            content,
        )
    }

    fun isFavorite() = articleId != DEFAULT_ID

    companion object {
        private const val DEFAULT_ID = -1L
    }
}