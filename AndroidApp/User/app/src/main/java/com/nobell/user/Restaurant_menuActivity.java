package com.nobell.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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


public class Restaurant_menuActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    private String result_menu;
    private String rs_name;
    private String menu_rs_id;
    private String menu_name;
    private String menu_price;
    int rs_id;
    private String customer_email;
    String data_m;
    int id;
    private LinearLayout view_menus2;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        // 제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
         getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment1).commitAllowingStateLoss();

        //MainActivity의 인텐트를 받아서 text값을 저장
        Intent intent = getIntent();
        rs_name = intent.getStringExtra("rs_name");
        customer_email = intent.getStringExtra("customer_email");
        Log.e("USER", customer_email);
        Log.e("rs_name", rs_name);

        //번들객체 생성, text값 저장
        Bundle bundle1 = new Bundle();
        bundle1.putString("rs_name",rs_name);

        Bundle bundle2 = new Bundle();
        bundle2.putString("rs_name",rs_name);
        bundle2.putString("customer_email",customer_email);

        Bundle bundle3 = new Bundle();
        bundle2.putString("rs_name",rs_name);
        bundle3.putString("customer_email",customer_email);

        String sb = "";
        //fragment1로 번들 전달
        fragment1.setArguments(bundle1);
        fragment2.setArguments(bundle2);
        fragment3.setArguments(bundle3);



        // bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        { @Override public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                case R.id.tab1:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_layout,fragment1).commitAllowingStateLoss();

                    return true;
                }
                case R.id.tab2:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_layout,fragment2).commitAllowingStateLoss();
                    return true;
                }
                case R.id.tab3:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_layout,fragment3).commitAllowingStateLoss();
                    return true;
                }
                default: return false;
            }
        }

        });
    }








}

