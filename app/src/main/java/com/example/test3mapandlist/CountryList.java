package com.example.test3mapandlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class CountryList extends AppCompatActivity {

    ListView list;
    String[] countries = {"Argentina", "Brazil", "Canada", "China", "France", "Germany", "India", "Italy", "Malaysia", "Mexico", "Pakistan", "Spain", "Sweden", "Turkey", "United Kingdom","USA"};
    String personName, personGender, personDob, personCountry = "", personLat, personLng, personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personName = getIntent().getStringExtra("nameSent");
        personGender = getIntent().getStringExtra("genderSent");
        personDob = getIntent().getStringExtra("birthdaySent");
        personCountry = getIntent().getStringExtra("countrySent");
        personLat = getIntent().getStringExtra("latSent");
        personLng = getIntent().getStringExtra("lngSent");
        personId = getIntent().getStringExtra("idSent");


        setContentView(R.layout.country_list);
        list = findViewById(R.id.countryListView);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,countries);
        list.setAdapter(listViewAdapter);

        // Onclick on an item in the recyclerview
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), countries[i], Toast.LENGTH_LONG).show();
                UpdateActivity.countrySelected = countries[i];
                Intent intent = new Intent(CountryList.this, UpdateActivity.class);
                intent.putExtra("nameSent", personName);
                intent.putExtra("birthdaySent", personDob);
                intent.putExtra("countrySent", personCountry);
                intent.putExtra("genderSent", personGender);
                intent.putExtra("latSent", personLat);
                intent.putExtra("lngSent",personLng);
                intent.putExtra("idSent", personId);
                startActivity(intent);
            }
        });
    }
}