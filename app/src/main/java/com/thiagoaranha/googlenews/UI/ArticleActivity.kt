package com.thiagoaranha.googlenews.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.thiagoaranha.googlenews.ApiModel.Models
import com.thiagoaranha.googlenews.Dao.ArticleModel
import com.thiagoaranha.googlenews.R
import kotlinx.android.synthetic.main.activity_article.*

/**
 * Created by Thiago on 21/01/18.
 */
class ArticleActivity() : AppCompatActivity() {

    private val ARTICLE = "article"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        var articleExtra = intent.getSerializableExtra(ARTICLE)
        var article = articleExtra as ArticleModel

        txtTitle.text = article.title
        txtDescription.text = article.description
        Picasso.with(this).load(article.urlToImage).into(imageViewArticle)
    }

}