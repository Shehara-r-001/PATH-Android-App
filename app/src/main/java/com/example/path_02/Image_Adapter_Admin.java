package com.example.path_02;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Image_Adapter_Admin extends RecyclerView.Adapter<Image_Adapter_Admin.IAViewHolder> {

    Context context;
    ArrayList<Model_Image> arrayList;
    String address, caption;


    public Image_Adapter_Admin(Context context, ArrayList<Model_Image> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Image_Adapter_Admin.IAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_list, parent, false);
        return new IAViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IAViewHolder holder, int position) {

         address = arrayList.get(position).getImages();
         caption = arrayList.get(position).getCaption();

        holder.setIMG(address, caption);

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, Image_Full_Admin.class);
                i.putExtra("img_url", address);
                i.putExtra("caps", caption);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class IAViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgAdd;
        private final TextView caps;

        public IAViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAdd = itemView.findViewById(R.id.imageview_admin_app);
            caps = itemView.findViewById(R.id.caption_admin);
        }

        public void setIMG(String address, String caption) {

            Picasso.get().load(address).fit().into(imgAdd);
            caps.setText(caption);
        }
    }

}
