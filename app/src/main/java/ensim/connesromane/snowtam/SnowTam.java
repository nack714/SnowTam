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

public class SnowTam {

    private static final String CLE_DE_FLORIAN = "c7fbfa50-1027-11ea-8814-bd9683aba036";
    //NE pas UTILISER //private static final String CLE_DE_NICOLAS = "0661e7b0-1027-11ea-8814-bd9683aba036";
   // public static final String PERSO$UNIV = "http://perso.univ-lemans.fr/~i152300/snowtam";
    public static final String URL = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/states/notams/notams-realtime-list?api_key="+CLE_DE_FLORIAN+"&format=json&criticality=&locations=";//ENGM, BGGH
    //public static final String PERSO$UNIV = "http://192.168.43.19/snowtam";
    public static final String PERSO$UNIV = URL;

    private String[] raw_fields;
    private List<String> decoded_fields = new ArrayList<>();

    protected SnowTam(String string) {

        this.raw_fields = string.split("\\)");
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
                    return new SnowTam(rawData.replaceAll("\\\\n", " "));
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return new NoSnowTam();
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

        /**
         * TODO : elements = str.split(i+"\\) ") avec i allant de A à Z
         */

        String prevLine = "";

        for(String rawLine : this.raw_fields) {

            String line = this.cleanStr(rawLine);

            switch(this.lastCharofStr(prevLine)){

                default:
                    Log.e("Parsing snowtam error", "Section " + this.lastCharofStr(prevLine) + " :  prevLine is \"" + prevLine +"\"");
                    break;
                case 'A':
                    this.decoded_fields.add(line);
                    break;
                case 'B':
                    this.decoded_fields.add(Decoder.decodedDate(line));
                    break;
                case 'C':
                    this.decoded_fields.add(Decoder.decodedRunway(line));
                    break;
                case 'D':
                    this.decoded_fields.add(Decoder.decodedCoveredRunwayLength(line));
                    break;
                case 'F':
                    this.decoded_fields.add(Decoder.decodedRunwayConditions(line));
                    break;
                case 'G':
                    this.decoded_fields.add(Decoder.decodedMeanDepth(line));
                    break;
                case 'H':
                    this.decoded_fields.add(Decoder.decodedFriction(line));
                    break;
                case 'N':

                    break;
            }

            prevLine = rawLine;
        }
    }

    private String cleanStr(String rawLine) {
        return rawLine.length() < 2 ? "" : rawLine.substring(0, rawLine.length()-2).trim();
    }

    private char lastCharofStr(String string) {
       return string.length() <1 ? '0' : string.charAt(string.length()-1);
    }

    private static class NoSnowTam extends SnowTam{

        protected NoSnowTam() {
            super("");
        }

        @Override
        public String getDecodedInfo() {
            return "Cannot access to website";
        }
    }
}

