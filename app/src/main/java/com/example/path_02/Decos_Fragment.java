package com.example.path_02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Decos_Fragment extends Fragment {


    RecyclerView recyclerView;
    DatabaseReference reference, user_ref;
    ArrayList<Helper> list;
    Adapter adapter;
    FirebaseAuth auth;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_decos, container, false);

        recyclerView = v.findViewById(R.id.decos_recycler_frag);
        reference = FirebaseDatabase.getInstance().getReference();
        user_ref = reference.child("users");

        auth = FirebaseAuth.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        list = new ArrayList<>();
        adapter = new Adapter(getContext(), list);
        recyclerView.setAdapter(adapter);


        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull /*@org.jetbrains.annotations.NotNull*/ DataSnapshot snapshot) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    if( key.child("spin").getValue().toString().equals("Decorations")) {

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

        return  v;
    }
}