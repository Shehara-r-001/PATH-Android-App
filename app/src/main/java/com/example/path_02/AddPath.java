package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddPath extends AppCompatActivity {

    TextInputLayout milestone;
    DatePicker picker;
    Button add_new;
    FirebaseDatabase root;
    DatabaseReference reference, path_ref;
    FirebaseAuth auth;
    String uid;
    private static final String TAG = "TAG";

    @Override
    protected void onStart() {
        super.onStart();
        if ( auth.getCurrentUser().getUid() != null){
            uid = auth.getCurrentUser().getUid();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.addpath_act);



        root = FirebaseDatabase.getInstance();
        reference = root.getReference("users");
        path_ref = root.getReference("PATH");
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();


        add_new = (Button) findViewById(R.id.done_addTOpath);
        picker = (DatePicker) findViewById(R.id.date_picker);
        milestone = findViewById(R.id.milestone_text);

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !Validate_text() ){
                    return;
                }
                else{
                    //Toast.makeText(getApplicationContext(), picker.getDayOfMonth() + "-" + picker.getMonth() + "-" + picker.getYear(), Toast.LENGTH_SHORT).show();
                // redirect to path

                    String ach = milestone.getEditText().getText().toString().trim();
                    int day = picker.getDayOfMonth();
                    int month = picker.getMonth();
                    int year = picker.getYear();
                    String date = day + "/" + month + "/" + year ;

                    path_ref.child(uid).child("PATH").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                                Map<String, Object> updates = new HashMap<String, Object>();
                                updates.put("Achievement ", ach);
                                updates.put("Date ", date);

                                final String key = root.getReference().push().getKey();

                                path_ref.child(uid).child(key).updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(), "Your achievement has been updated.", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(AddPath.this, Path.class));
                                    }
                                });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.v(TAG, error.getMessage());
                        }
                    });
                }

            }
        });

    }

    private boolean Validate_text() {

        String m_text = milestone.getEditText().getText().toString();
        //String m_pattern = "^" + "((?=[A-Za-z0-9@])(?![_\\\\-]).)*" + "$";

        if (m_text.isEmpty()){
            milestone.setError("Field Cannot be empty");
            return false;
        }
        else if( m_text.length()> 350){
            milestone.setError("Text should be less than 350 characters");
            return false;
        }
        else{
            milestone.setError(null);
            milestone.setErrorEnabled(false);
            return true;
        }

    }
}

//if(fname_by.isEmpty()){
//        reg_Fname.setError("Field cannot be empty");
//        return false;
//        }else{
//        reg_Fname.setError(null);
//        reg_Fname.setErrorEnabled(false);   //to remove the space of the error
//        return true;
//        }
