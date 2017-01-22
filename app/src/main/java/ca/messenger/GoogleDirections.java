/**
 * Created by Hamid Habib on 2017-01-21
 */
package ca.messenger;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleDirections {
    String jsonoutput = "", duration;
    public void getDirections(final Context context){

        GoogleDirection.withServerKey("AIzaSyDwdDONSqbgjvLvFqdzcnXE_sFeJ1Qw3Vs")
                .from(new LatLng(37.7681994, -122.444538))
                .to(new LatLng(37.7749003,-122.4034934))
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            Toast toast = Toast.makeText(context, rawBody, Toast.LENGTH_LONG);
//                            System.out.println("up:: " + rawBody);
                            jsonoutput = rawBody;
//                            System.out.println("up:: " + jsonoutput);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(context, "FUCK OFF", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        System.out.println("down: + " + jsonoutput);
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                }

                );
    }


}
