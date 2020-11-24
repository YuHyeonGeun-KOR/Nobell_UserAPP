package com.nobell.user;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nobell.user.model.HttpConnector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class OpenrestActivity extends AppCompatActivity {
    public static Activity meme;
    private ImageButton btn_Info,btn_Search,btn_pay;
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

        meme = OpenrestActivity.this;

        Intent intent = getIntent();
        customer_email = intent.getStringExtra("customer_email");

        btn_Search = findViewById(R.id.btn_Search);
        btn_Info = findViewById(R.id.btn_Info);
        btn_order = findViewById(R.id.button_order);
        btn_pay = findViewById(R.id.btn_pay);


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
                    btn_pay.setEnabled(false);
                    btn_pay.setVisibility(View.INVISIBLE);
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
                            btn_pay.setEnabled(false);
                            btn_pay.setVisibility(View.INVISIBLE);
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
                            btn_pay.setVisibility(View.VISIBLE);
                            btn_order.setText(table_no +"번 테이블 주문하기");
                            btn_order.setEnabled(true);
                            btn_pay.setEnabled(true);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(customer_state.equals("3")){



                            btn_order.setText("결제를 요청하였습니다. 결제 대기중입니다.");
                            btn_order.setEnabled(false);
                            btn_pay.setEnabled(false);
                            btn_pay.setVisibility(View.INVISIBLE);

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

    public void PayOnClickHandler(View view)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("결제하기").setMessage("결제를 요청하시겠습니까??");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                String param ="&customer_email=" + customer_email;
                HttpConnector conn2 = new HttpConnector();
                String data_t2 = conn2.httpConnect(param,"/pay","POST");
                OpenrestActivity.meme.recreate();


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}