package com.example.payment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private Context context;
    private ArrayList<Payment> list;

    public PaymentAdapter(Context context, ArrayList<Payment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_single, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = list.get(position);

        holder.list_payer.setText(payment.getPayer());
        holder.list_receiver.setText(payment.getReceiver());
        holder.list_amount.setText(payment.getAmount());
        holder.list_currency.setText(payment.getCurrency());
        holder.list_date.setText(payment.getDate());
        holder.list_method.setText(payment.getMethod());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView list_payer, list_receiver, list_amount, list_currency, list_date, list_method;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            list_payer = itemView.findViewById(R.id.payerField);
            list_receiver = itemView.findViewById(R.id.reciverField);
            list_amount = itemView.findViewById(R.id.amountField);
            list_currency = itemView.findViewById(R.id.currencyField);
            list_date = itemView.findViewById(R.id.dateField);
            list_method = itemView.findViewById(R.id.methodField);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Payment payment = list.get(getAdapterPosition());
            Intent gotoUpdate = new Intent(context, ModifyActivity.class);
            gotoUpdate.putExtra("Payment", payment);

            context.startActivity(gotoUpdate);
        }
    }
}

/*
* package com.example.payment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private Context context;
    private ArrayList<Payment> list;

    public PaymentAdapter(Context context, ArrayList<Payment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_single, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = list.get(position);
        holder.list_payer.setText(payment.getPayer());
        holder.list_receiver.setText(payment.getReceiver());
        holder.list_amount.setText(payment.getAmount());
        holder.list_currency.setText(payment.getCurrency());
        holder.list_date.setText(payment.getDate());
        holder.list_method.setText(payment.getMethod());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView list_payer, list_receiver, list_amount, list_currency, list_date, list_method;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            list_payer = itemView.findViewById(R.id.payerField);
            list_receiver = itemView.findViewById(R.id.reciverField);
            list_amount = itemView.findViewById(R.id.amountField);
            list_currency = itemView.findViewById(R.id.currencyField);
            list_date = itemView.findViewById(R.id.dateField);
            list_method = itemView.findViewById(R.id.methodField);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Payment payment = list.get(getAdapterPosition());
            Intent gotoUpdate = new Intent(context, DeleteActivity.class);
            gotoUpdate.putExtra("Payment", payment);

            context.startActivity(gotoUpdate);
        }
    }
}

* */