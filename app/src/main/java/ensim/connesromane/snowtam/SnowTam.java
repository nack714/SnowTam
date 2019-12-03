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
import java.util.HashMap;
import java.util.List;

public final class SnowTam {

    //private static final String CLE_DE_FLORIAN = "c7fbfa50-1027-11ea-8814-bd9683aba036";
   // public static final String PERSO$UNIV = "http://perso.univ-lemans.fr/~i152300/snowtam";
    public static final String PERSO$UNIV = "http://192.168.43.19/snowtam";

    private String[] raw_fields;
    private List<String> decoded_fields = new ArrayList<>();

    private SnowTam(String... strings) {
        this.raw_fields = strings;
        this.decode();
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

        for (String string : this.raw_fields) {
            s += string + "\n";
        }

        return s += "\n}";
    }

    public String getDecodedInfo() {
        String data = "";

        for(String info : this.decoded_fields) {
            data += info +"\n";
        }

        return data;
    }

    private void decode() {

        for(String rawLine : this.raw_fields) {
            if(rawLine.contains("A) ")) {
                String name = rawLine.split("A\\) ")[1].substring(0,4);
                this.decoded_fields.add(name);
            }

            if(rawLine.contains("B) ")) {
                this.decoded_fields.add( Decoder.decodedDate(rawLine.split("B\\) ")[1].substring(0, 8)));
            }

            if(rawLine.contains("C) ")) {
                this.decoded_fields.add(Decoder.decodeRunaway(rawLine.split("C\\) ")[1].substring(0, 2)));
            }

            if(rawLine.contains("D) ")) {
                this.decoded_fields.add(Decoder.decodeCoveredRunwayLength(Decoder.decodedDate(rawLine.split("D\\) ")[1])));
            }

            if(rawLine.contains("F) ")) {
                this.decoded_fields.add(Decoder.decodeRunwayConditions(rawLine.split("F\\) ")[1].substring(0, 5)));
            }
        }
    }
}

