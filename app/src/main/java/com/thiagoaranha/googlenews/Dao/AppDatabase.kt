package com.thiagoaranha.googlenews.Dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Thiago on 28/01/18.
 */

@Database(entities = arrayOf(ArticleModel::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun articleDao(): ArticleDao

}