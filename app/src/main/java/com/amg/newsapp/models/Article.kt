package com.amg.newsapp.models

import com.amg.newsapp.database.models.ArticleEntity
import com.amg.newsapp.database.models.SourceEntity

data class Article(
    val source: Source = Source(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = ""
) {
    fun getArticleEntity(): ArticleEntity {
        return ArticleEntity(
            source.id,
            author,
            title,
            description,
            url,
            urlToImage,
            publishedAt,
            content,
        )
    }

    fun getSourceEntity(): SourceEntity {
        return SourceEntity(
            source.id,
            source.name
        )
    }
}