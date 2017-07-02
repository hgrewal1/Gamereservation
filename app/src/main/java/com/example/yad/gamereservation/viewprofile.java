package com.example.yad.gamereservation;

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

public class viewprofile extends AppCompatActivity {
    private String TAG = gslocation.class.getSimpleName();
    URL url = null;
    private ProgressDialog pDialog;
    TextView firstname,lastname,email,phonenumber;
    String o1,o2,o3,o4,url1,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        setupNavigationView();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Profile");
        setupNavigationView();
        firstname = (TextView) findViewById(R.id.firstname);
        lastname = (TextView) findViewById(R.id.lastname);
        email = (TextView) findViewById(R.id.email);
        phonenumber=(TextView) findViewById(R.id.phonenumber) ;

        new MyTask().execute();
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
    public void edit(View view){
        Intent intent=new Intent(this,updateprofile.class);
        intent.putExtra("firstname",firstname.getText().toString());
        intent.putExtra("lastname",lastname.getText().toString());
        intent.putExtra("email",email.getText().toString());
        intent.putExtra("phonenumber",phonenumber.getText().toString());
        startActivity(intent);
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(viewprofile.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences sp1=getSharedPreferences("grewal",0);

            String r=sp1.getString("username", null);
            String s = sp1.getString("password", null);

            try {
                url = new URL("http://144.217.163.57:8080/cegepgim/mobile/gamereservation/viewprofile&"+r+"&"+s);

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
                myInput.close();
                JSONObject obj = new JSONObject(response.toString());
                String status=obj.getString("Status");
                if (status.equals("ok")) {
                    o1 = obj.getString("FirstName");
                    o2 = obj.getString("LastName");
                    o3 = obj.getString("Email");
                    o4 = obj.getString("PhoneNumber");

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

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            firstname.setText(o1);
            lastname.setText(o2);
            email.setText(o3);
            phonenumber.setText(o4);
            super.onPostExecute(result);
        }


    }

}
