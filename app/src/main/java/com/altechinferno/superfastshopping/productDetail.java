package com.altechinferno.superfastshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productDetail extends AppCompatActivity {

    TextView description, price, pName, quantityView, costView;
    ImageView productImageURL;
    ElegantNumberButton quantity_btn;
    Button addToCart;
    ImageButton back;
    String productID = "";
    String customerID = "";

    FirebaseFirestore mFirestore;

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        productID = getIntent().getStringExtra("pid");
        pName = findViewById(R.id.pName);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        productImageURL = findViewById(R.id.imageURL);
        quantity_btn = findViewById(R.id.quantity);
        addToCart = findViewById(R.id.add_to_cart_button);

        back = findViewById(R.id.back);

        mFirestore = FirebaseFirestore.getInstance();
        FirebaseUser customerCurrent = FirebaseAuth.getInstance().getCurrentUser();
        customerID = customerCurrent.getUid();


        Intent intent = getIntent();
        String productName = intent.getStringExtra("PRODUCT_NAME");
        String productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION");
        String productImage = intent.getStringExtra("PRODUCT_IMAGE");
        String productPrice = intent.getStringExtra("PRODUCT_PRICE");




        pName.setText(productName);
        description.setText(productDescription);
        price.setText("GHc" + productPrice);
        Picasso.get().load(productImage).into(productImageURL);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(productDetail.this, productScan.class);
                startActivity(intent);
            }
        });
    }

    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar CalForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM, dd, yyyy");
        saveCurrentDate = currentDate.format(CalForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(CalForDate.getTime());


        DocumentReference cartListRef = mFirestore.collection("CartList/UserView/Products").document(customerID);


        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", pName.getText().toString());
        cartMap.put("price", price.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", quantity_btn.getNumber());
        cartMap.put("discount", "");


        cartListRef.set(cartMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(productDetail.this, "Added to Cart!!", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
