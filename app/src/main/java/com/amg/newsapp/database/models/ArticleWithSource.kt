package com.amg.newsapp.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleWithSource(
    @Embedded val article: ArticleEntity,
    @Relation(
        parentColumn = "source_id",
        entityColumn = "id"
    )
    val source: SourceEntity
)