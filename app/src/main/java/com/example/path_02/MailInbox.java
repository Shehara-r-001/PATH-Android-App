package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MailInbox extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView1, recyclerView2;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;

    ArrayList<Req_Handler> arrayList;
    ArrayList<Helper_Sent> sentArrayList;
    ArrayList<Helper_Recei> receiArrayList;

    Adapter_Pending adapter_pending;
    Adapter_Sent adapter_sent;
    Adapter_Recei adapter_recei;

    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mail_inbox);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.pending_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        adapter_pending = new Adapter_Pending(this, arrayList);
        recyclerView.setAdapter(adapter_pending);

        //uid = auth.getCurrentUser().getUid();

        user_reference.child(auth.getCurrentUser().getUid()).child("requests").child("to").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){

                    Req_Handler req_handler = ds.getValue(Req_Handler.class);
                    arrayList.add(req_handler);

                    adapter_pending.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView1 = findViewById(R.id.sent_list);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        sentArrayList = new ArrayList<>();
        adapter_sent = new Adapter_Sent(this, sentArrayList);
        recyclerView1.setAdapter(adapter_sent);

        user_reference.child(auth.getCurrentUser().getUid()).child("requests").child("sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){
                    Helper_Sent helper_sent = ds.getValue(Helper_Sent.class);
                    sentArrayList.add(helper_sent);

                    adapter_sent.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView2 = findViewById(R.id.received_list);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        receiArrayList = new ArrayList<>();
        adapter_recei = new Adapter_Recei(this, receiArrayList);
        recyclerView2.setAdapter(adapter_recei);

        user_reference.child(auth.getCurrentUser().getUid()).child("requests").child("from").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){

                    Helper_Recei helper_recei = ds.getValue(Helper_Recei.class);
                    receiArrayList.add(helper_recei);

                    adapter_recei.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}