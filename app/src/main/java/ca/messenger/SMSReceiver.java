package ca.messenger;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

/**
 * Created by Princejeet Singh San on 1/21/2017.
 */
public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage msgs = null;
        String str = "";
        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            //msgs = new SmsMessage[pdus.length];
            String phoneNumber = "";
            //for (int i = 0; i < msgs.length; i++) {
                //msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                int i = 0;
                msgs = SmsMessage.createFromPdu((byte[]) pdus[i]);
                phoneNumber = msgs.getOriginatingAddress();
                //str += "Request from " + phoneNumber;
                //str += ": ";
                String messageBody = msgs.getMessageBody().toString();
                String[] messageBodyArray = messageBody.split("From ");
                messageBodyArray = messageBodyArray[1].split(" to ");
                String origin = messageBodyArray[0];
                messageBodyArray = messageBodyArray[1].split(" via ");
                String destination = messageBodyArray[0];
                String travelMode = messageBodyArray[1];
                /*int j = 1;
                while (j < messageBodyArray.length) {
                    if (messageBodyArray[j] == "to")
                        break;
                    origin += messageBodyArray[j] + " ";
                    j++;
                } j++;
                while (j < messageBodyArray.length) {
                    if (messageBodyArray[j] == "via")
                        break;
                    destination += messageBodyArray[j] + " ";
                    j++;
                }*/
                Log.d("Origin", origin);
                Log.d("Destination", destination);
                Log.d("Travel Mode", travelMode);
                str += "Origin: " + origin + "Destination: " + destination + "Travel Mode: " + travelMode;
                str += "\n";
            //}
            ArrayList<String> steplist = new ArrayList<String>();

            GoogleDirections googleDirections = new GoogleDirections();
            try {
                steplist = googleDirections.getNewDirections(origin, destination, travelMode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int j = 0; j<steplist.size(); j++){
                System.out.println(steplist.get(j));
            }
            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNumber, null, str, null, null);
            for(int k = 0; k< steplist.size(); k++){
                smsManager.sendTextMessage(phoneNumber, null, steplist.get(k), null, null);
            }

            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

            //---send a broadcast intent to update the SMS received in the activity---
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
        }
    }

}
