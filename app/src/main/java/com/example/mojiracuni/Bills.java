package com.example.mojiracuni;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mojiracuni.ui.main.Autentication;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bills#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bills extends Fragment  {




    // TODO: Rename and change types and number of parameters
    public static Bills newInstance(String param1, String param2) {
        Bills fragment = new Bills();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    Spinner spiner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_bills, container, false);

        listView = (ListView) view.findViewById(R.id.listVievBills);

        if (Bill.ListsBills.size() == 0)
            Bill.GetBillsFromDB();

        ArrayList<String> M = new ArrayList<>();

        for (Bill bill :Bill.ListsBills) {
            M.add(bill.Market);
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item, M);
        listView.setAdapter(adapter);

        return view;
    }




}