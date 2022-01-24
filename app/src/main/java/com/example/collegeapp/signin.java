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

public class signin extends AppCompatActivity {

    EditText mname,nemail,mpassword,mphone;
    Button mregister;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mname = findViewById(R.id.siname);
        nemail = findViewById(R.id.sinemail);
        mphone = findViewById(R.id.sinphone);
        mpassword = findViewById(R.id.sinpassword);
        mregister = findViewById(R.id.sinbtn);

        fauth = FirebaseAuth.getInstance();

        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = nemail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    nemail.setError("Email is Required");
                    return;
                }
                

                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(signin.this,"User Created",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signin.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(signin.this,"user cannot be created"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}