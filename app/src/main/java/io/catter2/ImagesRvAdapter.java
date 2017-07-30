package io.catter2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesRvAdapter extends RecyclerView.Adapter<ImagesRvAdapter.ImageVH> {

    private static String TAG = "ImagesRvAdapter";

    public interface ImageOnClick {
        void imageClicked(ImageView view, String url);
    }

    static class ImageVH extends RecyclerView.ViewHolder {
        private ImageView imageIv;

        public ImageVH(View itemView) {
            super(itemView);
            imageIv = (ImageView) itemView.findViewById(R.id.image_iv);
        }
    }

    private List<String> imageUrls;
    private ImageOnClick onClickListener;

    public ImagesRvAdapter(ImageOnClick onClick) {
        super();
        this.onClickListener = onClick;
    }

    public void updateImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        notifyDataSetChanged(); // This is bad. Used for simplicity.
    }

    @Override
    public ImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_imagesrv_img, parent, false);
        return new ImageVH(view);
    }

    @Override
    public void onBindViewHolder(final ImageVH holder, final int position) {
        String imageUrl = imageUrls.get(position);
        Log.d(TAG, "Binding: " + imageUrl);
        Picasso picasso = Picasso.with(holder.imageIv.getContext());
        picasso.setIndicatorsEnabled(false);
        picasso.setLoggingEnabled(true);
        picasso.load(imageUrl).into(holder.imageIv);

        if (onClickListener != null) {
            holder.imageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    String url = imageUrls.get(position);
                    onClickListener.imageClicked(holder.imageIv, url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (imageUrls == null) {
            return 0;
        }
        return imageUrls.size();
    }

}
