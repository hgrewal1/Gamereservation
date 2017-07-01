package com.example.yad.gamereservation;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class availability extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String TAG = availability.class.getSimpleName();
    ArrayAdapter<String> adapter;
    private ProgressDialog pDialog;
    ArrayList<String> arylist;
    String gsname,dayname,starttime,endtime,date1,id,url,o1,o2,o3,o4,o5;
    EditText editText;
    Spinner spinner2,spinner3,spinner1,spinner4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        setupNavigationView();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        spinner1=(Spinner) findViewById(R.id.spinner);
       spinner2=(Spinner) findViewById(R.id.spinner2);
        spinner3=(Spinner) findViewById(R.id.spinner3);
        spinner4=(Spinner) findViewById(R.id.spinner4);
        editText=(EditText) findViewById(R.id.date);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);


        actionBar.setTitle("Reservation");
        setupNavigationView();
        List<String> items= new ArrayList<String>();
        items.add("9:00");
        items.add("10:00");
        items.add("11:00");
        items.add("12:00");
        items.add("13:00");
        items.add("14:00");
        items.add("15:00");
        items.add("16:00");
        items.add("17:00");
        items.add("18:00");
        List<String> items2= new ArrayList<String>();
        items2.add("FOOTBALL GROUND 1");
        items2.add("FOOTBALL GROUND 2");
        items2.add("PINGPONG TABLE 1");
        items2.add("PINGPONGTABLE TABLE 2");
        items2.add("FOOSBALL TABLE 1");
        items2.add("BOWLING GAMESTATION 1");
        items2.add("BOWLING GAMESTAION 2");
        items2.add("SQUASH GAMESTATION ");
        items2.add("VOLLEYBALL GROUND ");
        items2.add("CRICKET GROUND 1");
        items2.add("CRICKET GROUND 2");
        items2.add("HOCKEY GROUND");
        items2.add("ICEHOCKEY GROUND");
        items2.add("LUDO TABLE 1");
        items2.add("LUDO TABLE 2");
        items2.add("BASKETBALL COURT 1");
        items2.add("BASKETBALL COURT 2");
        items2.add("CHESS TABLE 1");
        items2.add("CHESS TABLE 2");
        items2.add("BADMINTON COURT 1");
        items2.add("BADMINTON COURT 2");
        items2.add("CAROMBOARD TABLE 1");
        items2.add("CAROMBOARD TABLE 2");
        items2.add("BASEBALL GROUND");
        List<String> items3= new ArrayList<String>();
        items3.add("Sunday");
        items3.add("Monday");
        items3.add("Tuesday");
        items3.add("Wednesday");
        items3.add("Thursday");
        items3.add("Friday");
        items3.add("Saturday");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner3.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter3);

        editText.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new availability.DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }

        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinner1=(Spinner)parent;
        spinner2=(Spinner)parent;
        spinner3=(Spinner)parent;
        spinner4=(Spinner)parent;
        if(spinner2.getId()==R.id.spinner2) {

            starttime = parent.getSelectedItem().toString();
        }
if (spinner3.getId()==R.id.spinner3){

    endtime=parent.getSelectedItem().toString();
}
        if (spinner1.getId()==R.id.spinner){

            gsname=parent.getSelectedItem().toString();
        }
        if (spinner4.getId()==R.id.spinner4){

            dayname=parent.getSelectedItem().toString();
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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
                });}


    private boolean validateEmptyText() {
        boolean temp=true;
        String e=editText.getText().toString();
        if(e.matches("")){
            Toast.makeText(availability.this,"Please choose a Date",Toast.LENGTH_SHORT).show();
            temp=false;
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
            Toast.makeText(availability.this,"Date must be like DD-MM-YYYY",Toast.LENGTH_SHORT).show();
            temp = false;
        }
        return temp;
    }

        public void check1(View view){



            date1=editText.getText().toString();
        url="http://192.168.1.8:8080/gameservervation/cegepgim/gamereservation/viewavailability&"+gsname+"&"+starttime+"&"+endtime+"&"+dayname+"&"+date1;
           if(validateEmptyText()&&datevalidate(date1)){ new MyTask().execute();}

    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(availability.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
           URL url1;



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



                JSONObject obj = new JSONObject(response.toString());
                String status=obj.getString("Status");
                if (status.equals("ok")) {

                         o1 = obj.getString("Day");
                         o2 = obj.getString("GameStation");
                         o3 = obj.getString("StartTime");
                         o4 = obj.getString("EndTime");
                         o5= obj.getString("Date");
                    String o6= obj.getString("Message");
                    Intent intent=new Intent(getApplication(),newreservation.class);
                    intent.putExtra("Day",o1);
                    intent.putExtra("GameStation",o2);
                    intent.putExtra("StartTime",o3);
                    intent.putExtra("EndTime",o4);
                    intent.putExtra("Date",o5);
                    intent.putExtra("Message",o6);
                    startActivity(intent);

                    }

                else {
                    o5= obj.getString("Message");
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    o5,
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
            return  new DatePickerDialog(getActivity(),this,year,month,day);
        }

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            // set day of month , month and year value in the edit text
            editText.setText(dayOfMonth + "-"
                    + (monthOfYear + 1) + "-" + year);

        }
    }

    }

