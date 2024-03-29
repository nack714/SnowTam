package ensim.connesromane.snowtam;

public class Airport {
    private static int compt_id = 1;
    private String code_IATA,code_OACI, nom;
    private boolean active;
    private int id;
    private String lat, lon;

    public Airport(String code_OACI, String code_IATA, String nom, String lat, String lon)  {
        this.id = compt_id++;
        this.code_IATA = code_IATA;
        this.code_OACI = code_OACI;
        this.nom = nom;
        this.lat = lat;
        this.lon = lon;
        this.active= false;
    }


    public String getCode_IATA() {
        return code_IATA;
    }

    public String getCode_OACI() {
        return code_OACI;
    }

    public String getNom() {
        return nom;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isId(int id) {
        if(this.id == id){
            return true;
        }
        return false;
    }


    public void changeActive() {
        if(this.active){
            this.active = false;
        }else {
            this.active = true;
        }
    }

    public String getTitle() {
        return "["+code_OACI+"] "+nom;
    }

    @Override
    public String toString() {
        String ret = "";
        if(isActive()){
            ret = "✔️";
        }
        return ret +" "+code_OACI +" - "+ nom +" ("+code_IATA+")";
    }

    public int getId() {
        return  id;
    }
}