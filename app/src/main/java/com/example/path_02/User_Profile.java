package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class User_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_userview);

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
                Intent find_act = new Intent(User_Profile.this, com.example.path.Categories.class);
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
}
