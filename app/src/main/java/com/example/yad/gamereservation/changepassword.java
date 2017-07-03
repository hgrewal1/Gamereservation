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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class changepassword extends AppCompatActivity {
    final Context context = this;
    private Button button;
    String url,message;


    private ProgressDialog pDialog;
    EditText password,newpassword,oldpassword;
    String o1,o2,g,o4,r,s,status;
    private String TAG = changepassword.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        password=(EditText)findViewById(R.id.confirmpass) ;
        newpassword=(EditText)findViewById(R.id.newpass) ;
        oldpassword=(EditText)findViewById(R.id.oldpass) ;

        setupNavigationView();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp1=getSharedPreferences("grewal",0);
        g = sp1.getString("password", null);
        s = sp1.getString("username", null);
        actionBar.setTitle("Chnage Password");
        setupNavigationView(); // add button listener
    }
            public void change(View view) {
                r=password.getText().toString();

                url="http://144.217.163.57:8080/cegepgim/mobile/gamereservation/changepassword&"+s+"&"+r;
                if(validateEmptyText()&&matchpassword()&&isValidPassword(r))
                {new MyTask().execute();}
            }
    private boolean matchpassword() {
        boolean temp=true;

        String f=password.getText().toString();
        String l=oldpassword.getText().toString();
        String e=newpassword.getText().toString();


        if(!g.equals(l)){
            Toast.makeText(changepassword.this,"Old password  is wrong",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        if(!f.equals(e)){
            Toast.makeText(changepassword.this," New password didn't match",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        if(l.equals(e)){
            Toast.makeText(changepassword.this," New password and old password can't be same",Toast.LENGTH_SHORT).show();
            temp=false;
        }



        return temp;
    }
    private boolean validateEmptyText() {
        boolean temp=true;

        String f=password.getText().toString();
        String l=oldpassword.getText().toString();
        String e=newpassword.getText().toString();


        if(l.matches("")){
            Toast.makeText(changepassword.this,"old password field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(e.matches("")){
            Toast.makeText(changepassword.this,"new password field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(f.matches("")){
            Toast.makeText(changepassword.this,"confirm password field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }


        return temp;
    }
    public void back(View view) {
       Intent intent=new Intent(this,Accounts.class);
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
    public boolean isValidPassword(final String password) {
        boolean temp=true;
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        if (matcher.matches()) {
            temp = true;
        } else {
            Toast.makeText(changepassword.this,"Your Password  must be Six character long and must contain at least one Uppercase letter , lowercase letter and digit . ",Toast.LENGTH_LONG).show();
            temp = false;
        }
        return temp;
    }
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(changepassword.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

                URL URL1=null;
            try {
                URL1 = new URL(url);

                HttpURLConnection client = null;
                client = (HttpURLConnection) URL1.openConnection();
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
                    message = obj.getString("Message");
                    String j=password.getText().toString();
                    SharedPreferences sp=getSharedPreferences("grewal", 0);
                    SharedPreferences.Editor Ed=sp.edit();

                    Ed.putString("password",j);

                    Ed.commit();
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

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog);
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText(message);
                dialog.show();



            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }}


