package com.altechinferno.superfastshopping;
//

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class momoPayment extends AppCompatActivity {

    private Context ct ;

    public momoPayment(String body,Context ct){
        this.ct = ct;

        new HTTPTrack().execute(body);
    }

    private class HTTPTrack extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection urlConnection = null;

            try {

//
                URL url = new URL("https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setRequestProperty("Authorization", "");
//                urlConnection.setRequestProperty("X-Callback-Url", "");
                urlConnection.setRequestProperty("X-Reference-Id", "9ecd4c29-b999-42e4-9d73-b43c8f0372ff");
                urlConnection.setRequestProperty("X-Target-Environment", "sandbox");
                urlConnection.setRequestProperty("Ocp-Apim-Subscription-Key", "f0018849877448cda6bba1f1983fb1e8");
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setChunkedStreamingMode(0);

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        out, "UTF-8"));
                writer.write(params[0]);
                writer.flush();

                int code = urlConnection.getResponseCode();
                if (code == 401) {
                    throw new IOException("UnAuthorized Request: " + code);
                } else if (code != 201) {
                    throw new IOException("Invalid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    Log.i("data", line);
                }

            } catch (Exception e) {
                e.printStackTrace();
               //Toast.makeText(ct,e.getMessage(),Toast.LENGTH_LONG);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

    }
}
