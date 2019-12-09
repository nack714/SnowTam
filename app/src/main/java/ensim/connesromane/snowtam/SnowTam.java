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


    protected String oaci;
    private String[] raw_fields;
    private List<String> decoded_fields = new ArrayList<>();

    protected SnowTam(String oaci, String rawFields) {

        this.oaci = oaci;
        this.raw_fields = rawFields.split("\\)");
        this.decode();
    }

    public String getOaci(){
        return oaci;
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
                    //Log.e("Parsing snowtam error", "Section " + this.lastCharofStr(prevLine) + " :  prevLine is \"" + prevLine +"\"");
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

    public static class NoSnowTam extends SnowTam{

        protected NoSnowTam(String oaci) {
            super(oaci,"");
        }

        @Override
        public String toString() {
            return this.getDecodedInfo();
        }

        @Override
        public String getDecodedInfo() {
            return this.oaci + "\nNo snowtam aviable.";
        }
    }
}

