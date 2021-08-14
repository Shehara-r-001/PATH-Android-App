package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Warning_rec extends AppCompatActivity {

    DatabaseReference reference, user_reference;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    Warning_Adapter warning_adapter;
    ArrayList<Warning_Handler> warning_handlerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_rec);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.warning_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        warning_handlerArrayList = new ArrayList<>();
        warning_adapter = new Warning_Adapter(this, warning_handlerArrayList);
        recyclerView.setAdapter(warning_adapter);

        user_reference.child(auth.getCurrentUser().getUid()).child("warnings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot ds : snapshot.getChildren()){

                    Warning_Handler warning_handler = ds.getValue(Warning_Handler.class);
                    warning_handlerArrayList.add(warning_handler);

                    warning_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}