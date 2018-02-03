package com.thiagoaranha.googlenews.Service

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.thiagoaranha.googlenews.ApiModel.Models
import com.thiagoaranha.googlenews.R
import com.thiagoaranha.googlenews.Retrofit.RetrofitInitializer
import com.thiagoaranha.googlenews.UI.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.UUID.randomUUID



/**
 * Created by Thiago on 28/01/18.
 */
class NewsService() : Service() {

    private var uuid: String = randomUUID().toString()

    override fun onBind(i: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("NewsService", "onStartCommand:Before($uuid)")

        val task = NewsAsyncTask()
        task.execute()

        return Service.START_STICKY
    }

    private inner class NewsAsyncTask() : AsyncTask<String, String, Models.Result>() {

        var retorno: Models.Result? = null

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): Models.Result? {

            val call = RetrofitInitializer().service().listTopNews("google-news", "6203046c4c344a55876fbd420d3f75a3")
            call.enqueue(object: Callback<Models.Result> {
                override fun onResponse(call: Call<Models.Result>,
                                        response: Response<Models.Result>) {

                    var result = response.body()

                    retorno = result

                    onPostExecute(retorno)

                }

                override fun onFailure(call: Call<Models.Result?>?,
                                       t: Throwable?) {
                    Toast.makeText(applicationContext, "Falha", Toast.LENGTH_LONG).show()
                }
            })

            return null

        }

        override fun onPostExecute(result: Models.Result?) {
            Log.d("Service", "NewsService")

            if(result != null){
                val intent = Intent("UpdateNews")
                intent.putExtra("result", retorno)
                LocalBroadcastManager.getInstance(this@NewsService).sendBroadcast(intent)

                super.onPostExecute(retorno)
            }

        }
    }

}

