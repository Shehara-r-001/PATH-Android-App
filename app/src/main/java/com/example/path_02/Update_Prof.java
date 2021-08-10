package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Update_Prof extends AppCompatActivity {    // use a new adapter, id's might give that nulls

    TextInputLayout full_name, user_name, email, new_pword, new_pword_conf;
    Button update, cancel;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;
    String f_name, u_name, e_mail, n_pword, nr_pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_prof);

        full_name = findViewById(R.id.update_fullname);
        user_name = findViewById(R.id.update_username);
        email = findViewById(R.id.update_email);
        new_pword = findViewById(R.id.update_password);
        new_pword_conf = findViewById(R.id.update_retype_password);

        f_name = full_name.getEditText().getText().toString();
        u_name = user_name.getEditText().getText().toString();
        e_mail = email.getEditText().getText().toString();
        n_pword = new_pword.getEditText().getText().toString();
        nr_pword = new_pword_conf.getEditText().getText().toString();

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

//        String user_e_fname = getIntent().getStringExtra("existing full name");
//        String user_e_uname = getIntent().getStringExtra("existing user name");
//        String user_e_email = getIntent().getStringExtra("existing email");
//        String user_e_pass = getIntent().getStringExtra("existing password");
//
//        full_name.getEditText().setText(user_e_fname);
//        user_name.getEditText().setText(user_e_uname);
//        email.getEditText().setText(user_e_email);
//        new_pword.getEditText().setText(user_e_pass);
//        new_pword_conf.getEditText().setText(user_e_pass);

        user_reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String e_fname = snapshot.child("fname").getValue().toString();
                String e_uname = snapshot.child("uname").getValue().toString();
                String e_email = snapshot.child("email").getValue().toString();
                String e_pword = snapshot.child("pword").getValue().toString();

                full_name.getEditText().setText(e_fname);
                user_name.getEditText().setText(e_uname);
                email.getEditText().setText(e_email);
                new_pword.getEditText().setText(e_pword);
                new_pword_conf.getEditText().setText(e_pword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update = findViewById(R.id.update_btn_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> update_info = new HashMap<>();
                update_info.put("fname", f_name);
                update_info.put("uname", u_name);
                update_info.put("email", e_mail);
                update_info.put("pword", n_pword);
                update_info.put("rePword", nr_pword);

                user_reference.child(auth.getCurrentUser().getUid()).updateChildren(update_info).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Information updated successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Update_Prof.this, User_Profile.class));
                        finish();
                    }
                });
            }
        });

        cancel = findViewById(R.id.cancel_btn_update);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Update_Prof.this, User_Profile.class));
                finish();
            }
        });



    }
}