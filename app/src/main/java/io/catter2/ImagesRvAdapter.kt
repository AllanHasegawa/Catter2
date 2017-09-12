package io.catter2

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.squareup.picasso.Picasso

class ImagesRvAdapter(private val onClickListener: ImageOnClick?)
    : RecyclerView.Adapter<ImagesRvAdapter.ImageVH>() {

    interface ImageOnClick {
        fun imageClicked(view: ImageView, url: String)
    }

    class ImageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIv: ImageView = itemView.findViewById(R.id.image_iv) as ImageView
    }

    companion object {
        private val TAG = "ImagesRvAdapter"
    }

    private var imageUrls: List<String>? = null

    fun updateImageUrls(imageUrls: List<String>) {
        this.imageUrls = imageUrls
        notifyDataSetChanged() // This is bad. Used for simplicity.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_imagesrv_img, parent, false)
        return ImageVH(view)
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {
        val imageUrl = imageUrls!![position]
        Log.d(TAG, "Binding: " + imageUrl)
        val picasso = Picasso.with(holder.imageIv.context)
        picasso.setIndicatorsEnabled(false)
        picasso.isLoggingEnabled = true
        picasso.load(imageUrl).error(R.mipmap.azunyan_2).into(holder.imageIv)

        if (onClickListener != null) {
            holder.imageIv.setOnClickListener {
                val adapterPos = holder.adapterPosition
                val url = imageUrls!![adapterPos]
                onClickListener.imageClicked(holder.imageIv, url)
            }
        }
    }

    override fun getItemCount(): Int = imageUrls?.size ?: 0
}
