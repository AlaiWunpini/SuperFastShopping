package com.altechinferno.superfastshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class productScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private FirebaseFirestore mFirestore;
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    ZXingScannerView zXingScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_scan);


        mFirestore = FirebaseFirestore.getInstance();

        //check if camera permissions have been granted

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

        //this is going to be the scanning screen it will open the camera

        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);




    }

    @Override
    public void handleResult(Result result) {


        //  this is where the result of the scan is received

        String product_detail = result.getText();

        //toasting it so you can see what it is we can pass the result to your query once its a string

        checkProduct(product_detail);

        Toast.makeText(productScan.this, product_detail, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //this function will start the scanning  as in call the camera so remember to grant the camera permission add it to your manifest
                zXingScannerView.setResultHandler(this);
                zXingScannerView.startCamera();
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied, scanning would not be possible", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //this just stops the scanning when the screen goes off
        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this restarts the camera when you come back to the screen
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }


    public void checkProduct(String product){

        mFirestore.collection("Products").document(product)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String product_name = (String) document.get("product_name");
                    String product_price = (String) document.get("product_price");
                    String product_description = (String) document.get("product_description");
                    String imageURL = (String) document.get("imageURL");
                    String product = (String) document.get("product");



//                    Toast.makeText(productScan.this, product_name, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),productDetail.class);
                    intent.putExtra("PRODUCT_NAME", product_name);
                    intent.putExtra("PRODUCT_PRICE", product_price);
                    intent.putExtra("PRODUCT_DESCRIPTION", product_description);
                    intent.putExtra("PRODUCT_IMAGE", imageURL);
                    intent.putExtra("pid", product);
                    startActivity(intent);


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(productScan.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


}