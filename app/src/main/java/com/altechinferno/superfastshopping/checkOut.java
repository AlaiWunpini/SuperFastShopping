package com.altechinferno.superfastshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;


public class checkOut extends AppCompatActivity {

    EditText momoNumberText, amount;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        momoNumberText = findViewById(R.id.momoNumberText);
        amount = findViewById(R.id.amount);
        button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                call momopayment API and process transaction details as a json string
                //momoPayment.main(writeJSON());

            }
        });

    }

    public String writeJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONObject payerObj = new JSONObject();
        try {

            payerObj.put("partyIdType","MSISDN");
            payerObj.put("partyId","0242626771");

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
