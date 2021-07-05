package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class User_Register extends AppCompatActivity {

    TextInputLayout reg_Fname, reg_Uname, reg_email, reg_Pnum, reg_pword, reg_Rpword;
    Button reg_btn, to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);

        reg_Fname = findViewById(R.id.signup_fullname);
        reg_Uname = findViewById(R.id.signup_username);
        reg_email = findViewById(R.id.signup_email);
        reg_Pnum = findViewById(R.id.signup_contact);
        reg_pword = findViewById(R.id.signup_password);
        reg_Rpword = findViewById(R.id.signup_retype_password);

        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent next_act = new Intent(User_Register.this, User_Profile.class);
//                startActivity(next_act);
            }
        });
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

    }
}
