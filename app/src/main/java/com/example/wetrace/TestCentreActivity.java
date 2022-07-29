package com.example.wetrace;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class TestCentreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] cities = {"Mumbai", "Navi Mumbai", "Pune", "Nagpur", "Thane"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_centre);
        Spinner sp = findViewById(R.id.spinner);
        listView = findViewById(R.id.listView);
        listView.setDividerHeight(12);
        listView.setPadding(0, 8, 0, 8);
        sp.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ad);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        listView.setAdapter(null);
        switch (position) {
            case 0:
                String[] arrMumbai;
                arrMumbai = getResources().getStringArray(R.array.mumbai);
                final ArrayAdapter<String> mumbai = new ArrayAdapter<String>(this, R.layout.listview_custom_layout, arrMumbai);
                listView.setAdapter(mumbai);
                mumbai.notifyDataSetChanged();
                break;
            case 1:
                String[] arrNavi;
                arrNavi = getResources().getStringArray(R.array.navi);
                final ArrayAdapter<String> navi = new ArrayAdapter<String>(this, R.layout.listview_custom_layout, arrNavi);
                listView.setAdapter(navi);
                navi.notifyDataSetChanged();
                break;
            case 2:
                String[] arrPune;
                arrPune = getResources().getStringArray(R.array.pune);
                final ArrayAdapter<String> pune = new ArrayAdapter<String>(this, R.layout.listview_custom_layout, arrPune);
                listView.setAdapter(pune);
                pune.notifyDataSetChanged();
                break;
            case 3:
                String[] arrNagpur;
                arrNagpur = getResources().getStringArray(R.array.nagpur);
                final ArrayAdapter<String> nagpur = new ArrayAdapter<String>(this, R.layout.listview_custom_layout, arrNagpur);
                listView.setAdapter(nagpur);
                nagpur.notifyDataSetChanged();
                break;
            case 4:
                String[] arrThane;
                arrThane = getResources().getStringArray(R.array.thane);
                final ArrayAdapter<String> thane = new ArrayAdapter<String>(this, R.layout.listview_custom_layout, arrThane);
                listView.setAdapter(thane);
                thane.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}