package com.example.path_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Warning_Adapter extends RecyclerView.Adapter<Warning_Adapter.WViewHolder> {

    Context context;
    ArrayList<Warning_Handler> w_handler;

    public Warning_Adapter(Context context, ArrayList<Warning_Handler> w_handler) {
        this.context = context;
        this.w_handler = w_handler;
    }

    @NonNull
    @Override
    public Warning_Adapter.WViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.warning_list_item, parent, false);
        return new WViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Warning_Adapter.WViewHolder holder, int position) {

        String time = w_handler.get(position).getTime();
        String message = w_handler.get(position).getMessage();

        String display_msg = message + "\n" + time + "\n" + "- ADMIN panel";

        holder.setWarn(display_msg);
    }

    @Override
    public int getItemCount() {
        return w_handler.size();
    }

    public class WViewHolder extends RecyclerView.ViewHolder{

        private TextView warn_text;

        public WViewHolder(@NonNull View itemView) {
            super(itemView);

            warn_text = itemView.findViewById(R.id.warning_text);
        }

        public void setWarn(String display_message){
            warn_text.setText(display_message);
        }
    }
}
