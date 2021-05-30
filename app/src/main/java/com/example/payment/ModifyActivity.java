package com.example.payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText updatePayer, updateReceiver, updateAmount, updateCurrency, updateDate, updateMethod;
    private Payment payment;
    private FirebaseFirestore db;
    Intent goToMain = new Intent(ModifyActivity.this, MainActivity.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        payment = (Payment) getIntent().getSerializableExtra("Payment");
        db = FirebaseFirestore.getInstance();

        updatePayer = findViewById(R.id.updatePayerText);
        updateReceiver = findViewById(R.id.updateReceiverText);
        updateAmount = findViewById(R.id.updateAmountText);
        updateCurrency = findViewById(R.id.updateCurrencyText);
        updateDate = findViewById(R.id.updateDateText);
        updateMethod = findViewById(R.id.updateMethodText);

        updatePayer.setText(payment.getPayer());
        updateReceiver.setText(payment.getReceiver());
        updateAmount.setText(payment.getAmount());
        updateCurrency.setText(payment.getCurrency());
        updateDate.setText(payment.getDate());
        updateMethod.setText(payment.getMethod());

        findViewById(R.id.updateButton).setOnClickListener(this);
        findViewById(R.id.deletebutton).setOnClickListener(this);

    }

    private boolean hasMissingField(String payer, String receiver, String amount, String currency, String date, String method) {
        if (payer.isEmpty()){ return true; }
        else if (receiver.isEmpty()){ return true; }
        else if (amount.isEmpty()){ return true; }
        else if (currency.isEmpty()){ return true; }
        else if (date.isEmpty()){ return true; }
        else if (method.isEmpty()){ return true; }
        else { return false; }
    }

    private void updatePayment(){
        String payer = updatePayer.getText().toString();
        String receiver = updateReceiver.getText().toString();
        String amount = updateAmount.getText().toString();
        String currency = updateCurrency.getText().toString();
        String date = updateDate.getText().toString();
        String method = updateMethod.getText().toString();

        if (!hasMissingField(payer, receiver, amount, currency, date, method)){

            Payment payment1 = new Payment(payer, receiver, amount, currency, date, method);

            db.collection("Payment")
                    .document(payment.getId())
                    .set(payment1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ModifyActivity.this, "Payment updated sucessfuly!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(goToMain);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModifyActivity.this, "ERROR: Could't update payment!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(goToMain);
                        }
                    });
        } else {
            Toast.makeText(this, "All inputfields must be filled out!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePayment(){
        db.collection("Payment")
                .document(payment.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ModifyActivity.this, "Payment deleted sucessfuly!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(goToMain);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ModifyActivity.this, "ERROR: Could't delete the payment!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(goToMain);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateButton:
                updatePayment();
                break;
            case R.id.deletebutton:
                AlertDialog.Builder notyBuild = new AlertDialog.Builder(this);
                notyBuild.setTitle("Do you want to delete this payment? ");

                notyBuild.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePayment();
                    }
                });

                notyBuild.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog noty = notyBuild.create();
                noty.show();
                break;
        }
    }

    public void back(View view) {
        Intent goToMain = new Intent(ModifyActivity.this, MainActivity.class);
        startActivity(goToMain);
    }
}