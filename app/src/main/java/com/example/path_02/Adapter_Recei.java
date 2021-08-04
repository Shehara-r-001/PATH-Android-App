package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Recei extends RecyclerView.Adapter<Adapter_Recei.ReViewHolder> {

    Context context;
    ArrayList<Helper_Recei> arrayList;

    public Adapter_Recei(Context context, ArrayList<Helper_Recei> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Adapter_Recei.ReViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.received_list, parent, false);
        return new ReViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Recei.ReViewHolder holder, int position) {

        String sender = arrayList.get(position).getFrom();
        String contact = arrayList.get(position).getContact();
        String time = arrayList.get(position).getTime();
        String message = arrayList.get(position).getMessage();

        holder.setData( sender, contact, time, message);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ReViewHolder extends RecyclerView.ViewHolder{

        private final TextView sender_name, sender_contact, sent_time, sent_msg;

        public ReViewHolder(@NonNull View itemView) {
            super(itemView);

            sender_name = itemView.findViewById(R.id.sender_name_list);
            sender_contact = itemView.findViewById(R.id.sender_contact_re);
            sent_time = itemView.findViewById(R.id.sent_from_time_list);
            sent_msg = itemView.findViewById(R.id.sender_message);

        }
        public void setData( String sender, String contact, String time, String message){

            sender_name.setText(sender);
            sender_contact.setText(contact);
            sent_time.setText(time);
            sent_msg.setText(message);
        }
    }
}
