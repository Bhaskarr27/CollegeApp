package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText inname,inpass;
    TextView regis;
    Button letin;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regis = findViewById(R.id.reg);
        inname = findViewById(R.id.mainame);
        inpass = findViewById(R.id.mainpass);
        letin = findViewById(R.id.mainbtn);
        fauth=FirebaseAuth.getInstance();


        letin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inname.getText().toString().trim();
                String password = inpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    inname.setError("Email is Required");
                    return;
                }
                fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Successful login",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,dashboard.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"user cannot be created"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , signin.class);
                startActivity(intent);
            }
        });




    }
}