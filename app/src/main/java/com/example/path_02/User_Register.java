package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User_Register extends AppCompatActivity {

    TextInputLayout reg_Fname, reg_Uname, reg_email, reg_Pnum, reg_pword, reg_Rpword;
    Button reg_btn, to_login;
    ImageView profile_URL;
    ProgressBar progressBar;
    Spinner spinner;
    FirebaseAuth auth;
    Helper helper;

    String e_mail, p_word, profUrl, prof_url;


    FirebaseDatabase rootNode;
    DatabaseReference reference;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);




        auth = FirebaseAuth.getInstance();
//        rootNode = FirebaseDatabase.getInstance();

        reg_Fname = findViewById(R.id.signup_fullname);
        reg_Uname = findViewById(R.id.signup_username);
        reg_email = findViewById(R.id.signup_email);
        reg_Pnum = findViewById(R.id.signup_contact);
        reg_pword = findViewById(R.id.signup_password);
        reg_Rpword = findViewById(R.id.signup_retype_password);
        spinner = findViewById(R.id.category_spinner);
        progressBar = findViewById(R.id.progressBar);
        //profile_URL = findViewById(R.id.user_prof_image);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

//        Intent i = getIntent();
//        prof_url = i.getStringExtra("imgRef");

        if ( auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), User_Profile.class));
            finish();
        }


        to_login = (Button) findViewById(R.id.to_login_page);
        to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_Register.this, Login.class));
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

//                final String f_name = reg_Fname.getEditText().getText().toString().trim();
//                final String u_name = reg_Uname.getEditText().getText().toString().trim();
//                final String e_mail = reg_email.getEditText().getText().toString().trim();
//                String p_word = reg_pword.getEditText().getText().toString().trim();
//                String re_p_word = reg_Rpword.getEditText().getText().toString().trim();
//                final String p_num = reg_Pnum.getEditText().getText().toString().trim();
//                String catg_spinner = spinner.getSelectedItem().toString();


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                if(!validateFname() || !validateUname() || !validateEmail() || !validatePhone() || !validatePword()){
                    return;
                } else {

                    progressBar.setVisibility(View.VISIBLE);


                    String f_name = reg_Fname.getEditText().getText().toString();
                    String u_name = reg_Uname.getEditText().getText().toString();
                    e_mail = reg_email.getEditText().getText().toString();
                    String p_num = reg_Pnum.getEditText().getText().toString();
                    p_word = reg_pword.getEditText().getText().toString();
                    String re_p_word = reg_Rpword.getEditText().getText().toString();
                    String catg_spinner = spinner.getSelectedItem().toString();
                    helper = new Helper(f_name, u_name, e_mail, p_num, p_word, re_p_word, catg_spinner, prof_url);




                            if (p_word.equals(re_p_word)) {

                                auth.createUserWithEmailAndPassword(e_mail, p_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull /*@org.jetbrains.annotations.NotNull*/ Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            FirebaseUser user = auth.getCurrentUser();
                                            updateUI(user);

                                            startActivity(new Intent(User_Register.this, Login.class));
                                            Toast.makeText(getApplicationContext(), "User has been created. Please login to continue..", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Error..!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }

                                });



                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Your passwords are not matching..!", Toast.LENGTH_LONG).show();
                                return;
                            }
                }


            }
        });
    }


    public void updateUI(FirebaseUser user){
        String id = reference.push().getKey();
        reference.child(id).setValue(helper);
    }


    private Boolean validateFname(){

        String fname_by = reg_Fname.getEditText().getText().toString();

        if(fname_by.isEmpty()){
            reg_Fname.setError("Field cannot be empty");
            return false;
        }else{
            reg_Fname.setError(null);
            reg_Fname.setErrorEnabled(false);   //to remove the space of the error
            return true;
        }
    }

    private Boolean validateUname(){

        String uname_by = reg_Uname.getEditText().getText().toString();
        String noSpaces = "(?=\\s+$)";

        if(uname_by.isEmpty()){
            reg_Uname.setError("Field cannot be empty");
            return false;
        }else if(uname_by.length()>=12){
            reg_Uname.setError("Your username is too long");
            return false;
        }else if(uname_by.matches(noSpaces)){
            reg_Uname.setError("Spaces cannot be used for the username");
            return false;
        }
        else{
            reg_Uname.setError(null);
            reg_Uname.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){

        String email_by = reg_email.getEditText().getText().toString();
        String email_pattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";    // A-Z, a-z, 0-9, ., -, _ and after @ a-z, .

        if(email_by.isEmpty()){
            reg_email.setError("Field cannot be empty");
            return false;
        }
        else if (!email_by.matches(email_pattern)){
            reg_email.setError("Invalid email");
            return false;
        }
        else{
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhone(){

        String pnum_by = reg_Pnum.getEditText().getText().toString();
        String phone_pattern =  "^" + "(7|0(?:\\+94))" + "([0-9]{9,10})" + "$";

        if(pnum_by.isEmpty()){
            reg_Pnum.setError("Field cannot be empty");
            return false;
        }else if(pnum_by.matches(phone_pattern)){
            reg_Pnum.setError("Invalid phone number");
            return false;
        }
        else{
            reg_Pnum.setError(null);
            reg_Pnum.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePword(){

        String pword_by = reg_pword.getEditText().getText().toString();
        String password_pattern = "^" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*\\s+$)" + ".{6,}" +"$";

        if(pword_by.isEmpty()){
            reg_pword.setError("Field cannot be empty");
            return false;
        }else if(pword_by.matches(password_pattern)){
            reg_pword.setError("Your password is too weak");
            return false;
        }
        else{
            reg_pword.setError(null);
            reg_pword.setErrorEnabled(false);
            return true;
        }
    }

}
