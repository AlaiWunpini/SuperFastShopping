package com.altechinferno.superfastshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText et_full_name,et_email_address,et_phonenumber,et_password;
    Button mSignupBtn;
    TextView mLoginBtn;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    String customerID;

    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_full_name = findViewById(R.id.et_full_name);
        et_email_address = findViewById(R.id.et_email_address);
        et_phonenumber = findViewById(R.id.et_phonenumber);
        et_password = findViewById(R.id.et_password);
        mSignupBtn = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progressBar);



        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullName = et_full_name.getText().toString();
                final String email = et_email_address.getText().toString();
                final String phonenumber= et_phonenumber.getText().toString();
                final String password = et_password.getText().toString();

                if(TextUtils.isEmpty(fullName)){
                    et_full_name.setError("Full Name is Required!!");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    et_email_address.setError("Email Address is Required");
                    return;
                }
                if(TextUtils.isEmpty(phonenumber)){
                    et_phonenumber.setError("Phonenumber is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    et_password.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    et_password.setError("Password must be 6 or more characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    customer information = new customer(
                                            fullName,
                                            email,
                                            phonenumber,
                                            password
                                    );

                                    FirebaseDatabase.getInstance().getReference("customer")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Register.this, "Registration Completed!!!", Toast.LENGTH_SHORT).show();
                                            customerID = firebaseAuth.getCurrentUser().getUid();

//                                            Intent intent = new Intent(getApplicationContext(), productDetail.class);
//                                            intent.putExtra("customerID", customerID);
//                                            startActivity(intent);intent

                                            DocumentReference documentReference = fStore.collection("customer").document(customerID);
                                            Map<String, Object> customer = new HashMap<>();
                                            customer.put("fullName", fullName);
                                            customer.put("email", email);
                                            customer.put("phonenumber", phonenumber);
                                            documentReference.set(customer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Register.this,"Customer profile generated successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Failed to Register, Please Try Again...", Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);

                                }

                                // ...
                            }
                        });

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
