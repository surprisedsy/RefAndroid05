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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jeh80.refandroid05.AppController;
import com.example.jeh80.refandroid05.R;
import com.example.jeh80.refandroid05.User.SharedPrefManager;
import com.example.jeh80.refandroid05.User.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String url = "http://192.168.1.124:7777/refrigerator/member";

    EditText username, pass;
    Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        loginbutton = (Button) findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
                Log.d("cccccc", "id: " + username.getText().toString() + "\n" + "pass: " + pass.getText().toString() );
            }
        });
    }

    private void userlogin()
    {
        final String id = username.getText().toString();
        final String pw = pass.getText().toString();

        if(TextUtils.isEmpty(id))
        {
            username.setError("Please enter your Id");
            username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pw))
        {
            pass.setError("Please enter your Password");
            pass.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject obj = new JSONObject(response);

                            if(!obj.getBoolean("error"))
                            {
                                //JSONObject userJson = obj.getJSONObject("user");

                                UserInfo user = new UserInfo(
                                        obj.getString("ID"),
                                        obj.getString("pass")
                                );

                                SharedPrefManager.getmInstance(getApplicationContext()).userLogin(user);

                                finish();
                                startActivity(new Intent(getApplicationContext(), RefListActivity.class));

                            } else {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", id);
                params.put("pass", pw);

                return params;
            }
        };

        AppController.getAppInstance().addToRequestQueue(stringRequest);
    }

}
