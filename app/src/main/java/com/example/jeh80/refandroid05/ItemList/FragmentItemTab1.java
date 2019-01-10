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

public class FragmentItemTab1 extends Fragment {

    private TextView nameTxt, dateTxt, amountTxt;

    public FragmentItemTab1() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        Bundle bundle = getArguments();

        Log.d("cccccccc", "fragment bundle: " + bundle);

        if(bundle == null)
        {
            Toast.makeText(getActivity(), "bundle is null", Toast.LENGTH_SHORT).show();
        }
        if(bundle != null)
        {
            Toast.makeText(getActivity(), "not null: " + bundle, Toast.LENGTH_SHORT).show();

            nameTxt = (TextView) view.findViewById(R.id.nameTxt);
            dateTxt = (TextView) view.findViewById(R.id.dateTxt);
            amountTxt = (TextView) view.findViewById(R.id.amountTxt);

            //Bundle bundle = getArguments();

            String name = bundle.getString("1");
            String date = bundle.getString("2");
            int amount = bundle.getInt("3");

            Log.d("cccccccc", name + ", " + date + ", " + amount);

            nameTxt.setText(name);
            dateTxt.setText(date);
            amountTxt.setText(String.valueOf(amount));
        }

//        nameTxt = (TextView) view.findViewById(R.id.nameTxt);
//        dateTxt = (TextView) view.findViewById(R.id.dateTxt);
//        amountTxt = (TextView) view.findViewById(R.id.amountTxt);
//
//        Bundle bundle = getArguments();
//
//        String name = bundle.getString("1");
//        String date = bundle.getString("2");
//        int amount = bundle.getInt("3");
//
//        Log.d("cccccccc", name + ", " + date + ", " + amount);
//
//        nameTxt.setText(name);
//        dateTxt.setText(date);
//        amountTxt.setText(String.valueOf(amount));

        return view;
    }
}
