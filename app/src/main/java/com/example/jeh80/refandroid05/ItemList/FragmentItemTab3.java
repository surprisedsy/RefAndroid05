package com.example.jeh80.refandroid05.ItemList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jeh80.refandroid05.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FragmentItemTab3 extends Fragment {

    private static final String TAB3_url = "http://192.168.1.31:7777/refrigerator/Android_edate";

    private ListView listView;
    private List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
    private ItemTab3Adapter itemAdapter;
    private RequestQueue requestQueue;

    private String key, responseKey;
    private TextView nullTxt;

    public FragmentItemTab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        requestQueue = Volley.newRequestQueue(getContext());

        listView = (ListView) view.findViewById(R.id.itemlistview3);
        itemAdapter = new ItemTab3Adapter(getActivity(), itemInfoList);
        listView.setAdapter(itemAdapter);

        edateItemListParse();

        return view;
    }

    private void edateItemListParse() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, TAB3_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        nullTxt = (TextView) getActivity().findViewById(R.id.nullText3);

                        try {
                            Iterator iterator = response.keys();
                            while (iterator.hasNext()) {
                                responseKey = (String) iterator.next();
                            }

                            if (responseKey.equals("Object")) {
                                nullTxt.setText("비어있음");
                            } else {
                                JSONArray jsonArray = response.getJSONArray("list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);

                                    Iterator iter = item.keys();
                                    List<String> keys = new ArrayList<String>();
                                    while (iter.hasNext()) {
                                        key = (String) iter.next();
                                        keys.add(key);
                                    }

                                    ItemInfo itemInfo = new ItemInfo();

                                    Date currentDate = new Date();  // 현재 날짜 + 시간
                                    SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd");    // 형태 변환
                                    Date jsonDate = strToDate.parse(item.getString("edate"));            // json으로 받아온 유통기한 날짜

                                    long diff = jsonDate.getTime() - currentDate.getTime();
                                    long diffDays = diff / (24 * 60 * 60 * 1000);
                                    long mathDiffDays = Math.abs(diffDays);

                                    if (keys.contains("edate") && diffDays < 4 && -1 < diffDays) {
                                        String name = item.getString("name");
                                        String img = item.getString("img");
                                        int amount = item.getInt("amount");
                                        String edate = item.getString("edate");

                                        itemInfo.setDate(edate);
                                        itemInfo.setName(name);
                                        itemInfo.setAmount(String.valueOf(amount) + "개");
                                        itemInfo.setImg(img);
                                        itemInfo.setEdate("유통기한 " + String.valueOf(mathDiffDays + 1) + "일 남음");
                                        nullTxt.setText("");

                                    } else {

                                        if (keys.contains("edate") && diffDays < 0) {
                                            String name = item.getString("name");
                                            String img = item.getString("img");
                                            int amount = item.getInt("amount");
                                            String edate = item.getString("edate");

                                            itemInfo.setDate(edate);
                                            itemInfo.setName(name);
                                            itemInfo.setAmount(String.valueOf(amount) + "개");
                                            itemInfo.setImg(img);
                                            itemInfo.setEdate("유통기한 " + String.valueOf(mathDiffDays) + "일 지남");
                                            nullTxt.setText("");

                                        } else {
                                            listView = null;
                                            nullTxt.setText("없음");
                                            continue;
                                        }
                                    }

                                    itemInfoList.add(itemInfo);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        itemAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
