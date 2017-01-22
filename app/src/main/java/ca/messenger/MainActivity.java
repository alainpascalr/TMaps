/**
 * Created by Hamid Habib on 2017-01-21
 */
package ca.messenger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables
        GoogleDirections googleDirections = new GoogleDirections();

        //Get Directions
        googleDirections.getDirections(getApplicationContext());
        NewDirections newDirections = new NewDirections();
        try {
            newDirections.getNewDirections();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Check internet connection
        isInternetOn();
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
