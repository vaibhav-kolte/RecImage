package com.vkolte.cargofxtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolderAdapter>{

    private static final String TAG = "ImageAdapter";
    private Context context;
    private List<Images> imagesList;


    public ImageAdapter(Context context, List<Images> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rec_layout, parent, false);
        return new ViewHolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapter holder, int position) {

        try{
            Images images = imagesList.get(position);

            Glide.with(context)
                    .load(images.getUrl())
                    .into(holder.image);
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: Exception : "+e.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ViewHolderAdapter extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
        }
    }
}
