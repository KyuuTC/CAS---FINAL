package com.jeffersondeguzman.classattendance.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jeffersondeguzman.classattendance.R;

public class RegistrationActivity extends AppCompatActivity {
    Button signUp;
    EditText name, email, password;
    TextView logIn;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        signUp = findViewById(R.id.btnSignup);
        name = findViewById(R.id.tvName_reg);
        email = findViewById(R.id.tvEmail_reg);
        password = findViewById(R.id.tvPword_reg);
        logIn = findViewById(R.id.tvLogin);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);


                    createUser();

            }

            private void createUser() {

                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPassw = password.getText().toString();

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegistrationActivity.this, "Name is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(RegistrationActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userPassw)){
                    Toast.makeText(RegistrationActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userPassw.length() < 6){
                    Toast.makeText(RegistrationActivity.this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Create User
                auth.createUserWithEmailAndPassword(userEmail, userPassw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    UserModel user = new UserModel(userName, userEmail, userPassw);
                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);

                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

                                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(RegistrationActivity.this, "Error email has already been taken", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    }
