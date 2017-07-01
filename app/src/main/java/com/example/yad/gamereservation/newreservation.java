package com.example.yad.gamereservation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.HashMap;

public class newreservation extends AppCompatActivity {
    String f,b,c,d,g,x,url;
    final Context context = this;
    String message;
    TextView o1,o2,o3,o4,o5,o6;
    private String TAG = newreservation.class.getSimpleName();

    private ProgressDialog pDialog;
    Button lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newreservation);
        setupNavigationView();
        o1 = (TextView) findViewById(R.id.gamestation);
        o2 = (TextView) findViewById(R.id.date);
        o3 = (TextView) findViewById(R.id.day);
        o4=(TextView) findViewById(R.id.starttime) ;
        o5=(TextView) findViewById(R.id.endtime) ;
        o6=(TextView) findViewById(R.id.message) ;
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent=getIntent();
        f= intent.getStringExtra("GameStation");
        b=intent.getStringExtra("Date");
        c=intent.getStringExtra("Day");
        d=intent.getStringExtra("StartTime");
        g=intent.getStringExtra("EndTime");
        x=intent.getStringExtra("Message");

        o1.setText(f);
        o2.setText(b);
        o3.setText(c);
        o4.setText(d);
        o5.setText(g);
        o6.setText(x);
        actionBar.setTitle("Availability results");
        setupNavigationView();}











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
    public void reserve(View view){
        message();
    }


    protected void message() {

        AlertDialog.Builder alert = new AlertDialog.Builder(
                newreservation.this);
        alert.setTitle("Reserve");
        alert.setMessage("Do you want to reserve this?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp1=getSharedPreferences("grewal",0);

                String id=sp1.getString("username", null);
                url="http://192.168.1.8:8080/gameservervation/cegepgim/gamereservation/newreservations&"+b+"&"+id+"&"+f+"&"+d+"&"+g+"&"+c;
                new MyTask().execute();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }
private class MyTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(newreservation.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... params) {
        URL url1 = null;


        try {
            url1 = new URL(url);

            HttpURLConnection client = null;
            client = (HttpURLConnection) url1.openConnection();
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



            JSONObject obj = new JSONObject(response.toString());
            String status=obj.getString("Status");

            if (status.equals("ok")) {

                    String o2 = obj.getString("ReservationDate");
                    String o3 = obj.getString("GameStation");
                    String o4 = obj.getString("StartTime");
                    String o5 = obj.getString("EndTime");
                    String o6 = obj.getString("DayName");
                    message=obj.getString("Message");

                Intent intent=new Intent(getApplicationContext(),viewreservations.class);
                startActivity(intent);
            }
            else {
                message=obj.getString("Message");
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                message,
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Json parsing error: " + e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG)
                .show();

}

}


        }



