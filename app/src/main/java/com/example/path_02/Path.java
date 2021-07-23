package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Path extends AppCompatActivity {

    ArrayList <Model> arrayList;
    RecyclerView recyclerView;
    PATH_Adapter path_adapter;
    FirebaseAuth auth;
    DatabaseReference reference, path_reference;
    String user_id;
    final static String TAG = "TAG";


    @Override
    protected void onStart() {
        super.onStart();
        if( auth.getCurrentUser() != null){
            user_id = auth.getCurrentUser().getUid();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.path_recycler);





        reference = FirebaseDatabase.getInstance().getReference();
        path_reference = reference.child("PATH");
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_path);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        path_adapter = new PATH_Adapter(this, arrayList);
        recyclerView.setAdapter(path_adapter);

        path_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                Model model = snapshot.getChildren().iterator().next().getValue(Model.class);
//                arrayList.add(model);

                for( DataSnapshot key : snapshot.child(user_id).getChildren()){
                    //String pushed_key = snapshot.getKey();


                    Model model = key.getValue(Model.class);
                    arrayList.add(model);

                    path_adapter.notifyDataSetChanged();
                }
//                String pushed_key = reference.push().getKey();
//                Model model = pushed_key.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.v(TAG, error.getMessage());
            }
        });


        Button add_path = (Button) findViewById(R.id.add_new_item_path);
        add_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Path.this, AddPath.class));

            }
        });

    }
}
