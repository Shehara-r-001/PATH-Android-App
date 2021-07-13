package com.example.path_02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

public class User_Profile extends AppCompatActivity {

    TextView full_name, user_name, category;
    //TextInputLayout email, phone_num, pass;
    TextView user_name2, email, phone_num, pass;
    ImageView profPic;
    ProgressBar progressBar;
    DatabaseReference reference;
    StorageReference storageReference;
    FirebaseAuth fAUTH;


    String userNAME, eMAIL, pWORD, pNUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_userview);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("email");
        storageReference = FirebaseStorage.getInstance().getReference();



        StorageReference profref = storageReference.child("users/"+ phone_num +"/profile.jpg");
        profref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {           // to load directly without delay after restart
                Picasso.get().load(uri).into(profPic);
            }
        });

        full_name = findViewById(R.id.prof_page_fullname);
        user_name = findViewById(R.id.prof_page_uname);
        user_name2 = findViewById(R.id.view_ph_uname);
        email = findViewById(R.id.view_ph_email);
        phone_num = findViewById(R.id.view_ph_pnumber);
        category = findViewById(R.id.profile_catg);
        pass = findViewById(R.id.view_ph_pword);
        profPic = findViewById(R.id.user_prof_image);


        ImportData();

        TextView to_path = (TextView) findViewById(R.id.path_prof);
        to_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent path_act = new Intent(User_Profile.this, Path.class);
                startActivity(path_act);
            }
        });
        TextView to_find = (TextView) findViewById(R.id.find_prof);
        to_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent find_act = new Intent(User_Profile.this, Categories.class);
                startActivity(find_act);
            }
        });



        TextView change_prof = (TextView) findViewById(R.id.update_prof);
        change_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);

            }
        });
        TextView add_img = (TextView) findViewById(R.id.add_prof);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(User_Profile.this, Login.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable /*@org.jetbrains.annotations.Nullable*/ Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri image_uri = data.getData();
                //profPic.setImageURI(image_uri);

                UploadProfPic(image_uri);

            }
        }
    }

    private void UploadProfPic(Uri image_uri) {




        StorageReference strRef = storageReference.child("users/"+ phone_num +"/profile.jpg");
        strRef.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                strRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profPic);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull /*@org.jetbrains.annotations.NotNull*/ Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to update profile picture", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ImportData(){
        Intent intent = getIntent();
        String fullNAME = intent.getStringExtra("fname");
        String userNAME = intent.getStringExtra("uname");
        String eMAIL = intent.getStringExtra("email");
        String pNUM = intent.getStringExtra("pnumber");
        String catG = intent.getStringExtra("spin");
        String passW = intent.getStringExtra("pword");

        full_name.setText(fullNAME);
        user_name.setText(userNAME);
        user_name2.setText(userNAME);
        email.setText(eMAIL);
        phone_num.setText(pNUM);
        category.setText(catG);
        pass.setText(passW);

    }


}
