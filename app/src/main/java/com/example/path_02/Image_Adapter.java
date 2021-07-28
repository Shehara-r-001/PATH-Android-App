package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Image_Adapter extends RecyclerView.Adapter<Image_Adapter.IViewHolder> {

    Context context;
    ArrayList<Model_Image> array;

    public Image_Adapter(Context context, ArrayList<Model_Image> array){
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public Image_Adapter.IViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_collection, parent, false);
        return new IViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Image_Adapter.IViewHolder holder, int position) {

        String url = array.get(position).getImages();
        String caps = array.get(position).getCaption();

        holder.setImg( url, caps);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class IViewHolder extends RecyclerView.ViewHolder{

        private final ImageView img;
        private final TextView capt;

        public IViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_placeholder_imgcoll);
            capt = itemView.findViewById(R.id.img_caption);
        }

        public void setImg(String url, String caps) {

            Picasso.get().load(url).into(img);
            capt.setText(caps);
        }
    }
}
