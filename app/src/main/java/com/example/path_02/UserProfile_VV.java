package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class UserProfile_VV extends AppCompatActivity {

    TextView find, path, f_name, u_name, u_name_2, email, category;
    ImageView profile;
    Helper helper = null;
    DatabaseReference reference, user_ref;

    String fname, uname, uname2, url, catg, mail;

    RecyclerView recyclerView;
    ArrayList<Model_Image> arrayList;
    Image_Adapter image_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_vv);

        f_name = findViewById(R.id.prof_page_fullname_vv);
        u_name = findViewById(R.id.prof_page_uname_vv);
        u_name_2 = findViewById(R.id.view_ph_uname_vv);
        category = findViewById(R.id.profile_catg_vv);
        email = findViewById(R.id.view_ph_email_vv);
        profile = findViewById(R.id.user_prof_image_vv);

        String name = getIntent().getStringExtra("full name");

        reference = FirebaseDatabase.getInstance().getReference();
        user_ref = reference.child("users");

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot user_key : snapshot.getChildren()){
                    if ( user_key.child("fname").getValue().toString().equals(name)){

                        fname = user_key.child("fname").getValue().toString();
                        uname = user_key.child("uname").getValue().toString();
                        uname2 = user_key.child("uname").getValue().toString();
                        catg = user_key.child("spin").getValue().toString();
                        mail = user_key.child("email").getValue().toString();
                        url = user_key.child("profile_url").getValue().toString();

                        f_name.setText(fname);
                        u_name.setText(uname);
                        u_name_2.setText(uname2);
                        email.setText(mail);
                        category.setText(catg);
                        Picasso.get().load(url).fit().into(profile);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Failed to get the user", Toast.LENGTH_LONG).show();
            }
        });


        recyclerView = findViewById(R.id.image_grid_vv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        arrayList = new ArrayList<>();
        image_adapter = new Image_Adapter(this, arrayList);
        recyclerView.setAdapter(image_adapter);

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot u : snapshot.getChildren()){
                    if ( u.child("fname").getValue().toString().equals(name)){

                        for ( DataSnapshot i : u.child("images").getChildren()){
                            Model_Image model_image = i.getValue(Model_Image.class);
                            arrayList.add(model_image);

                            image_adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        find = findViewById(R.id.find_prof_vv);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile_VV.this, UserList.class));
            }
        });

        path = findViewById(R.id.path_prof_vv);
        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(UserProfile_VV.this, PATH_vv.class);
                in.putExtra("name_of", name);
                startActivity(in);
            }
        });

    }
}