package com.nobell.user.model;

import android.os.AsyncTask;
import android.util.Log;

import com.nobell.user.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnector {
    public String ipAddr = "http://172.20.10.2:3000/customer";
    public String myurl = "";
    public String myParams;
    public String mymethod;
    public String data_l;
    public String result_login;

    public String httpConnect(String params, String url, String method) {
        myParams = params;
        myurl = url;
        mymethod = method;
        LoginTask logintask = new LoginTask();
        try {
            data_l = logintask.execute().get().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_l;
    }

    public class LoginTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... unused) {

            String param = myParams;
            try {

                URL url = new URL(ipAddr + myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod(mymethod);
                conn.setDoInput(true);
                conn.connect();

                if(mymethod.equals("POST")) {
                    OutputStream outs = conn.getOutputStream();
                    outs.write(param.getBytes("UTF-8"));
                    outs.flush();
                    outs.close();
                }


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
                result_login = buff.toString();
                data_l = result_login.trim();
                Log.e("RECV DATA", data_l);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data_l;
        }

    }
}
