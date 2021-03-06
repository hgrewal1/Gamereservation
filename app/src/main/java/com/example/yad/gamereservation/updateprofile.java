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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updateprofile extends AppCompatActivity {

    private String TAG = updateprofile.class.getSimpleName();
    URL url = null;
    private ProgressDialog pDialog;
    EditText fname,lname,emailid,phone;
    String o1,o2,o3,o4,o5,f,b,c,d,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        setupNavigationView();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("Update Profile");
        setupNavigationView();
        fname = (EditText) findViewById(R.id.firstname);
        lname = (EditText) findViewById(R.id.lastname);
        emailid = (EditText) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phonenumber) ;
        Intent intent=getIntent();
        f= intent.getStringExtra("firstname");
        b=intent.getStringExtra("lastname");
        c=intent.getStringExtra("email");
        d=intent.getStringExtra("phonenumber");
        fname.setText(f);
        lname.setText(b);
        emailid.setText(c);
        phone.setText(d);

    }

    public void update(View view)
    {
        String h=emailid.getText().toString();
        String g=phone.getText().toString();
        String f=fname.getText().toString();
        String l=lname.getText().toString();
        if(validateEmptyText()&&validafirstname(f)&&validateFirstnameLength(f)&&validatelastname(l)&&validateLastnameLength(l)&&emailvalidate(h)&&emaillength(h)&&isValidMobile(g)){

        new MyTask().execute();}
    }
    public void cancel(View view)
    {
        Intent intent=new Intent(this,viewprofile.class);
        startActivity(intent);
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
    private boolean  validafirstname(String a) {
        boolean temp = true;
        String r = "^[A-Za-z]+$";

        Matcher matcherObj = Pattern.compile(r).matcher(a);
        if (matcherObj.matches()) {
            temp = true;
        } else {
            Toast.makeText(updateprofile.this,"Firstname must contain only Letters without any space",Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }
    private boolean  validatelastname(String a) {
        boolean temp = true;
        String r = "^[A-Za-z]+$";

        Matcher matcherObj = Pattern.compile(r).matcher(a);
        if (matcherObj.matches()) {
            temp = true;
        } else {
            Toast.makeText(updateprofile.this,"Lastname must contain only Letters without any space",Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }
    private boolean emailvalidate(String email2){
        boolean temp=true;

        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email2).matches()){
            temp = true;
        }
        else{
            Toast.makeText(getApplicationContext(),"Not a Valid email",Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }
    private boolean isValidMobile(String phone) {
        boolean check=true;

        if(phone.length()<10|| phone.length() > 10) {

            check = false;
            Toast.makeText(updateprofile.this,"Phone number must contain 10 digits",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean validateFirstnameLength(String user) {
        boolean check=true;

        if(user.length() > 20) {

            check = false;
            Toast.makeText(updateprofile.this,"Firstname is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean validateLastnameLength(String user) {
        boolean check=true;

        if(user.length() > 20) {

            check = false;
            Toast.makeText(updateprofile.this,"Lastname is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean emaillength(String email) {
        boolean check=true;

        if(email.length() > 20) {

            check = false;
            Toast.makeText(updateprofile.this,"Email is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean validateEmptyText() {
        boolean temp=true;
        String e=fname.getText().toString();
        String u=lname.getText().toString();
        String w=emailid.getText().toString();
        String y=phone.getText().toString();
        if(e.matches("")&&u.matches("")&&w.matches("")&&y.matches("")){
            Toast.makeText(updateprofile.this,"Required fields are empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }

        else if(e.matches("")){
            Toast.makeText(updateprofile.this,"Firstname field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(u.matches("")){
            Toast.makeText(updateprofile.this,"Lastname field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(w.matches("")){
            Toast.makeText(updateprofile.this,"Email field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(y.matches("")){
            Toast.makeText(updateprofile.this,"Phone field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }



        return temp;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(updateprofile.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences sp1=getSharedPreferences("grewal",0);

            String r=sp1.getString("username", null);
            String s = sp1.getString("password", null);
            o1=fname.getText().toString();
            o2=lname.getText().toString();
            o3=emailid.getText().toString();
            o4=phone.getText().toString();

            try {
                url = new URL("http://144.217.163.57:8080/cegepgim/mobile/gamereservation/updateprofile&"+o1+"&"+o2+"&"+o3+"&"+o4+"&"+r+"&"+s);

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
                    message = obj.getString("Message");

                    Intent intent=new Intent(getApplicationContext(),viewprofile.class);
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

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(getApplicationContext(),
                    message,
                    Toast.LENGTH_LONG)
                    .show();
            super.onPostExecute(result);
        }


    }

}
