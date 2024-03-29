package com.amg.newsapp.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources")
data class SourceEntity(
    @PrimaryKey @ColumnInfo(name = "id") val sourceId: String,
    val name: String = ""
)