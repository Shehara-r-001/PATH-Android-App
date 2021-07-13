package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private TextInputLayout email, password;
    private Button login_btn;
    private ProgressBar progressBar;
    private FirebaseAuth f_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        f_auth = FirebaseAuth.getInstance();
//        if(auth.getCurrentUser() != null){
//            startActivity(new Intent(Login.this, User_Profile.class));
//        }
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        progressBar = findViewById(R.id.progressBar_login);

        Button register_page = (Button) findViewById(R.id.not_a_user);
        register_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_act = new Intent(Login.this, User_Register.class);
                startActivity(reg_act);
            }
        });


        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateUname() || !validatePword()) {
                    progressBar.setVisibility(View.GONE);
                    return;


                } else {
                    user_Existance();

                }


            }
        });

    }

    private void user_Existance() {

        progressBar.setVisibility(View.VISIBLE);

        String eMail = email.getEditText().getText().toString().trim();
        String pWord = password.getEditText().getText().toString().trim();

        f_auth.signInWithEmailAndPassword(eMail, pWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull /*org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(Login.this, User_Profile.class));

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                    Query user_check = reference.orderByChild("uname");






                }
                else{
                    Toast.makeText(getApplicationContext(), "Error..!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


        private Boolean validateUname () {

            String uname_by = email.getEditText().getText().toString();

            if (uname_by.isEmpty()) {
                email.setError("Field cannot be empty");
                return false;
            } else {
                email.setError(null);
                email.setErrorEnabled(false);
                return true;
            }
        }

        private Boolean validatePword () {

            String pword_by = password.getEditText().getText().toString();

            if (pword_by.isEmpty()) {
                password.setError("Field cannot be empty");
                return false;
            } else {
                password.setError(null);
                password.setErrorEnabled(false);
                return true;
            }

        }
    }




