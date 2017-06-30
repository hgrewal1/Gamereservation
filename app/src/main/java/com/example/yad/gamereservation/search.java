package com.example.yad.gamereservation;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class search extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner spinner1;
    private String TAG = gslocation.class.getSimpleName();
    private ListView lv;


    // URL to get contacts JSON


    ArrayList<HashMap<String, String>> arylist;
    private ProgressDialog pDialog;
    String a, url, o1, o2, o3, o4, o5;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupNavigationView();
        setupNavigationView();
        arylist = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        actionBar.setTitle("Search");
        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(this);
        Button button = (Button) findViewById(R.id.search);
        List<String> items = new ArrayList<String>();
        items.add("Location");
        items.add("Category");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinner1 = (Spinner) parent;

        a = parent.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void search(View view) {

        String h=editText.getText().toString();

       Intent intent=new Intent(this,searchresults.class);
        intent.putExtra("spinner",a);
        intent.putExtra("search",h);
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sp1 = getSharedPreferences("grewal", 0);
        String s = sp1.getString("username", null);
        if (s == null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.guest, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.account:
                Intent intent = new Intent(getApplicationContext(), Accounts.class);
                startActivity(intent);
                return true;
            case R.id.login:
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.signup:
                Intent intent3 = new Intent(getApplicationContext(), signup.class);
                startActivity(intent3);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_Search:
                                Intent intent = new Intent(getApplicationContext(), search.class);
                                startActivity(intent);
                                break;
                            case R.id.action_categories:
                                Intent intent1 = new Intent(getApplicationContext(), categories.class);
                                startActivity(intent1);
                                break;
                            case R.id.action_home:
                                Intent intent3 = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent3);
                                break;
                            case R.id.action_locations:
                                Intent intent4 = new Intent(getApplicationContext(), location.class);
                                startActivity(intent4);
                                break;
                            case R.id.action_availabilties:
                                Intent intent5 = new Intent(getApplicationContext(), availability.class);
                                startActivity(intent5);
                                break;
                        }

                        return false;
                    }
                });
    }
}