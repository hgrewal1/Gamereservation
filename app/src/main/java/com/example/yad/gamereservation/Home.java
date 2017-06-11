package com.example.yad.gamereservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Home extends AppCompatActivity {
    TextView out1, out2, out3, out4,out5,out6,out7,out8;
    String r; String t;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        out1 = (TextView) findViewById(R.id.textView1);
        out2 = (TextView) findViewById(R.id.textView2);
        out3 = (TextView) findViewById(R.id.textView3);
        out4 = (TextView) findViewById(R.id.textView4);
        out5 = (TextView) findViewById(R.id.textView5);

        SharedPreferences sp1=getSharedPreferences("Login",0);

        r=sp1.getString("role", null);


        new MyTask().execute();
        setupNavigationView();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

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


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);
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
                                Intent intent5=new Intent(getApplicationContext(),avialability.class);
                                startActivity(intent5);
                                break;
                        }

                        return false;
                    }
                });
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {
        String o1, o2, o3, o4,o5,o6,o7,o8;

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            SharedPreferences sp1=getSharedPreferences("Login",0);


            String s = sp1.getString("Psw", null);

            try {
                url = new URL("http://144.217.163.57:8080/cegepgim/mobile/tutorials/login&"+r+"&"+s);

                HttpURLConnection client = null;
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();
                System.out.println("\n Sending 'GET' request to url:" + url);
                System.out.println("\n Response code:" + responseCode);
                InputStreamReader myInput = new InputStreamReader(client.getInputStream());
                BufferedReader in = new BufferedReader(myInput);
                String inputline;
                StringBuffer response = new StringBuffer();

                while ((inputline = in.readLine()) != null) {
                    response.append(inputline);
                }
                in.close();
                //print rsult
                System.out.println(response.toString());
                JSONObject obj = new JSONObject(response.toString());
                o1 = obj.getString("Status");
                if(o1.equals("ok")){
                    o2 = obj.getString("UserId");
                    t = obj.getString("FirstName");
                    o4 = obj.getString("LastName");
                    o5 = obj.getString("Email");}



               else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            out1.setText(o1);
            out2.setText(o2);
            out3.setText(t);
            out4.setText(o4);
            out5.setText(o5);

            ActionBar actionBar=getSupportActionBar();

            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(t);
            super.onPostExecute(result);



        }


    }


}

