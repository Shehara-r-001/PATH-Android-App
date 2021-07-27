package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

        searchField = findViewById(R.id.search_field);

        list = new ArrayList<>();
        adapter = new Adapter(this, list);
        recyclerView.setAdapter(adapter);

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {

                    Helper helper = key.getValue(Helper.class);
                    list.add(helper);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {

            }
        });


//        try {
//            anC.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    reference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
//                            for (DataSnapshot keyA : snapshot.getChildren()) {
//                                if (keyA.child("spin").getValue().equals("Arts n Crafts")) {
//                                    Helper helper = keyA.getValue(Helper.class);
//                                    list.add(helper);
//                                    startActivity(new Intent(UserList.this, UserList.class));
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull /*@org.jetbrains.annotations.NotNull*/ DatabaseError error) {
//
//                        }
//                    });
//
//                }
//
//            });
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), "Noooooooooooo", Toast.LENGTH_LONG).show();
//        }



            ImageView search = (ImageView) findViewById(R.id.search_img_icon);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String searchTxt = searchField.getText().toString();

                    UserSearch(searchTxt);
                }
            });

            anC = (Button) findViewById(R.id.anc_button);
            bt = (Button) findViewById(R.id.beauty_button);
            dec = (Button) findViewById(R.id.decos_button);
            fd = (Button) findViewById(R.id.foods_button);


        }
        public class UserViewHolder extends RecyclerView.ViewHolder{

            View mView;
            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
            }

            public void setDetails( String fullname, String categ, String prof){
                TextView full_name = (TextView) findViewById(R.id.username_view);
                TextView category = (TextView) findViewById(R.id.category_view);
                ImageView profile = (ImageView) findViewById(R.id.userimage);

                full_name.setText(fullname);
                category.setText(categ);
                Picasso.get().load(prof).fit().into(profile);
            }
        }


    private void UserSearch(String searchTxt) {

        Query searchQuery = reference.orderByChild("spin").startAt(searchTxt).endAt(searchTxt + "\uf8ff");
        

        searchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dat : snapshot.getChildren()) {
                        Helper helper = dat.getValue(Helper.class);

                        list.add(helper);

                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }








}
