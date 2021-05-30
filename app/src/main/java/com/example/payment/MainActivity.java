package com.example.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    PaymentAdapter adapter;
    ArrayList<Payment> paymentList;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();

        recyclerView = findViewById(R.id.firestoreList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        paymentList = new ArrayList<Payment>();
        adapter = new PaymentAdapter(this, paymentList);

        recyclerView.setAdapter(adapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("Payment")
                .orderBy("payer", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if (progress.isShowing())
                                progress.dismiss();
                            Log.e("Database error",error.getMessage());
                        }
                        for (DocumentChange change : value.getDocumentChanges()){
                            if(change.getType() == DocumentChange.Type.ADDED || change.getType() == DocumentChange.Type.MODIFIED || change.getType() == DocumentChange.Type.REMOVED){
                                Payment p = change.getDocument().toObject(Payment.class);
                                p.setId(change.getDocument().getId());
                                paymentList.add(p);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if (progress.isShowing())
                            progress.dismiss();
                    }
                });
    }

    public void insert(View view) {
        Intent goToInsert = new Intent(MainActivity.this, InsertActivity.class);
        startActivity(goToInsert);
    }
}

// orig
/*
* package com.example.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    PaymentAdapter adapter;
    ArrayList<Payment> paymentList;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();

        recyclerView = findViewById(R.id.firestoreList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        paymentList = new ArrayList<Payment>();
        adapter = new PaymentAdapter(this, paymentList);

        recyclerView.setAdapter(adapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("Payment")
                .orderBy("payer", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            if (progress.isShowing())
                                progress.dismiss();
                            Log.e("Database error",error.getMessage());
                        }
                        for (DocumentChange change : value.getDocumentChanges()){
                            //TODO: changed!!!!!!!
                            if(change.getType() == DocumentChange.Type.ADDED){
                                Payment p = change.getDocument().toObject(Payment.class);
                                p.setId(change.getDocument().getId());
                                paymentList.add(p);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if (progress.isShowing())
                            progress.dismiss();
                    }
                });
    }


    public void actions(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);
        startActivity(intent);
    }
}

* */


// sec
/*
* package com.example.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private PaymentAdapter adapter;
    private ArrayList<Payment> paymentList;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();

        recyclerView = findViewById(R.id.firestoreList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        paymentList = new ArrayList<>();
        adapter = new PaymentAdapter(this, paymentList);

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        db.collection("Payment")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progress.dismiss();
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot changes : list) {
                                Payment payment1 = changes.toObject(Payment.class);
                                payment1.setId(changes.getId());
                                paymentList.add(payment1);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    public void actions(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);
        startActivity(intent);
    }
}
* */