package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Register extends AppCompatActivity {

    TextInputLayout reg_Fname, reg_Uname, reg_email, reg_Pnum, reg_pword, reg_Rpword;
    Button reg_btn, to_login;
    ProgressBar progressBar;
    Spinner spinner;
    //FirebaseAuth auth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);




        //auth = FirebaseAuth.getInstance();
        reg_Fname = findViewById(R.id.signup_fullname);
        reg_Uname = findViewById(R.id.signup_username);
        reg_email = findViewById(R.id.signup_email);
        reg_Pnum = findViewById(R.id.signup_contact);
        reg_pword = findViewById(R.id.signup_password);
        reg_Rpword = findViewById(R.id.signup_retype_password);
        spinner = findViewById(R.id.category_spinner);
        progressBar = findViewById(R.id.progressBar);


        to_login = (Button) findViewById(R.id.to_login_page);
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_actv = new Intent(User_Register.this, Login.class);
                startActivity(next_actv);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.category_spinner); // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item); // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
//
//        Spinner spinner01 = (Spinner) findViewById(R.id.category_spinner);
//        spinner01.setOnItemSelectedListener(User_Register.this);


        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");




                String f_name = reg_Fname.getEditText().getText().toString();
                String u_name = reg_Uname.getEditText().getText().toString();
                String e_mail = reg_email.getEditText().getText().toString();
                String p_num = reg_Pnum.getEditText().getText().toString();
                String p_word = reg_pword.getEditText().getText().toString();
                String re_p_word = reg_Rpword.getEditText().getText().toString();
                String catg_spinner = spinner.getSelectedItem().toString();

                //progressBar.setVisibility(View.VISIBLE);



                if(!( TextUtils.isEmpty(re_p_word) || TextUtils.isEmpty(f_name) || TextUtils.isEmpty(u_name) || TextUtils.isEmpty(e_mail))) {

                    if( TextUtils.isEmpty(p_word) || !(p_word.length()<6)) {

                        if (p_word.equals(re_p_word)) {

                            Helper helper = new Helper(f_name, u_name, e_mail, p_num, p_word, re_p_word, catg_spinner);
                            reference.child(p_num).setValue(helper);
                            //progressBar.setVisibility(View.GONE);

                            startActivity(new Intent(User_Register.this, User_Profile.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Your passwords are not matching..!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Your password is too short..!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "You must fill every field..!", Toast.LENGTH_LONG).show();
                    return;
                }


            }
        });
    }
    private Boolean validateFname(){
        String fname_by = reg_Fname.getEditText().getText().toString();
        if(fname_by.isEmpty()){
            reg_Fname.setError("Field cannot be empty");
            return false;
        }else{
            reg_Fname.setError(null);
            return true;
        }
    }

}
