package com.nobell.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nobell.user.model.HttpConnector;

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

public class Fragment1 extends Fragment{
    ViewGroup viewGroup;
    TextView fragment_tv,tv_menu1,tv_price1;
    private String result_menu;
    private int rs_id;
    private int menu_rs_id;
    private String menu_name;
    private String menu_price;
    String rs_name;
    String data_m;
    int id;
    private LinearLayout view_menus1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        Bundle bundle1 = getArguments();

        //번들 안의 텍스트 불러오기
        rs_name = bundle1.getString("rs_name");
        Log.e("RECV DATA", rs_name);
        view_menus1 = viewGroup.findViewById(R.id.view_menus1);

        String param ="&rs_name=" + rs_name;
        // Making AsyncTask For Server
        HttpConnector conn = new HttpConnector();
        data_m = conn.httpConnect(param,"/res_menu","POST");


        try {
            JSONArray jarray = new JSONArray(data_m);
            for (int i = 0; i < jarray.length(); i++){

                JSONObject jsonObject = jarray.getJSONObject(i);
                rs_id = jsonObject.getInt("menu_rs_id");
                menu_name = jsonObject.getString("menu_name");
                menu_price = jsonObject.getString("menu_price");

                Log.e("RECV DATA", menu_name);
                Log.e("RECV DATA", menu_price);

                TextView tv_menu1 = new TextView(this.getActivity());
                tv_menu1.setWidth(200);
                tv_menu1.setHeight(120);
                tv_menu1.setTextSize(30);
                tv_menu1.setText(menu_name);

                TextView tv_price1 = new TextView(this.getActivity());
                tv_price1.setWidth(100);
                tv_price1.setHeight(120);
                tv_price1.setTextSize(20);
                tv_price1.setText(menu_price);


                view_menus1.addView(tv_menu1);
                view_menus1.addView(tv_price1);



            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


        return viewGroup;
    }


}

