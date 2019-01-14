package com.example.jeh80.refandroid05.ItemList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeh80.refandroid05.R;

public class FragmentItemTab1 extends Fragment {

    private TextView nameTxt, dateTxt, amountTxt;

    public FragmentItemTab1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        Bundle bundle = getArguments();

        if(bundle == null)
        {
            Toast.makeText(getContext(), "tab1 null", Toast.LENGTH_SHORT).show();
//            Log.d("bbbbbbbb", "tab1 null bundle: " + bundle);
        }
        if(bundle != null)
        {
            Log.d("bbbbbbbb", "tab1 Not null bundle: " + bundle);

            nameTxt = (TextView) view.findViewById(R.id.nameTxt);
            dateTxt = (TextView) view.findViewById(R.id.dateTxt);
            amountTxt = (TextView) view.findViewById(R.id.amountTxt);

            String name = bundle.getString("name");
            String date = bundle.getString("edate");
            int amount = bundle.getInt("amount");

            nameTxt.setText(name);
            dateTxt.setText(date);
            amountTxt.setText(String.valueOf(amount));

        }

        return view;
    }
}
