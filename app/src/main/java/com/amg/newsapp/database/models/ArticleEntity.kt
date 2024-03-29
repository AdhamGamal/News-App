package com.amg.newsapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles",
    foreignKeys = [
        ForeignKey(entity = SourceEntity::class, parentColumns = ["id"], childColumns = ["source_id"])
    ],
    indices = [Index("source_id")]
)
data class ArticleEntity(
    @ColumnInfo(name = "source_id") val sourceId: String = "",
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
}