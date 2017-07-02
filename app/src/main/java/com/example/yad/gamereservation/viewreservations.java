package com.example.yad.gamereservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class viewreservations extends AppCompatActivity {

    private String TAG = viewreservations.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    String s,message;
    Date dt;

    // URL to get contacts JSON


    ArrayList<HashMap<String, String>> arylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreservations);

        arylist = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        actionBar.setTitle("Reservations");
        new MyTask().execute();
        setupNavigationView();


        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, android.view.View view, int arg2,
                                    long arg3) {
                Intent i = new Intent(getApplicationContext(), cancelreservation.class);
                String f= ((TextView) view.findViewById(R.id.edittext1)).getText().toString();
                String g= ((TextView) view.findViewById(R.id.edittext2)).getText().toString();
                String h= ((TextView) view.findViewById(R.id.edittext3)).getText().toString();
                String k= ((TextView) view.findViewById(R.id.edittext4)).getText().toString();
                String j= ((TextView) view.findViewById(R.id.edittext5)).getText().toString();


                i.putExtra("date",g);
                i.putExtra("day", h);
                i.putExtra("start", k);
                i.putExtra("end", j);
                i.putExtra("gs", f);

                startActivity(i);
            }
        });
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
        MenuItem menuItem=menu.getItem(1);
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



    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(viewreservations.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            SharedPreferences sp1=getSharedPreferences("grewal",0);

            String r=sp1.getString("username", null);

            try {
                url = new URL("http://192.168.1.8:8080/gameservervation/cegepgim/gamereservation/viewreservations&"+r);

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



                JSONObject obj = new JSONObject(response.toString());
                String status=obj.getString("Status");
                if (status.equals("ok")) {
                    JSONArray ary = new JSONArray();
                    ary = obj.getJSONArray("Reservations");
                    for (Integer i = 0; i < ary.length(); i++) {
                        JSONObject obj1 = ary.getJSONObject(i);
                        String o2 = obj1.getString("ReservationDate");
                        String o3 = obj1.getString("GameStation");
                        String o4 = obj1.getString("StartTime");
                        String o5 = obj1.getString("EndTime");
                        String o6 = obj1.getString("DAY_NAME");

                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("ReservationDate", o2);
                        contact.put("GameStation", o3);
                        contact.put("StartTime", o4);
                        contact.put("EndTime", o5);
                        contact.put("DAY_NAME", o6);




                        // adding Array values to Array list
                        arylist.add(contact);
                    }
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
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    viewreservations.this, arylist,
                    R.layout.list_item3, new String[]{"GameStation","ReservationDate","DAY_NAME","StartTime","EndTime"
                    }, new int[]{R.id.edittext1,R.id.edittext2,R.id.edittext3,R.id.edittext4,R.id.edittext5});

            lv.setAdapter(adapter);
        }


    }

}

