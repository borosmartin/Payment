package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class InsertActivity extends AppCompatActivity {

    private EditText payerEditText, receiverEditText, amountEditText, currencyEditText, methodEditText, dateEditText;
    private Button insertButton;
    FirebaseFirestore db;
    Intent goToMain = new Intent(InsertActivity.this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        payerEditText = findViewById(R.id.insertPayerText);
        receiverEditText = findViewById(R.id.insertReceiverText);
        amountEditText = findViewById(R.id.insertAmountText);
        currencyEditText = findViewById(R.id.insertCurrencyText);
        dateEditText = findViewById(R.id.insertDateText);
        methodEditText = findViewById(R.id.insertMethodText);
        insertButton = findViewById(R.id.insertbutton);

        db = FirebaseFirestore.getInstance();

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payerStr = payerEditText.getText().toString();
                String receiverStr = receiverEditText.getText().toString();
                String amountStr = amountEditText.getText().toString();
                String currencyStr = currencyEditText.getText().toString();
                String methodStr= methodEditText.getText().toString();
                String dateStr = dateEditText.getText().toString();

                uploadToFireStore(payerStr, receiverStr, amountStr, currencyStr, methodStr, dateStr);
            }
        });
    }

    private void uploadToFireStore(String payerStr, String receiverStr, String amountStr, String currencyStr, String methodStr, String dateStr){
        if (!payerStr.isEmpty() && !receiverStr.isEmpty() && !amountStr.isEmpty() && !currencyStr.isEmpty() && !methodStr.isEmpty() && !dateStr.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("payer", payerStr);
            map.put("receiver", receiverStr);
            map.put("amount", amountStr);
            map.put("currency", currencyStr);
            map.put("method", methodStr);
            map.put("date", dateStr);

            db.collection("Payment").document().set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(InsertActivity.this, "Payment inserted sucessfuly!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(goToMain);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InsertActivity.this, "ERROR: Could't insert payment!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(goToMain);
                        }
                    });
        } else {
            Toast.makeText(this, "All inputfields must be filled out!", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        Intent goToMain = new Intent(InsertActivity.this, MainActivity.class);
        startActivity(goToMain);
    }
}

//orig
/*
* package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class InsertActivity extends AppCompatActivity {

    private EditText payerEditText, receiverEditText, amountEditText, currencyEditText, methodEditText, dateEditText;
    private Button insertButton;
    FirebaseFirestore db;
    Intent goToMain = new Intent(InsertActivity.this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        payerEditText = findViewById(R.id.insertPayerText);
        receiverEditText = findViewById(R.id.insertReceiverText);
        amountEditText = findViewById(R.id.insertAmountText);
        currencyEditText = findViewById(R.id.insertCurrencyText);
        dateEditText = findViewById(R.id.insertDateText);
        methodEditText = findViewById(R.id.insertMethodText);
        insertButton = findViewById(R.id.insertbutton);

        db = FirebaseFirestore.getInstance();

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payerStr = payerEditText.getText().toString();
                String receiverStr = receiverEditText.getText().toString();
                String amountStr = amountEditText.getText().toString();
                String currencyStr = currencyEditText.getText().toString();
                String methodStr= methodEditText.getText().toString();
                String dateStr = dateEditText.getText().toString();

                uploadToFireStore(payerStr, receiverStr, amountStr, currencyStr, methodStr, dateStr);
            }
        });
    }

    private void uploadToFireStore(String payerStr, String receiverStr, String amountStr, String currencyStr, String methodStr, String dateStr){
        if (!payerStr.isEmpty() && !receiverStr.isEmpty() && !amountStr.isEmpty() && !currencyStr.isEmpty() && !methodStr.isEmpty() && !dateStr.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
                map.put("payer", payerStr);
                map.put("receiver", receiverStr);
                map.put("amount", amountStr);
                map.put("currency", currencyStr);
                map.put("method", methodStr);
                map.put("date", dateStr);

            db.collection("Payment").document().set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(InsertActivity.this, "Payment inserted sucessfuly!", Toast.LENGTH_SHORT).show();
                            startActivity(goToMain);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsertActivity.this, "ERROR: Could't insert payment!", Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            Toast.makeText(this, "All inputfields must be filled out!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);
        startActivity(intent);
    }
}
* */

//sec
/*
* package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText payerEditText, receiverEditText, amountEditText, currencyEditText, methodEditText, dateEditText;
    private FirebaseFirestore db;
    Intent goToMain = new Intent(InsertActivity.this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        db = FirebaseFirestore.getInstance();

        payerEditText = findViewById(R.id.insertPayerText);
        receiverEditText = findViewById(R.id.insertReceiverText);
        amountEditText = findViewById(R.id.insertAmountText);
        currencyEditText = findViewById(R.id.insertCurrencyText);
        dateEditText = findViewById(R.id.insertDateText);
        methodEditText = findViewById(R.id.insertMethodText);

        findViewById(R.id.insertbutton).setOnClickListener(this);
    }

    public boolean hasValidationErrors(String payer, String receiver, String amount, String currency, String date, String method) {
        if (payer.isEmpty())
            return true;

        if (receiver.isEmpty())
            return true;

        if (amount.isEmpty())
            return true;

        if (currency.isEmpty())
            return true;

        if (date.isEmpty())
            return true;

        if (method.isEmpty())
            return true;

        return false;
    }

    @Override
    public void onClick(View v) {
        String payerStr = payerEditText.getText().toString();
        String receiverStr = receiverEditText.getText().toString();
        String amountStr = amountEditText.getText().toString();
        String currencyStr = currencyEditText.getText().toString();
        String methodStr= methodEditText.getText().toString();
        String dateStr = dateEditText.getText().toString();
    }

    private void uploadToFireStore(String payerStr, String receiverStr, String amountStr, String currencyStr, String methodStr, String dateStr){
        if (!payerStr.isEmpty() && !receiverStr.isEmpty() && !amountStr.isEmpty() && !currencyStr.isEmpty() && !methodStr.isEmpty() && !dateStr.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
                map.put("payer", payerStr);
                map.put("receiver", receiverStr);
                map.put("amount", amountStr);
                map.put("currency", currencyStr);
                map.put("method", methodStr);
                map.put("date", dateStr);

            db.collection("Payment").document().set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(InsertActivity.this, "Payment inserted sucessfuly!", Toast.LENGTH_SHORT).show();
                            startActivity(goToMain);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InsertActivity.this, "ERROR: Could't insert payment!", Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            Toast.makeText(this, "All inputfields must be filled out!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, ActionsActivity.class);
        startActivity(intent);
    }

}
* */