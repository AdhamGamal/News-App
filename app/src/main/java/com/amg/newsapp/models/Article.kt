package com.amg.newsapp.models

import com.amg.newsapp.database.models.ArticleEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

    fun generateId(): String {
        return "$title $url $publishedAt"
    }

    companion object {
        const val DEFAULT_ID = -1L

        fun generateFakeData(size: Int): List<Article> {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")

            return List(size) { index ->
                val date = Date(System.currentTimeMillis() + index * 24 * 60 * 60 * 1000)
                Article(
                    source = "source $index",
                    author = "author $index",
                    title = "title $index",
                    description = "description $index",
                    url = "url $index",
                    urlToImage = "urlToImage $index",
                    publishedAt = dateFormat.format(date),
                    content = "content $index"
                )
            }
        }
    }
}