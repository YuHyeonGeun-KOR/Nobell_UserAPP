package com.nobell.user;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nobell.user.model.HttpConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment3 extends Fragment{
    ViewGroup viewGroup;
    private LinearLayout view_review3;
    String rs_name,review_customer,review_time,customer_email;
    int rs_id;
    private String review_content;
    String data_r,param;
    Button btn_review;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3,container,false);
        Bundle bundle = getArguments();

        //번들 안의 텍스트 불러오기
        rs_name = bundle.getString("rs_name");
        customer_email = bundle.getString("customer_email");
        Log.e("RECV DATA", rs_name);

        view_review3 = viewGroup.findViewById(R.id.view_review3);
        btn_review = view_review3.findViewById(R.id.btn_review);

        param ="&rs_name=" + rs_name;
        // Making AsyncTask For Server
        HttpConnector conn = new HttpConnector();
        data_r = conn.httpConnect(param,"/res_review","POST");

        try {
            JSONArray jarray = new JSONArray(data_r);
            for (int i = 0; i < jarray.length(); i++){

                JSONObject jsonObject = jarray.getJSONObject(i);

                rs_id = jsonObject.getInt("review_rs_id");
                review_customer = jsonObject.getString("review_customer");
                review_content = jsonObject.getString("review_content");
                review_time = jsonObject.getString("review_time");

                Log.e("RECV DATA", review_customer);

                TextView tv_review_content = new TextView(this.getActivity());
                tv_review_content.setWidth(200);
                tv_review_content.setHeight(120);
                tv_review_content.setTextSize(30);
                tv_review_content.setText(review_content);

                TextView tv_review_customer = new TextView(this.getActivity());
                tv_review_customer.setWidth(100);
                tv_review_customer.setHeight(120);
                tv_review_customer.setTextSize(20);
                tv_review_customer.setText(review_customer);

                TextView tv_review_time = new TextView(this.getActivity());
                tv_review_time.setWidth(100);
                tv_review_time.setHeight(120);
                tv_review_time.setTextSize(20);
                tv_review_time.setText(review_time);

                view_review3.addView(tv_review_content);
                view_review3.addView(tv_review_customer);
                view_review3.addView(tv_review_time);


            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater dialog = LayoutInflater.from(getActivity());
                final View dialogLayout = dialog.inflate(R.layout.activity_review, null);
                final Dialog myDialog = new Dialog(getActivity());

                myDialog.setContentView(dialogLayout);
                myDialog.show();

                btn_review = (Button) dialogLayout.findViewById(R.id.btn_review);

                btn_review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText edt_review = (EditText) dialogLayout.findViewById(R.id.edt_review);
                        String review_String = edt_review.getText().toString();
                        String param ="&review_rs_name=" + rs_name + "&customer_email=" + customer_email + "&review_content=" + review_String;
                        HttpConnector conn = new HttpConnector();
                        String data_r = conn.httpConnect(param,"/set_review","POST");
                        Toast.makeText(getActivity(), "리뷰를 작성했습니다!!", Toast.LENGTH_SHORT).show();
                        myDialog.cancel();

                    }
                });

            }
        });


        return viewGroup;
    }
}

