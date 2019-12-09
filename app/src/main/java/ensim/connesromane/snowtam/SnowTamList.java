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
            Boolean[] find = new Boolean[listOACI.length];
            for(int i=0;i<find.length;i++){
                find[i] = false;
            }


                String line;
                while((line = reader.readLine()) != null) {

                    for (int i = 0; i < listOACI.length; i++) {

                        if (!find[i] && line.contains("\"all\":") && line.contains("SNOWTAM") && line.contains(listOACI[i])) {
                            Log.w("Nico i",i+"");
                            Log.w("Nico Contains : ", listOACI[i] + " : ");
                            Log.w("Nico line", line);
                            find[i] = true;
                            String rawData = line.split("\"all\":")[1];
                            snowTams.add(new SnowTam(listOACI[i], rawData.replaceAll("\\\\n", " ")));
                            break;
                        }
                    }
                }


            Log.w("Nico break", "\n\n\n");
            for(int i=0;i<find.length;i++) {

                Log.w("Nico look for", i+" ( "+find[i]+" )");
                if (!find[i]) {

                    Log.w("Nico put noSnowTam on",i+" ( "+listOACI[i]+" )");
                    snowTams.add(new SnowTam.NoSnowTam(listOACI[i]));
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

    public SnowTam searchByOACI(String oaci){
        for (SnowTam st : snowTams){
            if(oaci!=null && oaci.equals(st.getOaci())){
                return st;
            }
        }
        return null;
    }
}