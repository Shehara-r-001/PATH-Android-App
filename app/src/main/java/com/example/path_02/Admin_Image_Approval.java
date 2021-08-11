package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_Image_Approval extends AppCompatActivity {

    //Image_Adapter image_adapter;
    Image_Adapter_Admin image_adapter_admin;
    ArrayList<Model_Image> arrayList;
    RecyclerView recyclerView;
    DatabaseReference reference, user_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.admin_image_approval);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");

        recyclerView = findViewById(R.id.image_recycler_admin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        arrayList = new ArrayList<>();
        image_adapter_admin = new Image_Adapter_Admin(this, arrayList);
        recyclerView.setAdapter(image_adapter_admin);

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for( DataSnapshot ds : snapshot.getChildren()){
                     DataSnapshot dss = ds.child("images");

                     for ( DataSnapshot dsn : dss.getChildren()){

                         Model_Image model_image = dsn.getValue(Model_Image.class);
                         arrayList.add(model_image);

                         image_adapter_admin.notifyDataSetChanged();


                     }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}