package io.catter2.list_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.ImageView
import com.plattysoft.leonids.ParticleSystem
import io.catter2.ImagesRvAdapter
import io.catter2.R
import io.catter2.cat_api.FetchCatImagesUseCase
import io.catter2.favorites.AddFavoriteUseCase
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*
import javax.inject.Inject

class ListActivity : AppCompatActivity() {
    companion object {
        private val TAG = "List"

        fun launch(context: Context) {
            val intent = Intent(context, ListActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Inject lateinit var addFavoriteUseCase: AddFavoriteUseCase
    @Inject lateinit var fetchCatImagesUseCase: FetchCatImagesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(list_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = ImagesRvAdapter(object : ImagesRvAdapter.ImageOnClick {
            override fun imageClicked(view: ImageView, url: String) {
                addUrlToUserFavoritesList(view, url)
            }
        })
        list_rv.layoutManager = LinearLayoutManager(this)
        list_rv.adapter = adapter

        ListActivityDIComponent.initializeAndInject(this)
        fetchCatImagesUseCase.getImagesUrls { urls -> adapter.updateImageUrls(urls) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addUrlToUserFavoritesList(view: ImageView, url: String) {
        val imageAdded = addFavoriteUseCase.addFavoriteUrl(url)

        val msgId = when (imageAdded) {
            true -> R.string.list_user_favorite_url_added_success
            else -> R.string.list_user_favorite_url_already_in
        }
        Snackbar.make(list_rv, msgId, Snackbar.LENGTH_SHORT).show()

        if (imageAdded) {
            ParticleSystem(this@ListActivity, 500, R.mipmap.azunyan_2, 2000)
                    .setAcceleration(0.0005f, 90)
                    .setSpeedRange(0.2f, 0.5f)
                    .setRotationSpeedRange(90f, 180f)
                    .setInitialRotationRange(0, 180)
                    .setFadeOut(500)
                    .setScaleRange(0.1f, 1.0f)
                    .oneShot(view, 100)
        }
    }
}
