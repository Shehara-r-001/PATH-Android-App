package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Image_Full_Admin extends AppCompatActivity {

    ImageView image_full;
    TextView caption, approve,discard, warning;
    DatabaseReference reference, user_reference;

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
        approve = findViewById(R.id.approve_img_admin_btn);
        discard = findViewById(R.id.discard_img_admin_btn);
        warning = findViewById(R.id.warning_img_admin_btn);

        Picasso.get().load(address).fit().into(image_full);
        caption.setText(caps);
    }
}