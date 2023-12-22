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
import com.jeffersondeguzman.classattendance.MainActivity;
import com.jeffersondeguzman.classattendance.R;


public class LoginActivity extends AppCompatActivity {
    Button logIn;
    EditText email, password;
    TextView signUp;

    ProgressBar progressBar;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    boolean pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logIn = findViewById(R.id.btnLogin_login);
        email = findViewById(R.id.tvEmail_login);
        password = findViewById(R.id.tvPword_login);
        signUp = findViewById(R.id.tvSignup_login);



        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(
                        LoginActivity.this, RegistrationActivity.class
                ));
                finish();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                logInUser();
            }
        });

    }
            private void logInUser(){

                String userEmail = email.getText().toString();
                String userPassw = password.getText().toString();


                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(LoginActivity.this, "Please Insert a Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userPassw)){
                    Toast.makeText(LoginActivity.this, "Please Insert a Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userPassw.length() < 6){
                    Toast.makeText(LoginActivity.this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Login USer
                auth.signInWithEmailAndPassword(userEmail, userPassw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else{
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
}
