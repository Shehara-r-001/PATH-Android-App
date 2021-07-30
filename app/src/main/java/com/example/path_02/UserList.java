package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    private EditText searchField;

    RecyclerView recyclerView;
    DatabaseReference reference, user_ref;
    ArrayList<Helper> list;
    Adapter adapter;
    FirebaseAuth auth;
    String uid;

    Button anC, bt, dec, fd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_list);


        recyclerView = findViewById(R.id.User_list);
        reference = FirebaseDatabase.getInstance().getReference();
        user_ref = reference.child("users");

        auth = FirebaseAuth.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //searchField = findViewById(R.id.search_field);

        list = new ArrayList<>();
        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);


        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    if( key.child("spin").getValue().toString().equals("Arts n Crafts")) {

                        Helper helper = key.getValue(Helper.class);
                        list.add(helper);

                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {

            }
        });

        TextView see_all_anc = findViewById(R.id.see_more_anc);
        see_all_anc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserList.this, See_More.class);
                String anc = "Arts n Crafts";
                i.putExtra("type", anc);
                startActivity(i);

            }
        });

        ArrayList<Helper> list_b = new ArrayList<>();
        Adapter adapter_b = new Adapter(this, list_b);
        RecyclerView recycler_b = findViewById(R.id.User_list_beauty);
        recycler_b.setHasFixedSize(true);
        recycler_b.setLayoutManager(new LinearLayoutManager(this));
        recycler_b.setAdapter(adapter_b);

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    if( key.child("spin").getValue().toString().equals("Beauty")) {

                        Helper helper = key.getValue(Helper.class);
                        list_b.add(helper);

                        adapter_b.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {

            }
        });

        TextView see_all_beu = findViewById(R.id.see_more_beauty);
        see_all_beu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserList.this, See_More.class);
                String beauty = "Beauty";
                i.putExtra("type", beauty);
                startActivity(i);

            }
        });


        ArrayList<Helper> list_d = new ArrayList<>();
        Adapter adapter_d = new Adapter(this, list_d);
        RecyclerView recycler_d = findViewById(R.id.User_list_decos);
        recycler_d.setHasFixedSize(true);
        recycler_d.setLayoutManager(new LinearLayoutManager(this));
        recycler_d.setAdapter(adapter_d);

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    if( key.child("spin").getValue().toString().equals("Decorations")) {

                        Helper helper = key.getValue(Helper.class);
                        list_d.add(helper);

                        adapter_d.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {

            }
        });

        TextView see_all_deco = findViewById(R.id.see_more_decos);
        see_all_deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserList.this, See_More.class);
                String deco = "Decorations";
                i.putExtra("type", deco);
                startActivity(i);

            }
        });


        ArrayList<Helper> list_f = new ArrayList<>();
        Adapter adapter_f = new Adapter(this, list_f);
        RecyclerView recycler_f = findViewById(R.id.User_list_decos);
        recycler_f.setHasFixedSize(true);
        recycler_f.setLayoutManager(new LinearLayoutManager(this));
        recycler_f.setAdapter(adapter_f);

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    if( key.child("spin").getValue().toString().equals("Decorations")) {

                        Helper helper = key.getValue(Helper.class);
                        list_f.add(helper);

                        adapter_f.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {

            }
        });

        TextView see_all_food = findViewById(R.id.see_more_food);
        see_all_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserList.this, See_More.class);
                String food = "Food";
                i.putExtra("type", food);
                startActivity(i);

            }
        });



//        anC = (Button) findViewById(R.id.anc_button);
//        bt = (Button) findViewById(R.id.beauty_button);
//        dec = (Button) findViewById(R.id.decos_button);
//        fd = (Button) findViewById(R.id.foods_button);




    }



}
