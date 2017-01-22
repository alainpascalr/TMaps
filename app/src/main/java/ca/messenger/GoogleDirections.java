/**
 * Created by Hamid Habib on 21/01/17
 */
package ca.messenger;

import android.app.Activity;
import android.text.Html;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import java.util.ArrayList;

public class GoogleDirections extends Activity{

    public ArrayList<String> getNewDirections(String origin, String destination, String mode) throws Exception{
        ArrayList<String> steplist = new ArrayList<String>();
        String string;
        String simplifiedHTMLString;
        GeoApiContext gac = new GeoApiContext().setApiKey("AIzaSyDwdDONSqbgjvLvFqdzcnXE_sFeJ1Qw3Vs");

        DirectionsResult result;
        if(mode.equalsIgnoreCase("Driving")){
            result = DirectionsApi.newRequest(gac)
                .mode(TravelMode.DRIVING)
                .origin(origin)
                .destination(destination).await();
        }else if(mode.equalsIgnoreCase("Walking")){
            result = DirectionsApi.newRequest(gac)
                    .mode(TravelMode.WALKING)
                    .origin(origin)
                    .destination(destination).await();
        }else if(mode.equalsIgnoreCase("bicycling")){
            result = DirectionsApi.newRequest(gac)
                    .mode(TravelMode.BICYCLING)
                    .origin(origin)
                    .destination(destination).await();
        }else{
            result = DirectionsApi.newRequest(gac)
                    .mode(TravelMode.TRANSIT)
                    .origin(origin)
                    .destination(destination).await();
        }

        for(int i = 0; i<result.routes[0].legs[0].steps.length; i++ ){
            string = result.routes[0].legs[0].steps[i].htmlInstructions;
            simplifiedHTMLString =  string;
            // Start index from 1 not zero
            int index = i + 1;
            String finalSteps = "Step: " + index + " " + Html.fromHtml(Html.fromHtml(simplifiedHTMLString).toString());
            steplist.add(finalSteps);
        }
        return steplist;
    }

}
