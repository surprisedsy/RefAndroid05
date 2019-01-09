package com.example.jeh80.refandroid05.ItemList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeh80.refandroid05.R;

public class FragmentItemTab1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        Bundle bundle = getArguments();
//        String name = bundle.getString("name");
//
//        Log.d("bbbbb", "fragment: " + name);


        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }
}
