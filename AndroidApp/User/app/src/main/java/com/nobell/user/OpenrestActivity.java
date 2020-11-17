package com.nobell.user;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nobell.user.model.HttpConnector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenrestActivity extends AppCompatActivity {
    private ImageButton btn_Info,btn_Search;
    String customer_email ;
    String data_t;
    String rs_name;
    int rs_id;
    int table_no;
    String table_no_string;
    private Button btn_order;
    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openrest);

        Intent intent = getIntent();
        customer_email = intent.getStringExtra("customer_email");

        btn_Search = findViewById(R.id.btn_Search);
        btn_Info = findViewById(R.id.btn_Info);
        btn_order = findViewById(R.id.button_order);

        String param ="&customer_email=" + customer_email;
        HttpConnector conn = new HttpConnector();
        data_t = conn.httpConnect(param,"/customer_state","POST");


        try {
            JSONArray jarray = new JSONArray(data_t);
            for (int i = 0; i < jarray.length(); i++){

                JSONObject jsonObject = jarray.getJSONObject(i);
                String customer_state = jsonObject.getString("customer_state");

                Log.e("RECV DATA", customer_state);

                if(customer_state.equals("0")){

                    btn_order.setText("이용중인 식당이 없습니다");
                    btn_order.setEnabled(false);
                }
                else if(customer_state.equals("1")){

                    param ="&customer_email=" + customer_email;
                    HttpConnector conn1 = new HttpConnector();
                    String data_t1 = conn1.httpConnect(param,"/customer_state1","POST");
                    try {
                        JSONArray jarray1 = new JSONArray(data_t1);
                        for (int j = 0; j < jarray1.length(); j++){

                            JSONObject jsonObject1 = jarray1.getJSONObject(j);
                            rs_name = jsonObject1.getString("rs_name");
                            Log.e("RECV DATA", rs_name);
                            btn_order.setText(rs_name + "에 방문요청중입니다.");
                            btn_order.setEnabled(false);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if(customer_state.equals("2")){

                    param ="&customer_email=" + customer_email;
                    HttpConnector conn2 = new HttpConnector();
                    String data_t2 = conn2.httpConnect(param,"/customer_state2","POST");
                    try {
                        JSONArray jarray2 = new JSONArray(data_t2);
                        for (int k = 0; k < jarray2.length(); k++){
                            JSONObject jsonObject2 = jarray2.getJSONObject(k);
                            table_no = jsonObject2.getInt("table_no");
                            rs_id = jsonObject2.getInt("table_rs_id");
                            table_no_string = Integer.toString(table_no);

                            btn_order.setText(table_no +"번 테이블 주문하기");
                            btn_order.setEnabled(true);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



            }




        } catch (JSONException e) {
            e.printStackTrace();
        }



        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2  = new Intent(OpenrestActivity.this, OrderActivity.class);
                intent2.putExtra("customer_email", customer_email);
                intent2.putExtra("rs_id", rs_id);
                intent2.putExtra("table_no", table_no);
                startActivity(intent2);

            }
        });

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpenrestActivity.this, RestaurantView.class);
                intent.putExtra("customer_email", customer_email);
                startActivity(intent);
            }
        });

        btn_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpenrestActivity.this, InfoMainActivity.class);
                startActivity(intent);
            }
        });

    }


}