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


public class Frag_Received extends Fragment {

    ArrayList<Helper_Recei> helper_receiArrayList;
    Adapter_Recei adapter_recei;
    RecyclerView recyclerView;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag__received, container, false);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        recyclerView = v.findViewById(R.id.received_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        helper_receiArrayList = new ArrayList<>();
        adapter_recei = new Adapter_Recei(getContext(), helper_receiArrayList);
        recyclerView.setAdapter(adapter_recei);

        user_reference.child(auth.getCurrentUser().getUid()).child("requests").child("from").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){

                    Helper_Recei helper_recei = ds.getValue(Helper_Recei.class);
                    helper_receiArrayList.add(helper_recei);

                    adapter_recei.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  v;
    }
}