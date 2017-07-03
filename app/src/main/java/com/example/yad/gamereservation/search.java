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
import java.util.List;

public class search extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    Spinner spinner1;
    private String TAG = search.class.getSimpleName();
    private ListView lv;
    TextView out1,out2,out3;




    ArrayList<HashMap<String, String>> arylist;
    private ProgressDialog pDialog;
    String a, url, o1, o2, o3, o4, o5,message,g,status;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupNavigationView();
        setupNavigationView();
        arylist = new ArrayList<>();
        editText=(EditText) findViewById(R.id.edittext1) ;
        lv = (ListView) findViewById(R.id.list);
        out3=(TextView) findViewById(R.id.text3);

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
        g=editText.getText().toString().toUpperCase();

        if(a.equals("Category")){
            url="http://144.217.163.57:8080/cegepgim/mobile/gamereservation/searchcategory&"+g;
        }else{
            url="http://144.217.163.57:8080/cegepgim/mobile/gamereservation/viewlocation&"+g;
        }
if(validateEmptyText()) {
    new MyTask().execute();
}
    }
    private boolean validateEmptyText() {
        boolean temp = true;
        String u = editText.getText().toString();
        if (u.matches("")) {
            Toast.makeText(search.this, "Search field is empty", Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
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
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(search.this);
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
                 status=obj.getString("Status");
                if (status.equals("ok")) {
                    JSONArray ary = new JSONArray();
                    ary = obj.getJSONArray("Gamestations");
                    for (Integer i = 0; i < ary.length(); i++) {
                        JSONObject obj1 = ary.getJSONObject(i);
                        o1 = obj1.getString("Gamestation");

                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value

                        contact.put("GameStation", o1);


                        // adding Array values to Array list
                        arylist.add(contact);
                    }
                }
                else {
                    status=obj.getString("Status");
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

            out3.setVisibility(View.VISIBLE);
            out3.setText("Gamestations");
            if (status.equals("wrong")||status.equals("error")) {
                lv.setVisibility(View.INVISIBLE);
                out3.setVisibility(View.INVISIBLE);
            }
           else if (status.equals("ok")){
                out3.setVisibility(View.VISIBLE);
                lv.setVisibility(View.VISIBLE);
                ListAdapter adapter = new SimpleAdapter(
                        search.this, arylist,
                        R.layout.list_item5, new String[]{"GameStation"
                }, new int[]{R.id.edittext1});

                lv.setAdapter(adapter);

            }
        }

    }

}
