package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Request extends AppCompatActivity {

    TextInputLayout fullname, mobile, message;
    TextView send;
    TextView cancel;
    SimpleDateFormat time;

    String f_name, m_num, msg, uid, sender_fullname;
    ProgressBar progressBar;

    DatabaseReference reference, user_reference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.request);

        fullname = findViewById(R.id.name_field_v_act);
        mobile = findViewById(R.id.mobile_field_v_act);
        message = findViewById(R.id.message_field_v_act);

        send = findViewById(R.id.send_req_act);
        cancel = findViewById(R.id.cancel_req_act);

        reference = FirebaseDatabase.getInstance().getReference();
        user_reference = reference.child("users");
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();



            send.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

                    if (!validate_name() || !validate_mobile() || !validate_msg()) {
                        return;
                    } else {
                        //progressBar.setVisibility(View.VISIBLE);

                        f_name = fullname.getEditText().getText().toString();
                        m_num = mobile.getEditText().getText().toString();
                        msg = message.getEditText().getText().toString();
                        //sender_auth_id = auth.getCurrentUser().getUid();


                        String creator = getIntent().getStringExtra("creator");

                        time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();


//                        user_reference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                if (snapshot.getKey().equals(uid)){
//                                    sender_fullname = snapshot.child("fname").getValue().toString();
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });


                                        final String key = reference.push().getKey();

                                        Map<String, Object> update_request = new HashMap<String, Object>();
                                        update_request.put("name", f_name);
                                        update_request.put("contact", m_num);
                                        update_request.put("message", msg);
                                        update_request.put("receiver", creator);
                                        update_request.put("time", time.format(date));

                                        user_reference.child(uid).child("requests").child("to").child(key).updateChildren(update_request).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(), "Request has been sent", Toast.LENGTH_LONG).show();



                                                //progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(Request.this, UserProfile_VV.class));
                                                finish();
                                            }
                                        });

                    }
                }
            });

    }

    private boolean validate_msg() {

        String msg_by = message.getEditText().getText().toString();

        if( msg_by.length() != 0 && msg_by.length()>149){
            message.setError("Message is too long");
            return false;
        }
        else{
            message.setError(null);
            message.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validate_mobile() {

        String pnum_by = mobile.getEditText().getText().toString();
        String phone_pattern =  "^" + "(7|0(?:\\+94))" + "([0-9]{9,10})" + "$";

        if(pnum_by.isEmpty()){
            mobile.setError("Field cannot be empty");
            return false;
        }else if(pnum_by.matches(phone_pattern)){
            mobile.setError("Invalid phone number");
            return false;
        }
        else{
            mobile.setError(null);
            mobile.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validate_name() {

        String fname_by = fullname.getEditText().getText().toString();

        if(fname_by.isEmpty()){
            fullname.setError("Field cannot be empty");
            return false;
        }else{
            fullname.setError(null);
            fullname.setErrorEnabled(false);   //to remove the space of the error
            return true;
        }

    }
}