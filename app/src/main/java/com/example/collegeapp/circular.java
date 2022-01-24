package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class circular extends AppCompatActivity {

    EditText chead;
    EditText cbody;
    EditText cname;
    EditText cdate;
    Button cupload;

    DatabaseReference circularDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular);

        chead.findViewById(R.id.chead);
        cbody.findViewById(R.id.cbody);
        cname.findViewById(R.id.cname);
        cdate.findViewById(R.id.cdate);
        cupload.findViewById(R.id.uploadc);

        circularDbRef = FirebaseDatabase.getInstance().getReference().child("uploadcircular");

        cupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertcircular();
            }
        });
    }

    private void insertcircular(){
        String head=chead.getText().toString();
        String body=cbody.getText().toString();
        String name = cname.getText().toString();
        String date = cdate.getText().toString();

        uploadcircular Uploadcircular = new uploadcircular(head,body,name,date);

        circularDbRef.push().setValue(Uploadcircular);
        Toast.makeText(circular.this,"Circular Uploaded",Toast.LENGTH_SHORT).show();

    }
}