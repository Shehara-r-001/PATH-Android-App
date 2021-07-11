package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class User_Profile extends AppCompatActivity {

    TextView full_name, user_name, category;
    TextInputLayout email, phone_num, pass, user_name2;
    DatabaseReference reference;

    String userNAME, eMAIL, pWORD, pNUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_profile_userview);

        reference = FirebaseDatabase.getInstance().getReference("users");

        full_name = findViewById(R.id.prof_page_fullname);
        user_name = findViewById(R.id.prof_page_uname);
        user_name2 = findViewById(R.id.profile_uname_updt);
        email = findViewById(R.id.profile_email);
        phone_num = findViewById(R.id.profile_phone);
        category = findViewById(R.id.profile_catg);
        pass = findViewById(R.id.profile_pass);


        ImportData();

        TextView to_path = (TextView) findViewById(R.id.path_prof);
        to_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent path_act = new Intent(User_Profile.this, Path.class);
                startActivity(path_act);
            }
        });
        TextView to_find = (TextView) findViewById(R.id.find_prof);
        to_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent find_act = new Intent(User_Profile.this, Categories.class);
                startActivity(find_act);
            }
        });

        Button updt_info = (Button) findViewById(R.id.profile_update);
        updt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                DatabaseReference rootDB = FirebaseDatabase.getInstance().getReference().child("users");
//
//                HashMap hashmap = new HashMap();
//                hashmap.put("uname", user_name2);
//                hashmap.put("email", eMAIL);
//                hashmap.put("pword", pWORD);


                if( AltUsername() || AltEmail() || AltPnum() || AltPword()) {
                    Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_LONG).show();
//                    rootDB.child(userNAME).updateChildren(hashmap).addOnSuccessListener(new OnSuccessListener() {
//                        @Override
//                        public void onSuccess(Object o) {
//                            Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_LONG).show();
//                        }
//                    });
                }



            }
        });

        TextView update_btn = (TextView) findViewById(R.id.update_prof);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView add_img = (TextView) findViewById(R.id.add_prof);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    private void ImportData(){
        Intent intent = getIntent();
        String fullNAME = intent.getStringExtra("fname");
        String userNAME = intent.getStringExtra("uname");
        String eMAIL = intent.getStringExtra("email");
        String pNUM = intent.getStringExtra("pnumber");
        String catG = intent.getStringExtra("spin");
        String passW = intent.getStringExtra("pword");

        full_name.setText(fullNAME);
        user_name.setText(userNAME);
        user_name2.getEditText().setText(userNAME);
        email.getEditText().setText(eMAIL);
        phone_num.getEditText().setText(pNUM);
        category.setText(catG);
        pass.getEditText().setText(passW);

    }



    private boolean AltUsername(){
        String noSpaces = "(?=\\s+$)";

        if((!userNAME.equals(user_name2.getEditText().getText().toString())) && (userNAME.matches(noSpaces))){


            reference.child(userNAME).child("uname").setValue(user_name2.getEditText().getText().toString());
            userNAME = user_name2.getEditText().getText().toString();
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid username..!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean AltEmail(){

        String email_pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";    // A-Z, a-z, 0-9, ., -, _ and after @ a-z, .


        if((!eMAIL.equals(email.getEditText().getText().toString())) && (eMAIL.matches(email_pattern))){
            reference.child(userNAME).child("email").setValue(email.getEditText().getText().toString());
            eMAIL = email.getEditText().getText().toString();
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid email format..!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean AltPnum(){

        String phone_pattern =  "^" + "(7|0(?:\\+94))" + "([0-9]{9,10})" + "$";

        if((!pNUM.equals(phone_num.getEditText().getText().toString())) && (pNUM.matches(phone_pattern))){
            reference.child(userNAME).child("pnumber").setValue(phone_num.getEditText().getText().toString());
            pNUM = phone_num.getEditText().getText().toString();
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid phone number..!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean AltPword(){

        String password_pattern = "^" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*\\s+$)" + ".{6,}" +"$";

        if((!pWORD.equals(pass.getEditText().getText().toString())) && (pWORD.matches(password_pattern))){
            reference.child(userNAME).child("pword").setValue(pass.getEditText().getText().toString());
            pWORD = pass.getEditText().getText().toString();
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "New password is too weak..!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
