package ensim.connesromane.snowtam;

import android.os.Handler;
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

public final class SnowTam {

    //private static final String CLE_DE_FLORIAN = "c7fbfa50-1027-11ea-8814-bd9683aba036";
    public static final String PERSO$UNIV = "http://perso.univ-lemans.fr/~i152300/snowtam";

    private String[] fields;

    private SnowTam(String... strings) {
        this.fields = strings;
    }

    public static SnowTam createSnowTamFromURL(final String url){

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            URL website = new URL(url);

            InputStream in = website.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine()) != null) {

                if(line.contains("\"all\":") && line.contains("SNOWTAM")) {
                    String rawData = line.split("\"all\":")[1];
                    return new SnowTam(rawData.split("\\\\n"));
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        String s = "SnowTam :{\n";

        for (String string : this.fields) {
            s += string;
        }

        return s += "}\n";
    }
}

