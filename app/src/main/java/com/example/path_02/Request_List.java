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

public class Request_List extends AppCompatActivity {

    ArrayList<Req_Handler> arrayList;
    RecyclerView recyclerView;
    Req_Adapter req_adapter;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.request_recycler);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.req_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        req_adapter = new Req_Adapter(this, arrayList);
        recyclerView.setAdapter(req_adapter);

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){
                    for( DataSnapshot req : ds.child("requests").child("to").getChildren()){

                        Req_Handler req_handler = req.getValue(Req_Handler.class);
                        arrayList.add(req_handler);

                        req_adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}