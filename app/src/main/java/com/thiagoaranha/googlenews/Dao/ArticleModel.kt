package com.thiagoaranha.googlenews.Dao

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Thiago on 28/01/18.
 */

@Entity(tableName = "article")
data class ArticleModel (@ColumnInfo(name = "author")var author: String? = "", @ColumnInfo(name = "title")var title: String? = "" , @ColumnInfo(name = "description")var description: String? = "", @ColumnInfo(name = "urlToImage")var urlToImage: String? = "" , @ColumnInfo(name = "publishedAt")var publishedAt: String? = "" ) : Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

}