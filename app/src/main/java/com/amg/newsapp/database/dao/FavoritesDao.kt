package com.amg.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.amg.newsapp.database.models.ArticleEntity
import com.amg.newsapp.database.models.ArticleWithSource
import com.amg.newsapp.database.models.SourceEntity
import com.amg.newsapp.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Transaction
    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<ArticleWithSource>>

    @Insert
    suspend fun insertArticle(article: ArticleEntity): Long

    @Insert
    suspend fun insertSource(source: SourceEntity): Long

    @Transaction
    suspend fun insertArticleWithSource(article: Article) {
        insertArticle(article.getArticleEntity())
        insertSource(article.getSourceEntity())
    }


    @Transaction
    @Query("SELECT COUNT(*) FROM articles WHERE source_id = :sourceId")
    suspend fun getArticleCountForSource(sourceId: String): Int

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Delete
    suspend fun deleteSource(source: SourceEntity)

    @Transaction
    suspend fun deleteSourceIfNoArticles(articleWithSource: ArticleWithSource) {
        deleteArticle(articleWithSource.article)
        val articleCount = getArticleCountForSource(articleWithSource.source.sourceId)
        if (articleCount == 0) {
            deleteSource(articleWithSource.source)
        }
    }
}
