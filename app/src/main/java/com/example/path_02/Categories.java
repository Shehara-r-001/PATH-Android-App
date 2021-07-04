package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.catogaries);

        TextView arts_n_crafts = (TextView) findViewById(R.id.catg_AnT);
        arts_n_crafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ant_act = new Intent(Categories.this, Creators.class);
                startActivity(ant_act);
            }
        });
        TextView bty = (TextView) findViewById(R.id.catg_Beauty);
        bty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bty_act = new Intent(Categories.this, Creators.class);
                startActivity(bty_act);
            }
        });
        TextView decos = (TextView) findViewById(R.id.catg_Deco);
        decos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deco_act = new Intent(Categories.this, Creators.class);
                startActivity(deco_act);
            }
        });
        TextView foods = (TextView) findViewById(R.id.catg_food);
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent food_act = new Intent(Categories.this, Creators.class);
                startActivity(food_act);
            }
        });

    }
}
