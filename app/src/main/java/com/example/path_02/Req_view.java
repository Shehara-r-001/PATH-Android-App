package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Req_view extends AppCompatActivity {

    TextView sender, receiver, time, contact, message, approve, discard, notify;
    DatabaseReference reference, user_reference;
    FirebaseAuth auth;
    String uid, senderName, receiverName, senderMobile, msg, timeSent, sender_id, sender_auth, sender_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.req_view);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();

        senderName = getIntent().getStringExtra("sender name");
        receiverName = getIntent().getStringExtra("receiver name");
        senderMobile = getIntent().getStringExtra("sender mobile");
        msg = getIntent().getStringExtra("message");
        timeSent = getIntent().getStringExtra("time sent");
        //sender_auth = getIntent().getStringExtra("sender_auth_id");


        sender = findViewById(R.id.sender_name_admin);
        receiver = findViewById(R.id.receiver_name_admin);
        time = findViewById(R.id.time_sent_admin);
        contact = findViewById(R.id.contact_sender);
        message = findViewById(R.id.sender_msg_admin);

        sender.setText(senderName);
        receiver.setText(receiverName);
        time.setText(timeSent);
        contact.setText(senderMobile);
        message.setText(msg);

        approve = findViewById(R.id.approve_req_admin);
        discard = findViewById(R.id.discard_req_admin);
        notify = findViewById(R.id.notify_sender_admin);

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot ds : snapshot.getChildren()){
                    if( ds.child("fname").getValue().toString().equals(receiverName)){
                        uid = ds.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot rqs : snapshot.getChildren()){
                    if ( rqs.child("fname").getValue().toString().equals(senderName)){
                        sender_uid = rqs.getKey();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = reference.push().getKey();
                Map<String, Object> move = new HashMap<String, Object>();
                move.put("to", receiverName);
                move.put("date", timeSent);
                user_reference.child(sender_uid).child("requests").child("sent").child(num).updateChildren(move).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        user_reference.child(sender_uid).child("requests").child("to").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for ( DataSnapshot dlt: snapshot.getChildren()){
                                    if ( dlt.child("time").getValue().toString().equals(timeSent)){
                                        dlt.getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Toast.makeText(getApplicationContext(), "Data was moved", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> req = new HashMap<String, Object>();
                req.put("from", senderName);
                req.put("contact", senderMobile);
                req.put("message", msg);
                req.put("time", timeSent);

                Log.d("................", uid);

                String key = reference.push().getKey();
                //String key = UUID.randomUUID().toString();
                user_reference.child(uid).child("requests").child("from").child(key).updateChildren(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(getApplicationContext(), "Request has been redirected", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(Req_view.this, Request_List.class));
                        finish();

                    }
                });
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}