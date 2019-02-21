package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TempActivity extends AppCompatActivity {

//    private static final String url = "http://192.168.1.31:7777/refrigerator/Android_tem";
    private static final String url = "http://192.168.1.124:7777/refrigerator/Android_tem";

    private TextView tempResult;
    private RequestQueue requestQueue;

    private Button btn1, btn2;
    private ProgressBar progressBar;

    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        Init();
        tempJsonParse();
        tempAlert();
        btnMapping();
    }

    private void Init()
    {
        tempResult = (TextView) findViewById(R.id.tempTxt);
        requestQueue = Volley.newRequestQueue(this);

        btn1 = (Button) findViewById(R.id.btn_itemlist);
        btn2 = (Button) findViewById(R.id.btn_photo);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void tempJsonParse()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            temp = response.getString("tem");

                            tempResult.setText(temp + "℃");
                            progressBar.setProgress(Integer.valueOf(temp));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void tempAlert()
    {
        if(Integer.valueOf(temp) > 30)
        {
            AlertDialog.Builder alertbuiler = new AlertDialog.Builder(TempActivity.this);
            alertbuiler.setTitle("경고!!!!!")
                    .setMessage("냉장고 온도가 " + temp + "℃ 로 올랐습니다.\n냉장고를 확인 해주세요.")
                    .setPositiveButton("확인", null)
                    .setNeutralButton("취소", null)
                    .create()
                    .show();
        }
    }

    private void btnMapping()
    {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemIntent = new Intent(TempActivity.this, ItemListActivity.class);
                startActivity(itemIntent);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(TempActivity.this, PhotoActivity.class);
                startActivity(photoIntent);
                finish();
            }
        });
    }
}
