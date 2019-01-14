package com.example.jeh80.refandroid05.ItemList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeh80.refandroid05.R;

public class FragmentItemTab2 extends Fragment {

    private TextView nameTxt, dateTxt, amountTxt;

    public FragmentItemTab2() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        Bundle bundle = getArguments();

        if (bundle == null) {
            Toast.makeText(getContext(), "tab2 null", Toast.LENGTH_SHORT).show();
//            Log.d("bbbbbbbb", "tab2 null bundle: " + bundle);
        }
        if (bundle != null) {
            Log.d("bbbbbbbb", "tab2 Not null bundle: " + bundle);

            nameTxt = (TextView) view.findViewById(R.id.nameTxt2);
            dateTxt = (TextView) view.findViewById(R.id.dateTxt2);
            amountTxt = (TextView) view.findViewById(R.id.amountTxt2);

            String name = bundle.getString("name");
            int date = bundle.getInt("ldate");
            int amount = bundle.getInt("amount");

            nameTxt.setText(name);
            dateTxt.setText(String.valueOf(date));
            amountTxt.setText(String.valueOf(amount));
        }

        return view;

    }
}