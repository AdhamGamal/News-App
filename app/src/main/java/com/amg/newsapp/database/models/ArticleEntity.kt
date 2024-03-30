package com.amg.newsapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amg.newsapp.models.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @ColumnInfo(name = "source") val source: String = "",
    @ColumnInfo(name = "author") val author: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "url") val url: String = "",
    @ColumnInfo(name = "url_to_image") val urlToImage: String = "",
    @ColumnInfo(name = "published_at") val publishedAt: String = "",
    @ColumnInfo(name = "content") val content: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var articleId: Long = 0

    fun toArticle(): Article {
        return Article(
            source, author, title, description, url, urlToImage, publishedAt, content, articleId
        )
    }
}