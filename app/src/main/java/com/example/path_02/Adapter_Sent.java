package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Sent extends RecyclerView.Adapter<Adapter_Sent.SEViewHolder> {

    Context context;
    ArrayList<Helper_Sent> sent_list;

    public Adapter_Sent(Context context, ArrayList<Helper_Sent> sent_list) {
        this.context = context;
        this.sent_list = sent_list;
    }

    @NonNull
    @Override
    public Adapter_Sent.SEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.to_list, parent,false);
        return new SEViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Sent.SEViewHolder holder, int position) {

        String sent_to = sent_list.get(position).getTo();
        String sent_date = sent_list.get(position).getDate();

        holder.setSent( sent_to, sent_date);
    }

    @Override
    public int getItemCount() {
        return sent_list.size();
    }

    public class SEViewHolder extends RecyclerView.ViewHolder{

        private final TextView to, time;

        public SEViewHolder(@NonNull View itemView) {
            super(itemView);

            to = itemView.findViewById(R.id.receiver_name_list);
            time = itemView.findViewById(R.id.sent_time_list);
        }

        public void setSent( String sent_to, String sent_date){
            to.setText(sent_to);
            time.setText(sent_date);
        }
    }
}
