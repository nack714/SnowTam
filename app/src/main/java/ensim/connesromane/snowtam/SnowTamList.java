package ensim.connesromane.snowtam;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SnowTamList
{
    private static final String CLE_DE_FLORIAN = "c7fbfa50-1027-11ea-8814-bd9683aba036";
    //NE pas UTILISER //private static final String CLE_DE_NICOLAS = "0661e7b0-1027-11ea-8814-bd9683aba036";
    // private static final String PERSO$UNIV = "http://perso.univ-lemans.fr/~i152300/snowtam";
    private static final String URL = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/states/notams/notams-realtime-list?api_key="+CLE_DE_FLORIAN+"&format=json&criticality=&locations=";//ENGM, BGGH

    private List<SnowTam> snowTams = new ArrayList<>();

    public SnowTamList(String oacis){

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        String[] listOACI = oacis.split(",");

        Log.e("try", "before");

        try {
            java.net.URL website = new URL(URL + oacis);

            InputStream in = website.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            Log.e("try", "after");

            for(int i=0;i<listOACI.length;i++) {

                String line;
                while((line = reader.readLine()) != null) {

                    if(line.contains("\"all\":") && line.contains("SNOWTAM")) {
                        String rawData = line.split("\"all\":")[1];
                        snowTams.add(new SnowTam(listOACI[i], rawData.replaceAll("\\\\n", " ")));
                    }
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("SnowTamList", "MalformedURLException");
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e("SnowTamList", "IOException");
        }
    }

    public List<SnowTam> getList() {
        return this.snowTams;
    }
}
