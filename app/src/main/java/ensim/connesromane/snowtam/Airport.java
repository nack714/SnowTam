package ensim.connesromane.snowtam;

import java.io.Serializable;

public class Airport implements Serializable {

    private String code_IATA,code_OACI, nom;

    private boolean active;

    public Airport(String code_OACI, String code_IATA, String nom)  {
        this.code_IATA = code_IATA;
        this.code_OACI = code_OACI;
        this.nom = nom;
        this.active= false;
    }


    public String getCode_IATA() {
        return code_IATA;
    }

    public void setCode_IATA(String code_IATA) {
        this.code_IATA = code_IATA;
    }

    public String getCode_OACI() {
        return code_OACI;
    }

    public void setCode_OACI(String code_OACI) {
        this.code_OACI = code_OACI;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public void changeActive() {
        if(this.active){
            this.active = false;
        }else {
            this.active = true;
        }
    }

    @Override
    public String toString() {
        return this.code_OACI +" - "+ this.nom +" ("+this.code_IATA+")";
    }

}