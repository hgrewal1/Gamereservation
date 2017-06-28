package com.example.yad.gamereservation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

public class Accounts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Account");
        setupNavigationView();
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void viewprofile(View view) {
        Intent intent = new Intent(this, viewprofile.class);
        startActivity(intent);
    }
    public void changepassword(View view) {
        Intent intent = new Intent(this, changepassword.class);
        startActivity(intent);
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        menuItem.isEnabled();
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