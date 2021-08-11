package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Image_Full_Admin extends AppCompatActivity {

    ImageView image_full;
    TextView caption, approve,discard, warning, notify;
    DatabaseReference reference, user_reference;

    String userID, par;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.image_full_admin);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");

        String address = getIntent().getStringExtra("img_url");
        String caps = getIntent().getStringExtra("caps");

        image_full = findViewById(R.id.imageview_admin_fullview);
        caption = findViewById(R.id.caption_admin_fullview);
        notify = findViewById(R.id.notify_img_admin_btn);
        approve = findViewById(R.id.approve_img_admin_btn);
        discard = findViewById(R.id.discard_img_admin_btn);
        warning = findViewById(R.id.warning_img_admin_btn);

        Picasso.get().load(address).fit().into(image_full);
        caption.setText(caps);


        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for ( DataSnapshot ds : snapshot.getChildren()){
                            for ( DataSnapshot dss : ds.child("images").getChildren()){
                                if ( dss.child("images").getValue().toString().equals(address)){

                                    dss.getRef().removeValue();
                                    Toast.makeText(getApplicationContext(), "Click approve to complete approval", Toast.LENGTH_LONG).show();

                                    par = dss.getKey();
                                    userID = ds.getKey();
                                    //Log.d("zzzzzzzzzzzzzzzz", par);
                                    //Log.d("zzzzzzzzzzzzzzzz", userID);

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Map<String, Object> updt = new HashMap<>();
                updt.put("images", address);
                updt.put("caption", caps);

                String some_id = UUID.randomUUID().toString();
                user_reference.child(userID).child("app_images").child(some_id).updateChildren(updt).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Image has been approved", Toast.LENGTH_LONG).show();

                        //user_reference.child("images").child(par).removeValue();
                        startActivity(new Intent(Image_Full_Admin.this, Image_Adapter_Admin.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}