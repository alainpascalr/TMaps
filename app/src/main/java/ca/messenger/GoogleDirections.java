/**
 * Created by Hamid Habib on 2017-01-22
 */
package ca.messenger;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class GoogleDirections {

    public void getDirections(final Context context, String origin, String destination){
        Geocoder coder = new Geocoder(context);

        List<Address> addressOrigin;
        List<Address> addressDestination;

        LatLng geoOrigin = null;
        LatLng geoDestination = null;
        try {
            addressOrigin = coder.getFromLocationName(origin, 5);
            addressDestination = coder.getFromLocationName(destination, 5);

            Address locationOrigin = addressOrigin.get(0);
            Address locationDestination = addressDestination.get(0);

            locationOrigin.getLatitude();
            locationOrigin.getLongitude();

            locationDestination.getLatitude();
            locationDestination.getLongitude();

            geoOrigin = new LatLng(locationOrigin.getLatitude(), locationOrigin.getLongitude());
            geoDestination = new LatLng(locationDestination.getLatitude(), locationDestination.getLongitude());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        GoogleDirection.withServerKey("AIzaSyDwdDONSqbgjvLvFqdzcnXE_sFeJ1Qw3Vs")
                .from(geoOrigin)
                .to(geoDestination)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if(direction.isOK()) {
                            Toast toast = Toast.makeText(context, rawBody, Toast.LENGTH_LONG);
                            toast.show();
                            System.out.println("Index" + rawBody);
                        } else {
                            Toast toast = Toast.makeText(context, "FUCK OFF", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }
}