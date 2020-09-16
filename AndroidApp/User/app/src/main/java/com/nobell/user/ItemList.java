package com.nobell.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Intent intent = getIntent();
        String restaurent = intent.getExtras().getString("restaurent");
        TextView textView = (TextView) findViewById(R.id.tv_res);
        textView.setText(restaurent);

    }
}