package com.thiagoaranha.googlenews.UI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.thiagoaranha.googlenews.ApiModel.Models
import com.thiagoaranha.googlenews.R
import com.squareup.picasso.Picasso
import com.thiagoaranha.googlenews.Dao.ArticleModel


/**
 * Created by Thiago on 21/01/18.
 */
class NewsAdapter(private var activity: Activity, private var items: List<ArticleModel>) : BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtTitulo: TextView? = null
        var txtDescricao: TextView? = null
        var image: ImageView? = null

        init {
            this.txtTitulo = row?.findViewById<TextView>(R.id.txtTitulo)
            this.txtDescricao = row?.findViewById<TextView>(R.id.txtDescricao)
            this.image = row?.findViewById<ImageView>(R.id.imageView)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.news_item, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var article = items[position]
        viewHolder.txtTitulo?.text = article.title
        viewHolder.txtDescricao?.text = article.description

        Picasso.with(activity).load(article.urlToImage).into(viewHolder.image)

        view?.setOnClickListener(View.OnClickListener {

            var intent = Intent(activity, ArticleActivity::class.java)
            intent.putExtra("article", items[position])
            activity.startActivity(intent)

        })

        return view as View
    }

    override fun getItem(i: Int): ArticleModel {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}