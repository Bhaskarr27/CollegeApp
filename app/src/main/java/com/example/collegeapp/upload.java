package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class upload extends AppCompatActivity {
    EditText uedittext;
    EditText updfdet;
    Button ubtn;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uedittext = findViewById(R.id.edittext);
        updfdet=findViewById(R.id.pdfname);
        ubtn = findViewById(R.id.btn);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference( "uploadpdf");

        ubtn.setEnabled(false);

        uedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpdf();
            }
        });
    }
    private void selectpdf(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"pdf file select"),12);
    }
    @Override
    protected void onActivityResult(int requestCode, int resutlCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resutlCode,data);

        if (requestCode == 12 && resutlCode==RESULT_OK && data!=null && data.getData()!=null){
            ubtn.setEnabled(true);
            uedittext.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/n")+1));

            ubtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    uploadpdffirebase(data.getData());
                }
            });

        }
    }

    private void uploadpdffirebase(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("File Is Loading");
        progressDialog.show();

        StorageReference reference=storageReference.child("uploadpdf"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putpdf putpdf = new putpdf(uedittext.getText().toString(),updfdet.getText().toString(),uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(putpdf);





                        Toast.makeText(upload.this,"Uploaded Sucessfullly",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded"+(int) progress+ "%");
            }
        });
    }

    public void retrievePdf(View view) {
        startActivity(new Intent(getApplicationContext(),retrievepdf.class));
    }
}