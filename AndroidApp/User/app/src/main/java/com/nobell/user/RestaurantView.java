package com.nobell.user;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);
        btn_res = findViewById(R.id.btn_res);

        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Making AsyncTask For Server
                RestaurantView.Listtask Listtask = new RestaurantView.Listtask();

                // Execute LoginTask
                try {
                    data_r = Listtask.execute().get().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                StringBuffer sb = new StringBuffer("");

                try {

                    JSONArray jarray = new JSONArray(data_r);
                    Intent intent = new Intent(RestaurantView.this, ItemList.class);
                    intent.putExtra("restaurant",data_r);
                    startActivity(intent);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }






    // 데이터 불러오기
    public class Listtask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... unused) {


            String param = "";

            try {

                URL url = new URL("http://172.20.10.2:3000/user/res_View");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();


                InputStream is = null;
                BufferedReader in = null;


                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }
                result_restaurant = buff.toString();
                data_r = result_restaurant.trim();
                Log.e("RECV List", data_r);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data_r;
        }

    }



}





