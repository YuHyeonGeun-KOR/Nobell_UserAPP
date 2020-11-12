package com.nobell.user;
import com.nobell.user.model.HttpConnector;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RestaurantView extends AppCompatActivity {
    private TextView text_res;
    private Button btn_res;
    String data_r;
    private String result_restaurant;
    private int rs_id;
    private String rs_name;
    private String rs_phone;
    private String rs_address;
    private String rs_intro;
    private String rs_open;
    private String rs_close;
    private String rs_owner;
    private int rs_state;
    private LinearLayout view_menus;
    private List<String> data;

    String customer_email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        Intent intent = getIntent();
        customer_email=intent.getStringExtra("customer_email");

        String param = "";
        // Making AsyncTask For Server
        HttpConnector conn = new HttpConnector();
        data_r = conn.httpConnect(param,"/res_View","GET");

                 view_menus = (LinearLayout) findViewById(R.id.view_menus);


                try {
                    JSONArray jarray = new JSONArray(data_r);
                    for (int i = 0; i < jarray.length(); i++){

                        JSONObject jsonObject = jarray.getJSONObject(i);
                        rs_id = jsonObject.getInt("rs_id");
                        rs_name = jsonObject.getString("rs_name");
                        rs_phone = jsonObject.getString("rs_phone");
                        rs_address = jsonObject.getString("rs_address");
                        rs_intro = jsonObject.getString("rs_intro");
                        rs_open = jsonObject.getString("rs_open");
                        rs_close = jsonObject.getString("rs_close");
                        rs_owner = jsonObject.getString("rs_owner");
                        rs_state = jsonObject.getInt("rs_state");

                        TextView tv_name = new TextView(this);
                        tv_name.setTop(40);
                        tv_name.setWidth(300);
                        tv_name.setHeight(120);
                        tv_name.setTextSize(30);
                        tv_name.setText(rs_name); TextView tv_state = new TextView(this);
                        if(rs_state == 1){
                            tv_name.setBackgroundColor(Color.parseColor("#aaff88"));
                        }
                        else{
                            tv_name.setBackgroundColor( Color.RED);

                        }
                        view_menus.addView(tv_state);
                        tv_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TextView selectMenu = (TextView) view;

                                rs_name = selectMenu.getText().toString();

                                Intent intent = new Intent(RestaurantView.this, Restaurant_menuActivity.class);
                                intent.putExtra("rs_name", rs_name);
                                intent.putExtra("customer_email", customer_email);
                                startActivity(intent);
                            }

                        });

                        TextView tv_phone = new TextView(this);
                        tv_phone.setWidth(300);
                        tv_phone.setLeft(10);
                        tv_phone.setHeight(60);
                        tv_phone.setGravity(Gravity.LEFT);
                        tv_phone.setTextSize(15);
                        tv_phone.setText("연락처 : "+rs_phone);

                        TextView tv_address = new TextView(this);
                        tv_address.setWidth(300);
                        tv_address.setLeft(10);
                        tv_address.setHeight(60);
                        tv_address.setGravity(Gravity.LEFT);
                        tv_address.setTextSize(15);
                        tv_address.setText("주소 : "+rs_address);

                        TextView tv_intro = new TextView(this);
                        tv_intro.setWidth(300);
                        tv_intro.setLeft(10);
                        tv_intro.setHeight(60);
                        tv_intro.setGravity(Gravity.LEFT);
                        tv_intro.setTextSize(15);
                        tv_intro.setText("사장님의 한마디 : " + rs_intro);

                        TextView tv_open = new TextView(this);
                        tv_open.setWidth(300);
                        tv_open.setHeight(60);
                        tv_open.setLeft(10);
                        tv_open.setGravity(Gravity.LEFT);
                        tv_open.setTextSize(15);
                        tv_open.setText("오픈시간 : "+rs_open);

                        TextView tv_close = new TextView(this);
                        tv_close.setWidth(300);
                        tv_close.setHeight(60);
                        tv_close.setLeft(10);
                        tv_close.setGravity(Gravity.LEFT);
                        tv_close.setTextSize(15);
                        tv_close.setText("마감시간 : " + rs_close);

                        TextView tv_owner = new TextView(this);
                        tv_owner.setWidth(300);
                        tv_owner.setHeight(60);
                        tv_owner.setLeft(10);
                        tv_owner.setGravity(Gravity.LEFT);
                        tv_owner.setTextSize(15);
                        tv_owner.setText("사장님 : "+rs_owner);





                        view_menus.addView(tv_name);
                        view_menus.addView(tv_phone);
                        view_menus.addView(tv_address);
                        view_menus.addView(tv_intro);
                        view_menus.addView(tv_open);
                        view_menus.addView(tv_close);
                        view_menus.addView(tv_owner);


                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }





    }





}





