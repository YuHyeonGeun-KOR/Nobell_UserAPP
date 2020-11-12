package com.nobell.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nobell.user.model.HttpConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderActivity extends AppCompatActivity {
    LinearLayout view_menus2[] = new LinearLayout[200];
    TextView tv_menu2[][] =  new TextView[200][200];
    EditText tv_count[] =  new EditText[200];
    int[] number = new int[200];
    String[] menu_name = new String[200];
    String[] menu_price = new String[200];
    Button btn_order;
    String json;
    int rs_id;
    int table_no;
    String menu , price;
    JSONArray jArray1 = new JSONArray();
    JSONObject jsonMain = new JSONObject();
    String customer_email;
    int order_count= 0;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        customer_email = intent.getStringExtra("customer_email");
        rs_id = intent.getIntExtra("rs_id",0);
        table_no = intent.getIntExtra("table_no",0);

        String param ="&rs_id=" + rs_id;
        // Making AsyncTask For Server
        HttpConnector conn = new HttpConnector();
        String data_m = conn.httpConnect(param,"/res_menu2","POST");


        try {
            JSONArray jarray = new JSONArray(data_m);
            for (i = 0; i < jarray.length(); i++){

                JSONObject jsonObject = jarray.getJSONObject(i);
                int rs_id2 = jsonObject.getInt("menu_rs_id");
                menu_name[i] = jsonObject.getString("menu_name");
                menu_price[i] = jsonObject.getString("menu_price");

                Log.e("RECV DATA", menu_name[i]);
                Log.e("RECV DATA", menu_price[i]);
                order_count +=1;
            }

            for (int j = 0; j < jarray.length(); j++) {
                view_menus2[j]= (LinearLayout) findViewById(R.id.view_menus2);

                String key = String.valueOf(j);

                tv_menu2[j][0] = new TextView(this);
                tv_menu2[j][0].setWidth(200);
                tv_menu2[j][0].setHeight(120);
                tv_menu2[j][0].setTextSize(30);
                tv_menu2[j][0].setHint(key);
                tv_menu2[j][0].setText(menu_name[j] + "X" +number[j] + "개");




                tv_menu2[j][1] = new TextView(this);
                tv_menu2[j][1].setWidth(100);
                tv_menu2[j][1].setHeight(120);
                tv_menu2[j][1].setTextSize(20);
                tv_menu2[j][1].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                tv_menu2[j][1].setText(menu_price[j]);


                tv_menu2[j][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView M = (TextView)view;
                        String c = M.getHint().toString();
                        int count = Integer.parseInt(c);
                        number[count] += 1;
                        tv_menu2[count][0].setText( menu_name[count] + "X" +number[count] +  "개" );

                    }
                });

                view_menus2[j].addView(tv_menu2[j][0]);

                view_menus2[j].addView(tv_menu2[j][1]);

                btn_order = findViewById(R.id.btn_order);


                btn_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            JSONObject object = new JSONObject();
                            try {
                                // Create a new instance of a JSONObject
                                for (int c = 0; c < order_count; c++) {

                                    ;
                                    if (number[c] != 0) {
                                        // With put you can add a name/value pair to the JSONObject
                                        object.put(menu_name[c], number[c]);
                                        // Calling toString() on the JSONObject returns the JSON in string format.
                                    }
                                }
                            }
                                catch (JSONException e) {
                                e.printStackTrace();
                            }

                        String check = object.toString();
                        Log.e("RECV order_json", check);
                        String param ="&order_rs_id=" + rs_id + "&order_table=" + table_no + "&order_json=" +object ;
                        // Making AsyncTask For Server
                        HttpConnector conn4 = new HttpConnector();
                        String data_m = conn4.httpConnect(param,"/order","POST");

                        Intent intent3 = new Intent(OrderActivity.this, OpenrestActivity.class);
                        intent3.putExtra("customer_email", customer_email);
                        startActivity(intent3);

                    }



                });

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}