package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PATH_Adapter extends RecyclerView.Adapter<PATH_Adapter.PViewHolder> {

    Context context;
    ArrayList<Model> arrayList;

    public PATH_Adapter(Context context, ArrayList<Model> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PATH_Adapter.PViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.path_list, parent, false);
        return new PViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PATH_Adapter.PViewHolder holder, int position) {

        String achie = arrayList.get(position).getAchievement();
        String date = arrayList.get(position).getDate();

        holder.setAch( achie, date);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PViewHolder extends RecyclerView.ViewHolder{

        private TextView achievement, dates;

        public PViewHolder(@NonNull View itemView) {
            super(itemView);

            achievement = itemView.findViewById(R.id.achievement);
            dates = itemView.findViewById(R.id.date);
        }

        public void setAch( String achie, String date){
            achievement.setText(achie);
            dates.setText(date);
        }
    }
}
