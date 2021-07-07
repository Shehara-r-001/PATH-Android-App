package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_Register extends AppCompatActivity {

    TextInputLayout reg_Fname, reg_Uname, reg_email, reg_Pnum, reg_pword, reg_Rpword;
    Button reg_btn, to_login;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();
        reg_Fname = findViewById(R.id.signup_fullname);
        reg_Uname = findViewById(R.id.signup_username);
        reg_email = findViewById(R.id.signup_email);
        reg_Pnum = findViewById(R.id.signup_contact);
        reg_pword = findViewById(R.id.signup_password);
        reg_Rpword = findViewById(R.id.signup_retype_password);
        progressBar = findViewById(R.id.progressBar);


        to_login = (Button) findViewById(R.id.to_login_page);
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_actv = new Intent(User_Register.this, Login.class);
                startActivity(next_actv);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.category_spinner); // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
//
//        Spinner spinner01 = (Spinner) findViewById(R.id.category_spinner);
//        spinner01.setOnItemSelectedListener(User_Register.this);


        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent next_act = new Intent(User_Register.this, User_Profile.class);
//                startActivity(next_act);
                String full_name = reg_Fname.getEditText().toString().trim();
                String user_name = reg_Uname.getEditText().toString().trim();
                String email = reg_email.getEditText().toString().trim();
                String password = reg_pword.getEditText().toString().trim();

                if (TextUtils.isEmpty(full_name)) {
                    Toast.makeText(getApplicationContext(), "Please enter your full name..!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(user_name)) {
                    Toast.makeText(getApplicationContext(), "Please enter a user name..!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email address..!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password..!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Your password id too short..!", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(User_Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull /*@org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                        Toast.makeText(User_Register.this, "createUserWithEmail:onComplete" + task.isSuccessful(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(User_Register.this, "Authentication Failed..!" + task.getException(), Toast.LENGTH_LONG).show();

                        } else {
                            startActivity(new Intent(User_Register.this, User_Profile.class));
                            finish();
                        }
                    }
                });

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
