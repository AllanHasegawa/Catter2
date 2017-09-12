package io.catter2.favorites_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.catter2.ImagesRvAdapter
import io.catter2.R
import io.catter2.favorites.GetFavoritesUseCase
import io.catter2.list_activity.ListActivity
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.content_favorites.*
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity() {
    companion object {
        private val TAG = "ImagesRvAdapter"

        fun launch(context: Context) {
            val intent = Intent(context, FavoritesActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var getFavoritesUseCase: GetFavoritesUseCase

    private var rvAdapter: ImagesRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        setSupportActionBar(favorites_toolbar)

        favorites_fab.setOnClickListener { ListActivity.launch(this@FavoritesActivity) }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rvAdapter = ImagesRvAdapter(null)
        favorites_rv.layoutManager = LinearLayoutManager(this)
        favorites_rv.adapter = rvAdapter

        FavoritesActivityDIComponent.initializeAndInject(this)
    }

    override fun onResume() {
        super.onResume()
        getFavoritesUseCase.getFavorites { urls ->
            Log.d(TAG, "Updated favorites: " + urls.toString())
            rvAdapter!!.updateImageUrls(urls)
        }
    }

    override fun onPause() {
        getFavoritesUseCase.clear()
        super.onPause()
    }
}
