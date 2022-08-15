package com.example.workatyourwill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Book_Service extends AppCompatActivity {

    ImageView serv_image;
    TextView serv_name;
    TextView serv_cost;
    TextView business_name;
    TextView rating;
    TextView booking_month;
    TextView serv_desc;
    Button book_service;
    String docId;
    String workerId;
    String profession;
    String businessName;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView progresstext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__service);
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(Book_Service.this);

        progressDialog.show();

        progressDialog.setContentView(R.layout.progressbar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progresstext=progressDialog.findViewById(R.id.progress_text);
        progresstext.setVisibility(View.GONE);
        serv_image=findViewById(R.id.service_img);
        serv_name=findViewById(R.id.serv_name);
        business_name=findViewById(R.id.business_name);
        rating=findViewById(R.id.rating);
        serv_cost=findViewById(R.id.serv_cost);
        serv_desc=findViewById(R.id.serv_desc);
        booking_month=findViewById(R.id.booking_month);
        book_service=findViewById(R.id.bookService);
        Intent intent=getIntent();
        docId=intent.getStringExtra("docId");
        workerId=intent.getStringExtra("workerId");
        businessName=intent.getStringExtra("business_name");
        profession=intent.getStringExtra("category");


        if(profession.equals("Carpenter")){
            serv_image.setImageResource(R.drawable.prof1);
        }
        else if(profession.equals("Electrician"))
        {
            serv_image.setImageResource(R.drawable.prof2);
        }
        else if(profession.equals("Internet Provider")){
            serv_image.setImageResource(R.drawable.prof5_);
        }
        else if(profession.equals("Photographer")){
            serv_image.setImageResource(R.drawable.prof4_);
        }
        else if(profession.equals("Painter")){
            serv_image.setImageResource(R.drawable.prof3i);
        }
        else{
            serv_image.setImageResource(R.drawable.prof1);
        }

        firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference=firebaseFirestore.collection("workers").document(workerId).collection("services").document(docId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                serv_name.setText((String)documentSnapshot.get("s_name"));
                serv_cost.setText((double)documentSnapshot.get("s_cost")+"");
                serv_desc.setText((String)documentSnapshot.get("s_desc"));
                rating.setText((double)documentSnapshot.get("s_rating")+"");
                booking_month.setText(documentSnapshot.get("s_bookings_per_month")+"");
                business_name.setText(businessName);
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Book_Service.this,"Couldn't get details!",Toast.LENGTH_SHORT).show();
            }
        });
//        progressDialog.dismiss();

        // Code for book service
        book_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CollectionReference documentReference=firebaseFirestore.collection("requests");
                CollectionReference docReference=firebaseFirestore.collection("requests");
                String userId=firebaseAuth.getUid();
                Request request=new Request(userId,workerId,docId,new Date(),false,serv_name.getText().toString(),serv_cost.getText().toString(),serv_desc.getText().toString(),rating.getText().toString(),booking_month.getText().toString(),business_name.getText().toString());
                docReference.add(request).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Book_Service.this,"Request Sent to the Service Provider",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        finish();
                    }
                });

            }
        });



    }
}