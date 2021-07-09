package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class User_Profile extends AppCompatActivity {

    TextView full_name, user_name, category;
    TextInputLayout email, phone_num, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_userview);

        full_name = findViewById(R.id.prof_page_fullname);
        user_name = findViewById(R.id.prof_page_uname);
        email = findViewById(R.id.profile_email);
        phone_num = findViewById(R.id.profile_phone);
        category = findViewById(R.id.profile_catg);
        pass = findViewById(R.id.profile_pass);


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
        TextView update_btn = (TextView) findViewById(R.id.update_prof);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView add_img = (TextView) findViewById(R.id.add_prof);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        email.getEditText().setText(eMAIL);
        phone_num.getEditText().setText(pNUM);
        category.setText(catG);
        pass.getEditText().setText(passW);

    }

}
