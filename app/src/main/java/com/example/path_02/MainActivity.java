package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView login_btn = (TextView) findViewById(R.id.login_btn_in_main);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_act = new Intent(MainActivity.this, Login.class);
                startActivity(login_act);
            }
        });
        TextView reg_btn = (TextView) findViewById(R.id.reg_btn_in_main);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_act = new Intent(MainActivity.this, User_Register.class);
                startActivity(reg_act);
            }
        });
        TextView to_catg = (TextView) findViewById(R.id.visit_btn);
        to_catg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent catg_act = new Intent(MainActivity.this, UserList.class); //change
                startActivity(catg_act);
            }
        });

    }
}