package com.example.yad.gamereservation;

import android.app.ProgressDialog;
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
import java.util.ArrayList;
import java.util.HashMap;

public class cancelreservation extends AppCompatActivity {

    private String TAG = categories.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    String s,g,message;
    ListAdapter adapter;
    // URL to get contacts JSON


    ArrayList<HashMap<String, String>> arylist;
    Button button;
    String gs,date,day,start,end,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelreservation);
        button=(Button)findViewById(R.id.cancel) ;
        arylist = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);


        actionBar.setTitle("Cancel Reservation");
        setupNavigationView();
        Intent intent=getIntent();
       gs= intent.getStringExtra("gs");
        day= intent.getStringExtra("day");
        date= intent.getStringExtra("date");
        start= intent.getStringExtra("start");
        end= intent.getStringExtra("end");

        HashMap<String, String> contact = new HashMap<>();

        // adding each child node to HashMap key => value
        contact.put("g", gs);
        contact.put("r", day);
        contact.put("e", date);
        contact.put("w", start);
        contact.put("a", end);



        // adding Array values to Array list
        arylist.add(contact);

        adapter = new SimpleAdapter(
                cancelreservation.this, arylist,
                R.layout.list_item3, new String[]{"g","r","e","w","a"}, new int[]{R.id.edittext1,R.id.edittext2,R.id.edittext3,R.id.edittext4,R.id.edittext5});

        lv.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeItemFromList();


            }
        });
    }




    protected void removeItemFromList() {

        AlertDialog.Builder alert = new AlertDialog.Builder(
                cancelreservation.this);

        alert.setTitle("Cancel Reservation");
        alert.setMessage("Do you want to Cancel this Reservation?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences sp1=getSharedPreferences("grewal",0);

                String id=sp1.getString("username", null);
                url="http://144.217.163.57:8080/cegepgim/mobile/gamereservation/cancelreservations&"+date+"&"+id+"&"+gs+"&"+start+"&"+end+"&"+day;
                new MyTask().execute();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

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
            pDialog = new ProgressDialog(cancelreservation.this);
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
                myInput.close();


                JSONObject obj = new JSONObject(response.toString());
                String status=obj.getString("Status");
                if (status.equals("ok")) {

                    message = obj.getString("Message");

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
                client.disconnect();
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

                        Toast.makeText(getApplicationContext(),
                                message,
                                Toast.LENGTH_LONG)
                                .show();



        }


    }

}

