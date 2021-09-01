package com.example.mojiracuni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mojiracuni.ui.main.Autentication;

import java.util.ArrayList;


public class All extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView textPrice;
    ListView listView;
    Spinner spiner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_all, container, false);
        spiner = view.findViewById(R.id.spinner);
        textPrice = (TextView) view.findViewById(R.id.textView7);
        textPrice.setText(" dfk ");

        spiner.setOnItemSelectedListener(this);

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String ChousenDate = parent.getItemAtPosition(position).toString();

        Bill b = new Bill();
        Double price = b.GetDataForDate(ChousenDate);


        textPrice.setText(price.toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}