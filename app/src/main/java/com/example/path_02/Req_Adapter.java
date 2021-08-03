package com.example.path_02;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Req_Adapter extends RecyclerView.Adapter<Req_Adapter.RViewHolder> {

    Context context;
    ArrayList<Req_Handler> list;
    FirebaseAuth auth;

    public Req_Adapter(Context context, ArrayList<Req_Handler> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Req_Adapter.RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.request_list, parent, false);
        return  new RViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Req_Adapter.RViewHolder holder, int position) {

        auth = FirebaseAuth.getInstance();
        //String uid_sender = auth.getCurrentUser().getUid();

        String name_sender = list.get(position).getName();
        String contact_sender = list.get(position).getContact();
        String name_receiver = list.get(position).getReceiver();
        String msg = list.get(position).getMessage();
        String time = list.get(position).getTime();

        holder.set_req( name_sender, contact_sender, name_receiver, msg, time);

        holder.name_of_sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Req_view.class);

                i.putExtra("sender name", name_sender);
                i.putExtra("sender mobile", contact_sender);
                i.putExtra("receiver name", name_receiver);
                i.putExtra("message", msg);
                i.putExtra("time sent", time);
                //i.putExtra("sender_auth_id", uid_sender);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RViewHolder extends RecyclerView.ViewHolder{

        private final TextView name_of_sender, contact_of_sender, time_sent, name_of_rec, msg_brief;

        public RViewHolder(@NonNull View itemView) {
            super(itemView);

            name_of_sender = itemView.findViewById(R.id.sender_name);
            contact_of_sender = itemView.findViewById(R.id.contact);
            time_sent = itemView.findViewById(R.id.sent_time);
            name_of_rec = itemView.findViewById(R.id.reciever);
            msg_brief = itemView.findViewById(R.id.sent_msg);

        }

        public void set_req(String name_sender, String contact_sender, String name_receiver, String msg, String time){

            name_of_sender.setText(name_sender);
            contact_of_sender.setText(contact_sender);
            time_sent.setText(time);
            name_of_rec.setText(name_receiver);
            msg_brief.setText(msg);
        }
    }
}
