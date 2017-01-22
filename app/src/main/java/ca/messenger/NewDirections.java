package ca.messenger;

import android.app.Activity;
import android.widget.Toast;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import java.util.ArrayList;

import static com.google.maps.DirectionsApi.newRequest;
import static java.sql.Types.NULL;

/**
 * Created by luantran on 21/01/17.
 */

public class NewDirections extends Activity{
//    GeoApiContext gac = new GeoApiContext();

//    DirectionsApi d_api = new DirectionsApi();
//    DirectionsApiRequest request1 = newRequest(gac);
//    DirectionsApiRequest request2 = d_api.getDirections(gac, "Montreal", "Toronto");
    ArrayList<String> steplist = new ArrayList<String>();

    public void getNewDirections() throws Exception{
        GeoApiContext gac = new GeoApiContext().setApiKey("AIzaSyDwdDONSqbgjvLvFqdzcnXE_sFeJ1Qw3Vs");
        DirectionsResult result = DirectionsApi.getDirections(gac, "NewYork", "Toronto").await();
//        System.out.println(result.routes[0].legs[0].steps[0].htmlInstructions + "ENDDDDDDDDD");
        System.out.println("      " + result.routes[0].legs[0].steps.length + "    ");
//        Toast toast = Toast.makeText(this, result.routes[0].legs[0].steps[0].htmlInstructions, Toast.LENGTH_LONG);
        for(int i = 0; i<result.routes[0].legs[0].steps.length; i++ ){
            System.out.println(result.routes[0].legs[0].steps[i].htmlInstructions + "END");
//            String temp1 = result.routes[0].legs[0].steps[i].htmlInstructions.replaceAll("\\b<b>\\b","");
//
//            System.out.println( i+1 + " "+ temp1 );
//            String temp2 = temp1.replaceAll("\\b</b>\\b","");
//            System.out.println( i+1 + " "+ temp2 );

        }
    }



}
