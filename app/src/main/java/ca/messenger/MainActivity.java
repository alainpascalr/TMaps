package ca.messenger;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Hack for bypassing the Async Strict mode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if(checkInternetConnection.isInternetWorking()){
            Log.d("SUCCESS", "PRETTY GOOD");
        } else {
            Log.d("FAILURE", "PRETTY NOT GRESAT");

        }
    }

}
