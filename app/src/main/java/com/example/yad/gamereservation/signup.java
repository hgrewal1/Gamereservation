package com.example.yad.gamereservation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    EditText username,password,firstname,lastname,dob,email,c_password,phonenumber;
    StringBuffer response;
    private DatePicker datePicker;
    private Calendar calendar;

    private int year, month, day;
    String r,s,t,u,v,x,y,z,a;
    String url = null;


    private ProgressDialog nDialog;

    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);




        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        c_password = (EditText) findViewById(R.id.confrimpassword);
        phonenumber=(EditText) findViewById(R.id.phonenumber) ;
        dob = (EditText) findViewById(R.id.dob);

        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        showDate(day,month,year);
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, day, month, year);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int day, int month, int year) {
        dob.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }


    public void onClick(View view) {
        r=firstname.getText().toString();
        s=lastname.getText().toString();
        t=username.getText().toString();
        u=email.getText().toString();
        v=phonenumber.getText().toString();
        x=password.getText().toString();
        z=c_password.getText().toString();
        y=dob.getText().toString();

        url="http://192.168.1.8:8080/gameservervation/cegepgim/gamereservation/signup&"+r+"&"+s+"&"+t+"&"+u+"&"+v+"&"+x+"&"+y;
       if(validateall()&&validpassword()&&datevalidate(y)&&isValidMobile(v)) {new MyTask().execute();}
    }
    private boolean isValidMobile(String phone) {
        boolean check=true;

            if(phone.length()<10|| phone.length() > 10) {

                check = false;
                Toast.makeText(signup.this,"phonenumber must contain 10 digits",Toast.LENGTH_SHORT).show();
            }
        return check;
    }
    private boolean validpassword() {
        boolean temp=true;

        String pass=password.getText().toString();
        String cpass=c_password.getText().toString();

        if(!pass.equals(cpass)){
            Toast.makeText(signup.this,"Password Not matching",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }
    private boolean validateall() {
        boolean temp=true;

        String f=firstname.getText().toString();
        String l=lastname.getText().toString();
        String e=email.getText().toString();
        String u=username.getText().toString();


        if(f.matches("")){
            Toast.makeText(signup.this,"Firstname field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(l.matches("")){
            Toast.makeText(signup.this,"Lastname field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(e.matches("")){
            Toast.makeText(signup.this,"Email field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(u.matches("")){
            Toast.makeText(signup.this,"username field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }

        return temp;
    }
    private boolean  datevalidate(String registerdate) {
        boolean temp = true;
        String r = "^(0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])[-](19|20)\\d{2}$";
        Matcher matcherObj = Pattern.compile(r).matcher(registerdate);
        if (matcherObj.matches()) {
            temp = true;
        } else {
            Toast.makeText(signup.this,"Date must be like DD-MM-YYYY",Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
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
                    intent.putExtra("username",o3);
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
                                    "please enter all the required fields",
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
                                "IOException",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
                e.printStackTrace();
            }
            catch (JSONException e)

            {Log.e(TAG, "UserName and Email Must be Unique");
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
