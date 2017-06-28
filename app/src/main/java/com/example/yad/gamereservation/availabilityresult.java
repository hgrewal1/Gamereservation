package com.example.yad.gamereservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class availabilityresult extends AppCompatActivity {
    String f,b,c,d,g,x;
    TextView o1,o2,o3,o4,o5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availabilityresult);
        setupNavigationView();
        o1 = (TextView) findViewById(R.id.gamestation);
        o2 = (TextView) findViewById(R.id.date);
        o3 = (TextView) findViewById(R.id.day);
        o4=(TextView) findViewById(R.id.starttime) ;
        o5=(TextView) findViewById(R.id.endtime) ;
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent=getIntent();
        f= intent.getStringExtra("GameStation");
        b=intent.getStringExtra("Date");
        c=intent.getStringExtra("Day");
        d=intent.getStringExtra("StartTime");
        g=intent.getStringExtra("EndTime");
        x=intent.getStringExtra("Message");
        String string = d;
        String[] parts = string.split(":");

        String part1 = parts[0];
        String part2 = parts[1];
        String h=part1+part2;
        o1.setText(f);
        o2.setText(b);
        o3.setText(c);
        o4.setText(d);
        o4.setText(g);
        o5.setText(x);
        actionBar.setTitle("Grewal");
        setupNavigationView();
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
                });
    }
}