package com.example.path_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile_VV extends AppCompatActivity {

    TextView find, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_vv);

        find = findViewById(R.id.find_prof_vv);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile_VV.this, UserList.class));
            }
        });

        path = findViewById(R.id.path_prof_vv);
        path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //path of the respective user
            }
        });

    }
}