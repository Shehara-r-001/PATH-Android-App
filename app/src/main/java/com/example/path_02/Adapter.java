package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<Helper> list;

    //String prof_img;

    public Adapter(Context context, ArrayList<Helper> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull /*@org.jetbrains.annotations.NotNull*/ ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemlist, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull /*@org.jetbrains.annotations.NotNull*/ Adapter.MyViewHolder holder, int position) {

        String fname_txt = list.get(position).getFname();
        String catg_txt = list.get(position).getSpin();
        String prof_img = list.get(position).getProfile_url();


        holder.setData(fname_txt, catg_txt , prof_img);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Clickedddddd", Toast.LENGTH_LONG).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView f_name, catG;
        private ImageView profile_pic;

        public MyViewHolder(@NonNull /*@org.jetbrains.annotations.NotNull*/ View itemView) {
            super(itemView);

            f_name = itemView.findViewById(R.id.username_view);
            catG = itemView.findViewById(R.id.category_view);
            profile_pic = itemView.findViewById(R.id.userimage);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "Önclick.....", Toast.LENGTH_LONG).show();
//                }
//            });

        }

        public void setData( String fname_txt, String catg_txt , String prof_img) {
            f_name.setText(fname_txt);
            catG.setText(catg_txt);
            Picasso.get().load(prof_img).fit().into(profile_pic);
           // profile_pic.setImageResource(prof_img);

        }

    }
}
