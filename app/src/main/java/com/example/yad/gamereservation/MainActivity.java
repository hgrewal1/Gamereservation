package com.example.yad.gamereservation;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
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

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    StringBuffer response;
    String r,s;
    String url = null;
    String TAG_SUCCESS ;

    private ProgressDialog nDialog;

    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        username = (EditText) findViewById(R.id.username);
         password = (EditText) findViewById(R.id.password);




    }
    public void forgotpassword(View view)
    {
        Intent intent=new Intent(this,forgotpassword.class);
        startActivity(intent);
    }

            public void onClick(View view) {
                r=username.getText().toString();
                s=password.getText().toString();
                url="http://192.168.1.8:8080/gameservervation/cegepgim/gamereservation/login&"+r+"&"+s;
                    new MyTask().execute();
                }

                public void signup1(View view){
                    Intent intent=new Intent(this,signup.class);
                    startActivity(intent);
                }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private class MyTask extends AsyncTask<String, String, String> {
        String o1, o2, o3, o4,o5,o6,o7,o8;
        JSONObject obj;

        @Override
        protected String doInBackground(String... params) {



            try {
                URL url1 = new URL(url);

                HttpURLConnection client = null;
                client = (HttpURLConnection) url1.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();
                System.out.println("\n Sending 'GET' request to url:" + url1);
                System.out.println("\n Response code:" + responseCode);
                InputStreamReader myInput = new InputStreamReader(client.getInputStream());
                BufferedReader in = new BufferedReader(myInput);
                String inputline;
                response = new StringBuffer();

                while ((inputline = in.readLine()) != null) {
                    response.append(inputline);
                }
                in.close();
                obj = new JSONObject(response.toString());
                o1 = obj.getString("Status");
                if(o1.equals("ok")) {
                    o2 = obj.getString("UserName");
                    o3 = obj.getString("FirstName");
                    o4 = obj.getString("LastName");
                    o5 = obj.getString("Email");

                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("name",o3);
                    SharedPreferences sp=getSharedPreferences("grewal", 0);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("UserName",o2);
                    Ed.putString("FirstName",o3);
                    Ed.putString("LastName",o4 );
                    Ed.putString("Email",o5);

                    Ed.commit();
                    startActivity(intent);
                    finish();
                }

                else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "UserName or password incorrect",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            } catch (MalformedURLException e) {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "MalformedURLException",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

                e.printStackTrace();

            } catch (IOException e) {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "password incorrect",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
                e.printStackTrace();
            }
            catch (JSONException e)

            {Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "json error",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
                ;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);


        }


    }


}

