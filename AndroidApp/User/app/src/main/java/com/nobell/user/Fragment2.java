package com.nobell.user;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fragment2 extends Fragment {
    String rs_name;
    String customer_email;
    int rs_table_number;
    ViewGroup viewGroup;
    String data_t, data_r;
    String result_table;
    String result_res;
    private LinearLayout layout_table;
    TableRow newRow;
    String headcount;
    String num;
    int count;
    String tbl_n;
    int j, k;
    int y = 0, m = 0, d = 0, h = 0, mi = 0;
    Button btn_res;
    String mm1,mm2,dd1,dd2,hh1,hh2,mmi1,mmi2;
    String CT ;

    String [] savenum = new String[100];

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date time = new Date();
    String currentTime1 = format1.format(time);

    Button[] rs_table = new Button[200];
    Button btn_visit;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);


        Bundle bundle = getArguments();

        //번들 안의 텍스트 불러오기
        rs_name = bundle.getString("rs_name");
        customer_email = bundle.getString("customer_email");
        layout_table = viewGroup.findViewById(R.id.layout_table);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

        btn_visit = viewGroup.findViewById(R.id.btn_visit3);

        String param ="&rs_name=" + rs_name;
        // Making AsyncTask For Server
        HttpConnector conn = new HttpConnector();
        data_t = conn.httpConnect(param,"/res_table","POST");



        int cnt = 0;
        try {
            JSONArray jarray = new JSONArray(data_t);


            for (j = 0; j < 10; j++) {
                newRow = new TableRow(this.getActivity());


                for (k = 0; k < 10; k++) {
                    // Menu name View
                    rs_table[10 * j + k] = new Button(this.getActivity());
                    rs_table[10 * j + k].setEnabled(false);
                    params.width = 110;
                    params.height = 110;
                    newRow.addView(rs_table[10 * j + k], params);
                    rs_table[10 * j + k].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final LayoutInflater dialog = LayoutInflater.from(getActivity());
                            final View dialogLayout = dialog.inflate(R.layout.activity_reservation, null);
                            final Dialog myDialog = new Dialog(getActivity());

                            Button b = (Button)view;
                            tbl_n = b.getText().toString();


                            myDialog.setContentView(dialogLayout);
                            myDialog.show();

                            btn_visit = (Button) dialogLayout.findViewById(R.id.btn_visit3);

                            btn_res = (Button) dialogLayout.findViewById(R.id.btn_res);


                            btn_visit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EditText edt_head2= (EditText) dialogLayout.findViewById(R.id.edt_head4);
                                    String headcount2 = edt_head2.getText().toString();

                                    String param ="&rs_name=" + rs_name +  "&tbl_n=" + tbl_n + "&customer_email=" + customer_email + "&headcount2=" + headcount2;

                                    // Making AsyncTask For Server
                                    HttpConnector conn = new HttpConnector();
                                    data_t = conn.httpConnect(param,"/res_visit","POST");

                                    Intent intent;
                                    intent = new Intent(getActivity(), OpenrestActivity.class);
                                    intent.putExtra("customer_email", customer_email);
                                    startActivity(intent);


                                    myDialog.cancel();
                                }
                            });

                            btn_res.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    EditText edt_head = (EditText) dialogLayout.findViewById(R.id.edt_head);
                                    EditText y1 = (EditText) dialogLayout.findViewById(R.id.y1);
                                    EditText d1 = (EditText) dialogLayout.findViewById(R.id.d1);
                                    EditText h1= (EditText) dialogLayout.findViewById(R.id.h1);
                                    EditText textView34= (EditText) dialogLayout.findViewById(R.id.textView34);

                                    headcount = edt_head.getText().toString();


                                    mm1 = y1.getText().toString();

                                    dd1 = d1.getText().toString();

                                    hh1 = h1.getText().toString();

                                    mmi1 = textView34.getText().toString();


                                    String param ="&rs_name=" + rs_name + "&tbl_n=" + tbl_n + "&customer_email=" + customer_email + "&headcount=" + headcount +
                                            "&M=" +mm1 + "&D=" +dd1  +"&H=" + hh1+ "&MMi=" +mmi1 +"";
                                    // Making AsyncTask For Server
                                    HttpConnector conn = new HttpConnector();
                                    data_t = conn.httpConnect(param,"/res_res","POST");

                                    myDialog.cancel();
                                }
                            });
                        }
                    });
                }

                layout_table.addView(newRow);
            }


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jsonTable = jarray.getJSONObject(cnt++);
                int table_no = jsonTable.getInt("table_no");
                int table_x = jsonTable.getInt("table_position_x");
                int table_y = jsonTable.getInt("table_position_y");
                int table_state = jsonTable.getInt("table_state");


                if (table_state == 0) {
                    rs_table[(table_x) + (10 * (table_y-1))-1].setBackgroundColor(Color.BLUE);
                    rs_table[(table_x) + (10 * (table_y-1))-1].setText( Integer.toString(table_no));
                    rs_table[(table_x) + (10 * (table_y-1))-1].setEnabled(true);

                } else if (table_state == 1) {

                    rs_table[(table_x) + (10 * (table_y-1))-1].setBackgroundColor(Color.RED);
                    rs_table[(table_x) + (10 * (table_y-1))-1].setText(Integer.toString(table_no));
                    rs_table[(table_x) + (10 * (table_y-1))-1].setEnabled(false);
                }
                else{
                    rs_table[(table_x) + (10 * (table_y-1))-1].setVisibility(View.INVISIBLE);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return viewGroup;
    }

}