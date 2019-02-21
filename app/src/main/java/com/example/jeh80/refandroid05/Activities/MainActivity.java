package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.AppController;
import com.example.jeh80.refandroid05.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    private static final String url = "http://192.168.1.31:7777/refrigerator/Android_reflist";
    private static final String url = "http://192.168.1.124:7777/refrigerator/Android_reflist";

    EditText username, pass;
    Button loginbutton;

    RequestQueue requestQueue;
    Intent refIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });
    }

    private void Init()
    {
        requestQueue = Volley.newRequestQueue(this);

        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        loginbutton = (Button) findViewById(R.id.loginbutton);
    }

    private void userlogin() {

        final String id = username.getText().toString();
        final String pw = pass.getText().toString();

        if (TextUtils.isEmpty(id)) {
            username.setError("Please enter your Id");
            username.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pw)) {
            pass.setError("Please enter your Password");
            pass.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String error = response.substring(2, 11);
                if (!error.equals("LoginFail")) {
                    refIntent = new Intent(MainActivity.this, RefListActivity.class);
                    refIntent.putExtra("data", response);
                    startActivity(refIntent);
                } else {
                    Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pass", pw);
                params.put("ID", id);

                return params;
            }
        };
        AppController.getAppInstance().addToRequestQueue(stringRequest);
    }
}
