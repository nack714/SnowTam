package ensim.connesromane.snowtam;

public class Decoder {

    public static String decodedDate(String raw) {
        String month = raw.substring(0, 2);
        String day = raw.substring(2, 4);
        String hour = raw.substring(4, 6);
        String minute = raw.substring(6, 8);

//        return day + "/" + month + " " + SwipeActivity.getTranslation(R.string.SNOWTAM_at) + " " + hour + "h" + minute + " UTC";
        return day + "/" + month + " " + "at" + " " + hour + "h" + minute + " UTC";
    }

    public static String decodeRunaway(String raw) {
        int runway = Integer.parseInt(raw);
        //return runaway == 88 ? SwipeActivity.getTranslation(R.string.SNOWTAM_all_runaway) : ((runaway % 50 == runaway ? SwipeActivity.getTranslation(R.string.SNOWTAM_left_runaway) : SwipeActivity.getTranslation(R.string.SNOWTAM_right_runaway)) + (runaway % 50));
        return runway == 88 ? "All runways" : ((runway % 50 == runway ? "Left runway " : "Right runway ") + (runway % 50));
    }

    public static String decodeCoveredRunwayLength(String raw){

        return "Cleared runway length " + raw + " M";
    }

    public static String decodeRunwayConditions(String raw){
        String[] states = raw.split("/");
        String result = "Conditions :\n";

        for(int i=0;i<states.length;i++) {
            result += "\tPart #" + (i+1) + " : " +conditions(Integer.parseInt(states[i])) + "\n";
        }

        return result;
    }

    private static String conditions(int i) {
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
                return "iieeeeet";
        }
    }
}
