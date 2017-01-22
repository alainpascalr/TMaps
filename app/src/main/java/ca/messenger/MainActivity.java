/**
 * Created by Hamid Habib on 2017-01-21
 */
package ca.messenger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

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
}

