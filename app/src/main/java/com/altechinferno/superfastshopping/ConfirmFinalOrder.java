package com.altechinferno.superfastshopping;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrder extends AppCompatActivity {

    EditText momoNumber;
    Button makePayment;
    private String totalAmount = "";

    FirebaseFirestore mFirestore;
    String customerID = "";
    TextView totalAmountPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        momoNumber = findViewById(R.id.momoNumber);
        makePayment = findViewById(R.id.makePayment);
        totalAmountPay = findViewById(R.id.totalAmountPay);


        Intent intent = getIntent();
        String totalAmount = intent.getStringExtra("Total Price");
        totalAmountPay.setText("GHc " + totalAmount);

        mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser customerCurrent = FirebaseAuth.getInstance().getCurrentUser();
        customerID = customerCurrent.getUid();

        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentClick();
                Check();
            }
        });


    }

    private void Check() {
        if (TextUtils.isEmpty(momoNumber.getText().toString())) {
            Toast.makeText(this, "Please provide your Mobile Money Number.", Toast.LENGTH_SHORT).show();
        } else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DocumentReference ordersRef = mFirestore.collection("Orders").document(customerID);

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", momoNumber.getText().toString());

        ordersRef.update(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirestore.getInstance()
                            .collection("CartList").document(customerID)
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ConfirmFinalOrder.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmFinalOrder.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void paymentClick() {
        new momoPayment(writeJSON());
    }

    public String writeJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONObject payerObj = new JSONObject();
        try {

            payerObj.put("partyIdType", "MSISDN");
            payerObj.put("partyId", "0242626771");

            jsonObject.put("amount", 20.00);
            jsonObject.put("currency", "GHS");
            jsonObject.put("payer", payerObj);
            jsonObject.put("payerMessage", "Hacker");
            jsonObject.put("payeeNote", "Hacker");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject.toString();
    }
}
