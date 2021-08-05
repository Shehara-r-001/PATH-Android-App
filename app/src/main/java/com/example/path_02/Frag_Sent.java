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


public class Frag_Sent extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Helper_Sent> sentArrayList;
    Adapter_Sent adapter_sent;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.frag__sent, container, false);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        recyclerView = v.findViewById(R.id.sent_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sentArrayList = new ArrayList<>();
        adapter_sent = new Adapter_Sent(getContext(), sentArrayList);
        recyclerView.setAdapter(adapter_sent);

        user_reference.child(auth.getCurrentUser().getUid()).child("requests").child("sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){
                    Helper_Sent helper_sent = ds.getValue(Helper_Sent.class);
                    sentArrayList.add(helper_sent);

                    adapter_sent.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}