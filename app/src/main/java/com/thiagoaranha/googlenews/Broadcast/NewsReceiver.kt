package com.thiagoaranha.googlenews.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.thiagoaranha.googlenews.ApiModel.Models
import com.thiagoaranha.googlenews.Dao.ArticleModel
import com.thiagoaranha.googlenews.NewsApp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Thiago on 28/01/18.
 */
class NewsReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "UpdateNews") {

            var apiResult = intent.getSerializableExtra("result") as? Models.Result

            if(apiResult != null){
                val compositeDisposable = CompositeDisposable()

                compositeDisposable.add(Observable.fromCallable { NewsApp.database?.articleDao()?.deleteAllArticles() }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())

                for (article in apiResult?.articles.orEmpty()) {
                    var articleModel = ArticleModel()
                    articleModel.author = article.author
                    articleModel.description = article.description
                    articleModel.publishedAt = article.publishedAt
                    articleModel.title = article.title
                    articleModel.urlToImage = article.urlToImage

                    compositeDisposable.add(Observable.fromCallable { NewsApp.database?.articleDao()?.insertArticle(articleModel) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }
            }

        }
    }
}