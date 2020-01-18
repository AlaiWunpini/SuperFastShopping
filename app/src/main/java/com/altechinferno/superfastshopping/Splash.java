package com.altechinferno.superfastshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
    public void  login(View view)
    {
        startActivity(new Intent(this,Login.class));
    }


    public void  getStarted(View view)
    {
        startActivity(new Intent(this,signup.class));
    }
}
