package com.thiagoaranha.googlenews

import android.app.Application
import android.arch.persistence.room.Room
import com.thiagoaranha.googlenews.Dao.AppDatabase

/**
 * Created by Thiago on 28/01/18.
 */
class NewsApp : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        NewsApp.database =  Room.databaseBuilder(this, AppDatabase::class.java, "news-db").build()
    }

}