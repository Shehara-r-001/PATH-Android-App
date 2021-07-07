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

public class Login extends AppCompatActivity {

    private TextInputLayout email, password;
    private Button login_btn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, User_Profile.class));
        }
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
//                Intent next_act = new Intent(Login.this, User_Profile.class);
//                startActivity(next_act);
                String eMail = email.getEditText().toString();
                final String passWord = password.getEditText().toString();

                if(TextUtils.isEmpty(eMail)){
                    Toast.makeText(getApplicationContext(), "Please enter your email address..!", Toast.LENGTH_LONG).show();
                    return;
                }if(TextUtils.isEmpty(passWord)){
                    Toast.makeText(getApplicationContext(), "Please enter your password..!", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(eMail, passWord).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull /*@org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful()){
                            if(passWord.length()<6){
                                Toast.makeText(getApplicationContext(), "Your Password is too short..", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Authentication failed. Check your email and password and try again", Toast.LENGTH_LONG).show();

                            }

                        }
                        else{
                            Intent next_act = new Intent(Login.this, User_Profile.class);
                            startActivity(next_act);
                            finish();
                        }
                    }
                });
            }
        });


    }

}
