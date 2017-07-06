package com.example.yad.gamereservation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    Button onset;
    DatePickerDialog datePickerDialog;
    private ProgressDialog pDialog;
    private int year, month, day;
    String r,s,t,u,v,x,y,z,a,message;
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
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        dob = (EditText) findViewById(R.id.dob);
    }


    public void date(View v) {
        DialogFragment newFragment = new signup.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
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

        url="http://144.217.163.57:8080/cegepgim/mobile/gamereservation/signup&"+r+"&"+s+"&"+t+"&"+u+"&"+v+"&"+x+"&"+y;
       if(validateEmptyText()&&validafirstname(r)&&validateFirstnameLength(r)&&validateLastnameLength(s)&&validatelastname(s)&&usernamelength(t)&&validateusername(t)&&emailvalidate(u)&&emaillength(u)&&passwordlength(x)&&isValidPassword(x)&&validpassword()&&datevalidate(y)&&isValidMobile(v))
       {
           new MyTask().execute();
       }
    }
    private boolean isValidMobile(String phone) {
        boolean check=true;

            if(phone.length()<10|| phone.length() > 10) {

                check = false;
                Toast.makeText(signup.this,"Phone number must contain 10 digits",Toast.LENGTH_SHORT).show();
            }
        return check;
    }
    private boolean validpassword() {
        boolean temp=true;

        String pass=password.getText().toString();
        String cpass=c_password.getText().toString();

        if(!pass.equals(cpass)){
            Toast.makeText(signup.this,"Password didn't match",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }
    private boolean validateEmptyText() {
        boolean temp=true;

        String f=firstname.getText().toString();
        String l=lastname.getText().toString();
        String e=email.getText().toString();
        String u=username.getText().toString();
        String d=dob.getText().toString();
        if(f.matches("")&&l.matches("")&&e.matches("")&&u.matches("")){
            Toast.makeText(signup.this,"Required fields are empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }

        else if(f.matches("")){
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
        else if(d.matches("")){
            Toast.makeText(signup.this,"Date of Birth field is empty",Toast.LENGTH_SHORT).show();
            temp=false;
        }

        return temp;
    }    public boolean isValidPassword(final String password) {
        boolean temp=true;
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        if (matcher.matches()) {
            temp = true;
        } else {
            Toast.makeText(signup.this,"Your Password  must be Six character long and must contain at least one Uppercase letter, lowercase letter and digit without any space. ",Toast.LENGTH_LONG).show();
            temp = false;
        }
        return temp;
    }
    private boolean  datevalidate(String registerdate) {
        boolean temp = true;
        String r = "^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d{2}$";
        String s = "^([1-9]|[12][0-9]|3[01])[-]([1-9]|1[012])[-](19|20)\\d{2}$";
        String t = "^([1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d{2}$";
        String u = "^(0[1-9]|[12][0-9]|3[01])[-]([1-9]|1[012])[-](19|20)\\d{2}$";
        Matcher matcherObj = Pattern.compile(r).matcher(registerdate);
        Matcher matcherObj2 = Pattern.compile(s).matcher(registerdate);
        Matcher matcherObj3 = Pattern.compile(t).matcher(registerdate);
        Matcher matcherObj4 = Pattern.compile(u).matcher(registerdate);
        if (matcherObj.matches()||matcherObj2.matches()||matcherObj3.matches()||matcherObj4.matches()) {
            temp = true;
        } else {
            Toast.makeText(signup.this,"Date must be like DD-MM-YYYY",Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }
    private boolean  validafirstname(String a) {
        boolean temp = true;
        String r = "^[A-Za-z]+$";

        Matcher matcherObj = Pattern.compile(r).matcher(a);
        if (matcherObj.matches()) {
            temp = true;
        } else {
            Toast.makeText(signup.this,"Firstname must contain only Letters without any space",Toast.LENGTH_LONG).show();
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
            Toast.makeText(signup.this,"Lastname must contain only Letters without any space",Toast.LENGTH_LONG).show();
            temp = false;
        }
        return temp;
    }
    private boolean  validateusername(String a) {
        boolean temp = true;
        String r = "^[0-9a-zA-Z]+$";

        Matcher matcherObj = Pattern.compile(r).matcher(a);
        if (matcherObj.matches()) {
            temp = true;
        } else {
            Toast.makeText(signup.this,"Username must contain only Letters and digits without any space",Toast.LENGTH_LONG).show();
            temp = false;
        }
        return temp;
    }
    private boolean validateFirstnameLength(String user) {
        boolean check=true;

        if(user.length() > 20) {

            check = false;
            Toast.makeText(signup.this,"Firstname is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean validateLastnameLength(String user) {
        boolean check=true;

        if(user.length() > 20) {

            check = false;
            Toast.makeText(signup.this,"Lastname is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean emaillength(String email) {
        boolean check=true;

        if(email.length() > 20) {

            check = false;
            Toast.makeText(signup.this,"Email is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean usernamelength(String user) {
        boolean check=true;

        if(user.length() > 20) {

            check = false;
            Toast.makeText(signup.this,"Username is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private boolean passwordlength(String pass) {
        boolean check=true;

        if(pass.length() > 20) {

            check = false;
            Toast.makeText(signup.this,"Password is too long",Toast.LENGTH_SHORT).show();
        }
        return check;
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

    private class MyTask extends AsyncTask<String, String, String> {
        String o1, o2, o3, o4,o5,o6,o7,o8;
        JSONObject obj;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(signup.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

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
                myInput.close();
                obj = new JSONObject(response.toString());
                o1 = obj.getString("Status");
                if(o1.equals("ok")) {

                    o3 = obj.getString("FirstName");
                    message = obj.getString("Message");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("firstname",o3);
                    SharedPreferences sp=getSharedPreferences("grewal", 0);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("FirstName",o3);
                    Ed.commit();
                    startActivity(intent);
                    finish();

                }

                else {
                    message=obj.getString("Message");



                }
                client.disconnect();

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


            if (pDialog.isShowing())
                pDialog.dismiss();
            if(o1.equals("Eroor"))
            {
                Toast.makeText(getApplicationContext(),
                        "UserName Must be unique",
                        Toast.LENGTH_LONG)
                        .show();
            }
            else if(o1.equals("wrong")||o1.equals("ok")){
                Toast.makeText(getApplicationContext(),
                        message,
                        Toast.LENGTH_LONG)
                        .show();
            }
            super.onPostExecute(result);


        }


    }

    public class DatePickerFragment  extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return  new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK,this,year,month,day);
        }

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            // set day of month , month and year value in the edit text
            dob.setText(dayOfMonth + "-"
                    + (monthOfYear + 1) + "-" + year);

        }
    }
}
