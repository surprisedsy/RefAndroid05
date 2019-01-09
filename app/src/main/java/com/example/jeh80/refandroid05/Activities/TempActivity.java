package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private static final String url = "http://192.168.1.124:7777/refrigerator/tem_json";

    private TextView tempResult;
    private RequestQueue requestQueue;

    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        Init();
        tempJsonParse();
        btnMapping();
    }

    private void Init()
    {
        tempResult = (TextView) findViewById(R.id.tempTxt);
        requestQueue = Volley.newRequestQueue(this);

        btn1 = (Button) findViewById(R.id.btn_itemlist);
        btn2 = (Button) findViewById(R.id.btn_photo);
    }

    private void tempJsonParse()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String temp = response.getString("tem");

                            tempResult.setText(temp + "℃");

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

    private void btnMapping()
    {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemIntent = new Intent(TempActivity.this, ItemListActivity.class);
                startActivity(itemIntent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(TempActivity.this, PhotoActivity.class);
                startActivity(photoIntent);
            }
        });
    }
}
