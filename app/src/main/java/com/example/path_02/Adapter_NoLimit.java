package com.example.path_02;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_NoLimit extends RecyclerView.Adapter<Adapter_NoLimit.LViewHolder> {

    Context context;
    ArrayList<Helper> list_nl;


    public Adapter_NoLimit(Context context, ArrayList<Helper> list_nl) {
        this.context = context;
        this.list_nl = list_nl;

    }

    @NonNull
    @Override
    public Adapter_NoLimit.LViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false);
        return new LViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_NoLimit.LViewHolder holder, int position) {

        //final Helper temp = list_nl.get(position);

        String fname_txt = list_nl.get(position).getFname();
        String catg_txt = list_nl.get(position).getSpin();
        String prof_img = list_nl.get(position).getProfile_url();

        holder.setUsers(fname_txt, catg_txt, prof_img);

        holder.f_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, UserProfile_VV.class);
                i.putExtra("full name", fname_txt);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_nl.size();
    }



    public class LViewHolder extends RecyclerView.ViewHolder{

        private TextView f_name, catG;
        private ImageView profile_pic;

        public LViewHolder(@NonNull View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.username_view);
            catG = itemView.findViewById(R.id.category_view);
            profile_pic = itemView.findViewById(R.id.userimage);


        }

        public void setUsers(String fname_txt, String catg_txt, String prof_img) {
            f_name.setText(fname_txt);
            catG.setText(catg_txt);
            Picasso.get().load(prof_img).fit().into(profile_pic);
        }


    }
}
