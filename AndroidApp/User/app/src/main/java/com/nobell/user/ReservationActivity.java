package com.nobell.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ReservationActivity extends AppCompatActivity {
    int table_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent intent = getIntent();
        table_num = intent.getIntExtra("table_num",0);




    }



}