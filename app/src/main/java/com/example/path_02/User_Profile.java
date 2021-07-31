package com.example.path_02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User_Profile extends AppCompatActivity {

    private static final String TAG = "TAG";
    TextView full_name, user_name, category;
    //TextInputLayout email, phone_num, pass;
    TextView user_name2, email, phone_num, pass;
    ImageView profPic;
    ProgressBar progressBar;
    DatabaseReference reference, usrRF;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String email_i, try_url, uid;
    FirebaseUser usr;

    RecyclerView recyclerView;
    ArrayList<Model_Image> arrayList;
    Image_Adapter image_adapter;


    String userNAME, eMAIL, pWORD, pNUM, url, imgRef;

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            ImportData();
        }
        else if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(User_Profile.this, Login.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_userview);

        //FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();
        usrRF = reference.child("users");
        Log.v("USERID", usrRF.getKey());

        storageReference = FirebaseStorage.getInstance().getReference();


        recyclerView = findViewById(R.id.image_grid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        arrayList = new ArrayList<>();
        image_adapter = new Image_Adapter(this, arrayList);
        recyclerView.setAdapter(image_adapter);

        usrRF.child(mAuth.getCurrentUser().getUid()).child("images").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot key : snapshot.getChildren()){
                    Model_Image model_image = key.getValue(Model_Image.class);
                    arrayList.add(model_image);

                    image_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        final StorageReference profref = storageReference.child("users/"+ mAuth.getCurrentUser().getUid() +"/profile.jpg");
        profref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {           // to load directly without delay after restart
                Picasso.get().load(uri).fit().into(profPic);
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


        Intent in = getIntent();
        email_i = in.getStringExtra("email");




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
                Intent find_act = new Intent(User_Profile.this, UserList.class);
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

                startActivity(new Intent(User_Profile.this, Add_Image.class));

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

        ImageView change_dp = (ImageView) findViewById(R.id.user_prof_image);
        change_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable /*@org.jetbrains.annotations.Nullable*/ Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri image_uri = data.getData();
                profPic.setImageURI(image_uri);

                UploadProfPic(image_uri);

            }
        }
    }

    private void UploadProfPic(Uri image_uri) {

//        final StorageReference strRef = storageReference.child("profile_picture").child(fAUTH.getUid());
        final StorageReference strRef = storageReference.child("users/"+ mAuth.getCurrentUser().getUid() +"/profile.jpg");
        strRef.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image has been uploaded", Toast.LENGTH_LONG).show();
                strRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().into(profPic);


                        imgRef = uri.toString();

                        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Map<String, Object> updates = new HashMap<String, Object>();
                        updates.put("profile_url", imgRef);
                        usrRF.child(uid).updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Upload Complete", Toast.LENGTH_LONG).show();

//                                usrRF.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        String u = snapshot.child("profile_url").getValue().toString();
//                                        Picasso.get().load(u).fit().into(profPic);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
                            }
                        });

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

            usrRF.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                //should be usrRF
                String fullName, userName, userName2, eMail, phoneNum, catG, paSS;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    fullName = snapshot.child("fname").getValue().toString();
                    userName = snapshot.child("uname").getValue().toString();
                    userName2 = snapshot.child("uname").getValue().toString();
                    eMail = snapshot.child("email").getValue().toString();
                    phoneNum = snapshot.child("pnumber").getValue().toString();
                    catG = snapshot.child("spin").getValue().toString();
                    paSS = snapshot.child("pword").getValue().toString();

                    //Toast.makeText(getApplicationContext(), fullName, Toast.LENGTH_LONG).show();

                    full_name.setText(fullName);
                    user_name.setText(userName);
                    user_name2.setText(userName2);
                    email.setText(eMail);
                    phone_num.setText(phoneNum);
                    category.setText(catG);
                    pass.setText(paSS);

                    Picasso.get().load(imgRef).fit().into(profPic);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.d(TAG, error.getMessage());
                }
            });

    }


}
