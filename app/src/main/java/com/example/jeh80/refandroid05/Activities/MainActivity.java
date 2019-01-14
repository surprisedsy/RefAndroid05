package com.example.jeh80.refandroid05.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.AppController;
import com.example.jeh80.refandroid05.R;
import com.example.jeh80.refandroid05.RefList.RefInfo;
import com.example.jeh80.refandroid05.User.SharedPrefManager;
import com.example.jeh80.refandroid05.User.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://192.168.1.124:7777/refrigerator/re_list";
    //private static final String url = "https://api.myjson.com/bins/lwtnc";

    EditText username, pass;
    Button loginbutton;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        loginbutton = (Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userlogin();
//                refDataResponse();
                Intent refIntent = new Intent(MainActivity.this, RefListActivity.class);
                startActivity(refIntent);
            }
        });
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

                Log.d("bbbbbbb", "stringRequest: " + response);

                refDataResponse();

                try {
                    JSONObject obj = new JSONObject(response);

                    if(!obj.getBoolean("Not Correct"))
                    {
                        JSONObject userJson = obj.getJSONObject("reflist");

                        UserInfo userInfo = new UserInfo(userJson.getString("ref_name"),
                                userJson.getString("ref_num"));

                        SharedPrefManager.getmInstance(getApplicationContext()).userLogin(userInfo);

                        finish();
                        startActivity(new Intent(getApplicationContext(), RefListActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void refDataResponse() {

        StringRequest getStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("bbbbbb", "ref data response: " + response);

                try {
                    JSONObject obj = new JSONObject(response);

                    String refname = obj.getString("ref_name");
                    String refnum = obj.getString("ref_num");

                    TextView text = (TextView) findViewById(R.id.text11);
                    text.setText(refname);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Intent refIntent = new Intent(MainActivity.this, RefListActivity.class);
//                startActivity(refIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        getStringRequest.setTag("TAG");
        requestQueue.add(getStringRequest);

    }
}
