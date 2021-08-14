package com.example.path_02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Add_Image extends AppCompatActivity {

    TextInputLayout caption;
    Button add;
    ImageView slctd_img;
    DatabaseReference reference;
    FirebaseAuth auth;
    StorageReference storageReference;
    String img_URL, key, cap;
    //Object cap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add_image);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        key = UUID.randomUUID().toString();

        slctd_img = findViewById(R.id.selected_image);
        caption = findViewById(R.id.type_caption);

        //cap = caption.getEditText().getText().toString();

        slctd_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });



        add = findViewById(R.id.add_imageNcap);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put("caption", caption.getEditText().getText().toString());
//                String test = "okay";
//                updates.put("test", test);
                reference.child("users").child(auth.getCurrentUser().getUid()).child("images").child(key).updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Updated" ,Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(Add_Image.this, User_Profile.class));
                    }
                });


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1000){
            if( resultCode == Activity.RESULT_OK){
                Uri img_uri = data.getData();

                Upload_Image(img_uri);
            }
        }
    }

    private void Upload_Image( Uri img_uri) {



        final StorageReference s_ref = storageReference.child("users/" + auth.getCurrentUser().getUid() + "/images/" + key);
        s_ref.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image has been uploaded", Toast.LENGTH_LONG).show();

                s_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().into(slctd_img);

                        img_URL = uri.toString();


                        Map<String, Object> update = new HashMap<String, Object>();
                        update.put("images", img_URL);
                        //update.put("caption", cap);
                        reference.child("users").child(auth.getCurrentUser().getUid()).child("images").child("pending").child(key).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Updated" ,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }
}