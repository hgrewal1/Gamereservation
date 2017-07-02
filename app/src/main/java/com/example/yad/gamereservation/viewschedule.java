package com.example.yad.gamereservation;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class viewschedule extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String gsname,dayname;
    Spinner spinner2,spinner1;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewschedule);
        setupNavigationView();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        spinner1=(Spinner) findViewById(R.id.spinner);
        spinner2=(Spinner) findViewById(R.id.spinner2);

        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);


        actionBar.setTitle("Search");
        setupNavigationView();

        List<String> items2= new ArrayList<String>();
        items2.add("FOOTBALL GROUND 1");
        items2.add("FOOTBALL GROUND 2");
        items2.add("PINGPONG TABLE 1");
        items2.add("PINGPONGTABLE TABLE 2");
        items2.add("FOOSBALL TABLE 1");
        items2.add("BOWLING GAMESTATION 1");
        items2.add("BOWLING GAMESTAION 2");
        items2.add("SQUASH GAMESTATION ");
        items2.add("VOLLEYBALL GROUND ");
        items2.add("CRICKET GROUND 1");
        items2.add("CRICKET GROUND 2");
        items2.add("HOCKEY GROUND");
        items2.add("ICEHOCKEY GROUND");
        items2.add("LUDO TABLE 1");
        items2.add("LUDO TABLE 2");
        items2.add("BASKETBALL COURT 1");
        items2.add("BASKETBALL COURT 2");
        items2.add("CHESS TABLE 1");
        items2.add("CHESS TABLE 2");
        items2.add("BADMINTON COURT 1");
        items2.add("BADMINTON COURT 2");
        items2.add("CAROMBOARD TABLE 1");
        items2.add("CAROMBOARD TABLE 2");
        items2.add("BASEBALL GROUND");
        List<String> items3= new ArrayList<String>();
        items3.add("Sunday");
        items3.add("Monday");
        items3.add("Tuesday");
        items3.add("Wednesday");
        items3.add("Thursday");
        items3.add("Friday");
        items3.add("Saturday");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter1);




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinner1=(Spinner)parent;
        spinner2=(Spinner)parent;

        if(spinner2.getId()==R.id.spinner2) {

            dayname = parent.getSelectedItem().toString();
        }

        if (spinner1.getId()==R.id.spinner){

            gsname=parent.getSelectedItem().toString();
        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences sp1=getSharedPreferences("grewal",0);
        String s = sp1.getString("username", null);
        if(s==null){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.guest, menu);
        }else {
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
                Intent intent=new Intent(getApplicationContext(),Accounts.class);
                startActivity(intent);
                return true;
            case R.id.login:
                Intent intent2=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.signup:
                Intent intent3=new Intent(getApplicationContext(),signup.class);
                startActivity(intent3);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(4);
        menuItem.setChecked(true);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_Search:
                                Intent intent=new Intent(getApplicationContext(),search.class);
                                startActivity(intent);
                                break;
                            case R.id.action_categories:
                                Intent intent1=new Intent(getApplicationContext(),categories.class);
                                startActivity(intent1);
                                break;
                            case R.id.action_home:
                                Intent intent3=new Intent(getApplicationContext(),Home.class);
                                startActivity(intent3);
                                break;
                            case R.id.action_locations:
                                Intent intent4=new Intent(getApplicationContext(), location.class);
                                startActivity(intent4);
                                break;
                            case R.id.action_availabilties:
                                Intent intent5=new Intent(getApplicationContext(),availability.class);
                                startActivity(intent5);
                                break;
                        }

                        return false;
                    }
                });}
    public void check1(View view){
Intent intent=new Intent(this,viewScheduleresults.class);
        intent.putExtra("dayname",dayname);
        intent.putExtra("gsname",gsname);
startActivity(intent);
    }

}

