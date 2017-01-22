/**
 * Created by Hamid Habib on 21/01/17
 */
package ca.messenger;

import android.app.Activity;
import android.text.Html;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

public class NewDirections extends Activity{
    public void getNewDirections() throws Exception{
        String string;
        String simplifiedHTMLString;
        GeoApiContext gac = new GeoApiContext().setApiKey("AIzaSyDwdDONSqbgjvLvFqdzcnXE_sFeJ1Qw3Vs");
        DirectionsResult result = DirectionsApi.getDirections(gac, "NewYork", "Toronto").await();
        for(int i = 0; i<result.routes[0].legs[0].steps.length; i++ ){
            string = result.routes[0].legs[0].steps[i].htmlInstructions + " END";
            simplifiedHTMLString =  string;
            // Start index from 1 not zero
            int index = i + 1;
            System.out.println("Index: " + index + " " + Html.fromHtml(Html.fromHtml(simplifiedHTMLString).toString()));
        }
    }
}
