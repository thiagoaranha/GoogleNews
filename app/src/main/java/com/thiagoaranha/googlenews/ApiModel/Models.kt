package com.thiagoaranha.googlenews.ApiModel

import java.io.Serializable

/**
 * Created by Thiago on 06/01/18.
 */
object Models {
    data class Result(var status: String, var totalResults: String, var articles: List<Article>) : Serializable
    data class Article(var author: String, var title: String, var description: String, var urlToImage: String, var publishedAt: String): Serializable
}