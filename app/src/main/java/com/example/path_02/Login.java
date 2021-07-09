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

    private TextInputLayout user_name, password;
    private Button login_btn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        auth = FirebaseAuth.getInstance();
//        if(auth.getCurrentUser() != null){
//            startActivity(new Intent(Login.this, User_Profile.class));
//        }
        setContentView(R.layout.activity_login);

        user_name = findViewById(R.id.username_login);
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

                if(!validateUname() || !validatePword()) {
                    return;

                }else{
                    user_Existance();

                }


            }
        });

    }
    private void user_Existance(){

        final String entered_uname = user_name.getEditText().getText().toString().trim();
        final String entered_pword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query user_check = reference.orderByChild("uname").equalTo(entered_uname);
        user_check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                if(snapshot.exists()){

                    user_name.setError(null);
                    user_name.setErrorEnabled(false);

                    String passwordDB = snapshot.child(entered_uname).child("pword").getValue(String.class);

                    if(passwordDB.equals(entered_pword)){

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String fnameDB = snapshot.child(entered_uname).child("fname").getValue(String.class);
                        String unameDB = snapshot.child(entered_uname).child("uname").getValue(String.class);
                        String emailDB = snapshot.child(entered_uname).child("email").getValue(String.class);
                        String pnumDB = snapshot.child(entered_uname).child("pnumber").getValue(String.class);
                        String spinDB = snapshot.child(entered_uname).child("spin").getValue(String.class);

                        Intent intent = new Intent(Login.this, User_Profile.class);

                        intent.putExtra("fname", fnameDB);
                        intent.putExtra("uname", unameDB);
                        intent.putExtra("email", emailDB);
                        intent.putExtra("pword", passwordDB);
                        intent.putExtra("pnumber", pnumDB);
                        intent.putExtra("spin", spinDB);
                        startActivity(intent);
                    }
                    else{
                        password.setError("Wrong password..!");
                        password.requestFocus();
                    }
                }
                else{
                    user_name.setError("User does not exist..!");
                    user_name.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {

            }
        });
    }

    private Boolean validateUname(){

        String uname_by = user_name.getEditText().getText().toString();

        if(uname_by.isEmpty()){
            user_name.setError("Field cannot be empty");
            return false;
        }
        else{
            user_name.setError(null);
            user_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePword(){

        String pword_by = password.getEditText().getText().toString();

        if(pword_by.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}



