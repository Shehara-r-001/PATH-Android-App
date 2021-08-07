package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Image_Pending extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Helper> arrayListH;
    Adapter_NoLimit adapter_noLimit;
    DatabaseReference reference, user_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_pending);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");

        recyclerView = findViewById(R.id.images_pending_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayListH = new ArrayList<>();
        adapter_noLimit = new Adapter_NoLimit(this, arrayListH);
        recyclerView.setAdapter(adapter_noLimit);


        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){

                    DataSnapshot dsI = ds.child("images").child("pending") ;

                    if ( dsI.exists()){

                        //String uid = ds.getKey();

                            Helper helper = ds.getValue(Helper.class);
                            arrayListH.add(helper);
                            Log.d("============", ds.getKey());

                            adapter_noLimit.notifyDataSetChanged();

                    }
                    else{
                        Log.d("------------------", ds.getKey());
                        Toast.makeText(getApplicationContext(), "No pending images", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ooooooooooooooooo", error.getMessage());
            }
        });

    }
}