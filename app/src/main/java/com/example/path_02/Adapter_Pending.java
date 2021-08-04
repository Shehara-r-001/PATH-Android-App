package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Pending extends RecyclerView.Adapter<Adapter_Pending.PEViewHolder> {

    Context context;
    ArrayList<Req_Handler> arrayList;

    public Adapter_Pending(Context context, ArrayList<Req_Handler> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Adapter_Pending.PEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.to_list, parent, false);
        return new PEViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Pending.PEViewHolder holder, int position) {

        String receiver = arrayList.get(position).getReceiver();
        String date = arrayList.get(position).getTime();

        holder.setdata( receiver, date);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PEViewHolder extends RecyclerView.ViewHolder{

        private final TextView recei, time;

        public PEViewHolder(@NonNull View itemView) {
            super(itemView);

            recei = itemView.findViewById(R.id.receiver_name_list);
            time = itemView.findViewById(R.id.sent_time_list);
        }

        public void setdata( String receiver, String date){
            recei.setText(receiver);
            time.setText(date);
        }
    }
}
