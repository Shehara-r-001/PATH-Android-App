package com.example.path_02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        return inflater.inflate(R.layout.frag__pending, container, false);

    }
}