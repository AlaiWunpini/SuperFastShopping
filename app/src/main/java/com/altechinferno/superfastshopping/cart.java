package com.altechinferno.superfastshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.altechinferno.superfastshopping.model.Cart;
import com.altechinferno.superfastshopping.ViewHolder.CartViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

public class cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button buttonCheckout;
    private TextView totalPrice;



    FirebaseFirestore mFireStore;
    String customerID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        buttonCheckout = findViewById(R.id.buttonCheckout);
        totalPrice = findViewById(R.id.totalPrice);



        FirebaseUser customerCurrent = FirebaseAuth.getInstance().getCurrentUser();
        customerID = customerCurrent.getUid();

        FirebaseFirestore.setLoggingEnabled(true);

        mFireStore = FirebaseFirestore.getInstance();

    }

    public void paymentClick(View v){
        //call momopayment API and process transaction details as a json string
        new momoPayment(writeJSON());


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


    @Override
    protected void onStart() {
        super.onStart();



        CollectionReference cartListRef = mFireStore.collection("CartList");

        Query query = cartListRef.whereEqualTo("pname",true);

        FirestoreRecyclerOptions<Cart> options =
                new FirestoreRecyclerOptions.Builder<Cart>()
                        .setQuery(query, Cart.class)
                        .build();

        final FirestoreRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirestoreRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                holder.productName.setText(model.getPname());
                holder.productPrice.setText(model.getPrice());
                holder.productQuantity.setText(model.getQuantity());

                holder.getAdapterPosition();

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.startListening();

    }


}
