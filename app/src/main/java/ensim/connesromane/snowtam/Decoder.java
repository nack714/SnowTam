package ensim.connesromane.snowtam;


public class Decoder {

    public static String decodedDate(String raw) {
        String month = raw.substring(0, 2);
        String day = raw.substring(2, 4);
        String hour = raw.substring(4, 6);
        String minute = raw.substring(6, 8);

        return day + "/" + month + " " + "at" + " " + hour + "h" + minute + " UTC";
    }

    public static String decodedRunway(String raw) {

        if(raw.endsWith("L")) {
            return "Left runway " + Integer.parseInt(raw.substring(0, raw.length()-2));
        }

        else if(raw.endsWith("R")) {
            return "Right runway " + Integer.parseInt(raw.substring(0, raw.length()-2));
        }

        else {
            int runway = Integer.parseInt(raw);
            return runway == 88 ? "All runways" : ((runway % 50 == runway ? "Left runway " : "Right runway ") + (runway % 50));
        }

    }

    public static String decodedCoveredRunwayLength(String raw){

        return "Cleared runway length " + raw + " M";
    }

    public static String decodedRunwayConditions(String raw){
        String[] states = raw.split("/");

        String result = "Conditions :\n";

        for(int i=0;i<states.length;i++)
        {
            result += "\t" + runwayPart(i) + conditions(Integer.parseInt(states[i])) + "\n";
        }

        return result;
    }

    private static String runwayPart(int i) {
        switch(i)
        {
            case 0:
                return "Threshold : ";
            case 1:
                return "Mid runway : ";
            case 2:
                return "Roll out : ";
            default:
                return "[ERROR] Unknown runway part : ";
        }
    }

    private static String conditions(int i) {

        if(i > 9) {
            return "Top : " + conditions(i/10) + ", Under : " + conditions(i%10);
        }
        switch(i)
        {
            case 0:
                return "Clear and Dry";
            case 1:
                return "Damp";
            case 2:
                return "Wet or water patches";
            case 3:
                return "Rime or frost covered";
            case 4:
                return "Dry snow";
            case 5:
                return "Wet snow";
            case 6:
                return "Slush";
            case 7:
                return "Ice";
            case 8:
                return "Compacted or rolled snow";
            case 9:
                return "Frozen ruts or ridges";
            default:
                return "[ERROR] Unknown condition";
        }
    }

    public static String decodedMeanDepth(String raw) {
        String[] depth = raw.split("/");

        String result = "Mean depth :\n";

        for(int i=0;i<depth.length;i++)
        {
            result += "\t" + runwayPart(i) + (depth[i].equals("XX") ? "Not significant" : (depth[i] + "mm")) + "\n";
        }

        return result;
    }

    public static String decodedFriction(String raw) {
        String[] infos = raw.split(" ");
        String[] frictions = infos[0].split("/");

        String result = "Friction measurements :\n";

        for(int i=0;i<frictions.length;i++) {
            result += "\t" + runwayPart(i) + (friction(frictions[i])) + "\n";
        }

        return result + (infos.length > 1 ? infos[1] : "Unknown measurement device");
    }

    private static String friction(String friction) {

        if(friction.equals("XX") || friction.equals("9")) {
            return "Missing value";
        }

        int coeff = Integer.parseInt(friction);

        if(coeff == 5 || coeff > 40)
            return "Good";
        else if(coeff == 4 || coeff <= 39 && coeff >= 36)
            return "Medium to Good";
        else if(coeff == 3 || coeff <=35 && coeff >= 30)
            return "Medium";
        else if(coeff == 2 || coeff <= 29 && coeff >=26)
            return "Medium to poor";
        else
            return "Poor";
    }
}
