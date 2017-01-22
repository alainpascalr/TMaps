/**
 * Created by Hamid Habib on 2017-01-21
 */
package ca.messenger;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Yellow Pages Main
    ProgressDialog progressDialog;
    Button getYellowPagesData;
    TextView yellowPagesResult;

    //Yellow Pages Variables
    String what = "Restaurants";
    String where = "Toronto";
    //Number of results to return in each page
    int pgLen = 5;
    //Radius in kilometer
    int dist = 1;
    //Set the language for the response. Permitted values are en or fr
    String lang = "en";
    // Format JSON OR XML
    String fmt = "JSON";
    String UID = "USER001";
    //YellowAPI
    String YellowAPI = "c8bk7t7ay794pynf7xxaapnh";

    IntentFilter intentFilter;
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //---display the SMS received in the TextView---
            TextView SMSes = (TextView) findViewById(R.id.textView1);
            SMSes.setText(intent.getExtras().getString("sms"));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables
        ArrayList<String> steplist = new ArrayList<String>();

        //Get Directions
        NewDirections newDirections = new NewDirections();
        try {
            steplist = newDirections.getNewDirections();
            for(int i = 0; i < steplist.size(); i++){
                System.out.println(steplist.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Check internet connection
        isInternetOn();
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        //Get Yellow pages data
        getYellowPagesData = (Button) findViewById(R.id.btnHit);
        yellowPagesResult = (TextView) findViewById(R.id.tvJsonItem);
        getYellowPagesData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask().execute("http://api.sandbox.yellowapi.com/FindBusiness/?what="+what+"+&where="+where+"&pgLen="+pgLen+"&pg=1&dist="+dist+"&fmt="+fmt+"&lang="+lang+"&UID="+UID+"&apikey="+YellowAPI+"\n");
            }
        });
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

//    Check internet connection method
public final boolean isInternetOn() {
    // get Connectivity Manager object to check connection
    ConnectivityManager connectivityManager =
            (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

    // Check for network connections
    if (connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
            connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
            connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

        // if connected with internet
        Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
        return true;

    } else if (
            connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                    connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

        Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
        return false;
    }
    return false;
}

    // Yellow pages API
    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            yellowPagesResult.setText(result);
        }
    }
}

