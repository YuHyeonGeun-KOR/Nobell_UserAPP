package com.nobell.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nobell.user.model.HttpConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InfoMainActivity extends AppCompatActivity {
    private EditText edt_pwd;
    private EditText edt_pwd2;
    private EditText edt_email;
    private Button btn_upt;
    private String email;
    private String pwd;
    private String pwdcheck;
    private String result_check;
    String data_u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_main);
        edt_email = findViewById(R.id.edt_email);
        edt_pwd = findViewById(R.id.edt_pwd);
        edt_pwd2 = findViewById(R.id.edt_pwd2);
        btn_upt = findViewById(R.id.btn_upt);


        btn_upt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString();
                pwd = edt_pwd.getText().toString();
                pwdcheck = edt_pwd2.getText().toString();

                if(!pwd.equals(pwdcheck)) {
                    Toast.makeText(InfoMainActivity.this, "Two Passwords are Different", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Making AsyncTask For Server
                String param = "&update_email=" + email+ "&update_pwd=" + pwd  + "";
                // Making AsyncTask For Server
                HttpConnector conn = new HttpConnector();
                data_u = conn.httpConnect(param,"/update","POST");


                if (data_u.equals("UptOK")) {
                    Toast.makeText(InfoMainActivity.this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    intent = new Intent(InfoMainActivity.this, OpenrestActivity.class);


                    startActivity(intent);
                }
                // Fail
                else {

                        Toast.makeText(InfoMainActivity.this, "오류가 발생했습니다. ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
