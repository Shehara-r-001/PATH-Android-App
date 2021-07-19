package com.example.path_02;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPath extends AppCompatActivity {

    TextInputLayout milestone;
    DatePicker picker;
    Button add_new;
    FirebaseDatabase root;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.addpath_act);

        root = FirebaseDatabase.getInstance();
        reference = root.getReference("users");

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
                    Toast.makeText(getApplicationContext(), picker.getDayOfMonth() + "-" + picker.getMonth() + "-" + picker.getYear(), Toast.LENGTH_SHORT).show();
                // redirect to path
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
