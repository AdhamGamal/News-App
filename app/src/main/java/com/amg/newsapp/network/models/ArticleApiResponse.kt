package com.amg.newsapp.network.models

import com.amg.newsapp.database.models.ArticleEntity
import com.amg.newsapp.database.models.SourceEntity

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
            source?.toSource() ?: Source(),
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