package com.thiagoaranha.googlenews.UI

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.thiagoaranha.googlenews.ApiModel.Models
import com.thiagoaranha.googlenews.R
import com.thiagoaranha.googlenews.Retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import android.content.Intent
import com.thiagoaranha.googlenews.Broadcast.NewsReceiver
import com.thiagoaranha.googlenews.Service.NewsService
import android.support.v4.content.LocalBroadcastManager
import android.content.IntentFilter
import com.thiagoaranha.googlenews.Dao.AppDatabase
import com.thiagoaranha.googlenews.Dao.ArticleModel
import com.thiagoaranha.googlenews.NewsApp
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import android.support.v4.widget.SwipeRefreshLayout
import com.thiagoaranha.googlenews.R.id.swipeContainer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.net.InetAddress
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager




class MainActivity : AppCompatActivity() {

    private val broadCastReceiver = NewsReceiver()
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onResume() {
        super.onResume()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val i = Intent(this@MainActivity, NewsService::class.java)
        startService(i)

        val intentFilter = IntentFilter()
        intentFilter.addAction("UpdateNews")

        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiver, intentFilter)

        swipeContainer = (findViewById<View>(R.id.swipeContainer) as SwipeRefreshLayout)
        swipeContainer.setOnRefreshListener()
        {
            if(isInternetAvailable()){
                updateNews()
                updateArticlesList()
            }else{
                Toast.makeText(this@MainActivity, "No internet connection!", Toast.LENGTH_LONG)
            }

            swipeContainer.setRefreshing(false)
        }

        updateArticlesList()

    }

    private fun updateArticlesList() {

        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(Observable.fromCallable { NewsApp.database?.articleDao()?.getAllArticles() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var adapter = NewsAdapter(this@MainActivity, it )

                    var listNews: ListView? = null
                    listNews = findViewById<ListView?>(R.id.listNews)

                    listNews?.adapter = adapter
                }))

    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadCastReceiver)
    }

    fun updateNews(){
        val i = Intent(this@MainActivity, NewsService::class.java)
        startService(i)


    }

    fun isInternetAvailable(): Boolean {
        var cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkinfo = cm.activeNetworkInfo

        return networkinfo != null && networkinfo.isConnected
    }

}
