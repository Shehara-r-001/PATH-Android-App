package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PATH_vv extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> arrayList;

    DatabaseReference reference, user_ref;
    PATH_Adapter path_adapter;

    TextView auther_name;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_vv);

        reference = FirebaseDatabase.getInstance().getReference();
        user_ref = reference.child("users");

        recyclerView = findViewById(R.id.recycler_path_vv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        path_adapter = new PATH_Adapter(this, arrayList);
        recyclerView.setAdapter(path_adapter);

        auther_name = findViewById(R.id.name_author_path);
        profile = findViewById(R.id.userimage_pvv);


        String user_name = getIntent().getStringExtra("name_of");

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){
                    if ( ds.child("fname").getValue().toString().equals(user_name)){

                        for ( DataSnapshot path : ds.child("PATH").getChildren()){

                            Model model = path.getValue(Model.class);
                            arrayList.add(model);

                            path_adapter.notifyDataSetChanged();

                            String a_name = ds.child("fname").getValue().toString();
                            auther_name.setText(a_name);

                            String a_url = ds.child("profile_url").getValue().toString();
                            Picasso.get().load(a_url).into(profile);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Loading failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}