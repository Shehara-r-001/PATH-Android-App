package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Admin_Profile extends AppCompatActivity {

    TextView requests, images, user_name, logout;
    ImageView profile;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;
    String user_id, admin_name, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_profile);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();
        user_id = auth.getCurrentUser().getUid();

        requests = findViewById(R.id.pending_req_admin);
        images = findViewById(R.id.pending_img_admin);
        user_name = findViewById(R.id.admin_name);
        profile = findViewById(R.id.admin_profile);
        logout = findViewById(R.id.logout_admin);

        user_reference.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                admin_name = snapshot.child("fname").getValue().toString();
                url = snapshot.child("profile_url").getValue().toString();

                user_name.setText(admin_name);
                //Picasso.get().load(url).fit().into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load the data", Toast.LENGTH_LONG).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Admin_Profile.this, Login.class));
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_Profile.this, Request_List.class));
            }
        });

    }
}