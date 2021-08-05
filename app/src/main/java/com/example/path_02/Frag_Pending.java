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


public class Frag_Pending extends Fragment {

    ArrayList<Req_Handler> arrayList;
    RecyclerView recyclerView;
    Adapter_Pending adapter_pending;
    FirebaseAuth auth;
    DatabaseReference reference, user_reference;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag__pending, container, false);

        recyclerView = v.findViewById(R.id.pending_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        arrayList = new ArrayList<>();
        adapter_pending = new Adapter_Pending(getContext(), arrayList);
        recyclerView.setAdapter(adapter_pending);

        user_reference.child(auth.getCurrentUser().getUid()).child("requests").child("to").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot ds : snapshot.getChildren()){

                    Req_Handler req_handler = ds.getValue(Req_Handler.class);
                    arrayList.add(req_handler);

                    adapter_pending.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  v;

    }
}