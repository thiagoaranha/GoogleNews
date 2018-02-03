package com.thiagoaranha.googlenews.Dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.support.annotation.WorkerThread
import io.reactivex.Flowable
import io.reactivex.internal.operators.flowable.FlowableAll

/**
 * Created by Thiago on 28/01/18.
 */
@Dao
interface ArticleDao {

    @Query("select * from article")
    fun getAllArticles(): List<ArticleModel>

    @Query("select * from article where id = :arg0")
    fun findArticleById(id: Long): ArticleModel

    @Insert(onConflict = REPLACE)
    fun insertArticle(article: ArticleModel)

    @Update(onConflict = REPLACE)
    fun updateArticle(article: ArticleModel)

    @Delete
    fun deleteArticle(article: ArticleModel)

    @Query("delete from article")
    fun deleteAllArticles()

}