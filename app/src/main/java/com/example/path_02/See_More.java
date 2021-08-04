package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class See_More extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference, user_ref;
    ArrayList<Helper> arrList;
    Adapter_NoLimit adapterNL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.see_more);

        reference = FirebaseDatabase.getInstance().getReference();
        user_ref = reference.child("users");

        recyclerView = findViewById(R.id.recycler_see_more);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrList = new ArrayList<>();
        adapterNL = new Adapter_NoLimit(this, arrList);
        recyclerView.setAdapter(adapterNL);

        String type = getIntent().getStringExtra("type");




        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for ( DataSnapshot key : snapshot.getChildren()){
                     if ( key.child("spin").getValue().toString().equals(type)){

                         Helper helper = key.getValue(Helper.class);
                         arrList.add(helper);

                         adapterNL.notifyDataSetChanged();
                     }
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}