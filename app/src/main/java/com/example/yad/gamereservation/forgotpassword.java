package com.example.yad.gamereservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
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
import java.util.List;

 public class forgotpassword extends AppCompatActivity {
     String a,r,message;
     EditText s,u;
     String url;
     private String TAG = MainActivity.class.getSimpleName();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_forgotpassword);
         ActionBar actionBar=getSupportActionBar();
         actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setTitle("Forgot Password");
     }

     public void onClick(View view) {
         s=(EditText)  findViewById(R.id.forgot);
         u=(EditText)  findViewById(R.id.username);
         String r=s.getText().toString();
         String h=u.getText().toString();

             url = "http://144.217.163.57:8080/cegepgim/mobile/gamereservation/forgotpasswordemail&"+r+"&"+h;

new MyTask().execute();
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
                 StringBuffer response = new StringBuffer();

                 while ((inputline = in.readLine()) != null) {
                     response.append(inputline);
                 }
                 in.close();
                 myInput.close();
                 obj = new JSONObject(response.toString());
                 o1 = obj.getString("Status");
                 if(o1.equals("ok")) {
                     o2 = obj.getString("UserName");

                     o4 = obj.getString("Password");
                     message = obj.getString("Message");

                     Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                     startActivity(intent);
                     finish();
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
             }catch (MalformedURLException e) {
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



             } catch (IOException e) {
                 Log.e(TAG, "Couldn't get json from server.");
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Toast.makeText(getApplicationContext(),
                                 "json error"+url,
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
                                 "please enter your email",
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
             Toast.makeText(getApplicationContext(),
                     message,
                     Toast.LENGTH_LONG)
                     .show();
             super.onPostExecute(result);


         }


     }


 }

