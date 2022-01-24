package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class retrievepdf extends AppCompatActivity {

    ListView listView;

    DatabaseReference databaseReference;
    List<putpdf> uploadedpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievepdf);

        listView=findViewById(R.id.listvw);

        uploadedpdf=new ArrayList<>();

        retrievepdffiles();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                putpdf putpdf= uploadedpdf.get(i);

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(putpdf.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void retrievepdffiles() {

        databaseReference= FirebaseDatabase.getInstance().getReference("uploadpdf");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    putpdf putpdf=ds.getValue(com.example.collegeapp.putpdf.class);
                    uploadedpdf.add(putpdf);
                }
                String[] uploadsname=new String[uploadedpdf.size()];

                for (int i=0;i<uploadsname.length;i++) {
                   uploadsname[i]=uploadedpdf.get(i).getPdfdet();
                }

                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,uploadsname){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent){
                        View view =super.getView(position,convertView,parent);

                        TextView textView=(TextView) view
                                 .findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        return view;
                    }

                };

                listView.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}