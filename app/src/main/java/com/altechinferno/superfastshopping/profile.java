package com.altechinferno.superfastshopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private TextView name_textview,email_textview,phone_textview;
    private ImageView user_imageview,email_imageview,phone_imageview;
    private String email, password;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USERS = "customer";




    TextView myTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        name_textview = findViewById(R.id.name_textview);
        phone_textview = findViewById(R.id.phone_textview);
        email_textview = findViewById(R.id.email_textview);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USERS);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().equals(email)){
                        name_textview.setText(ds.child("fullName").getValue(String.class));
                        email_textview.setText(email);
                        phone_textview.setText(ds.child("phonenumber").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}
