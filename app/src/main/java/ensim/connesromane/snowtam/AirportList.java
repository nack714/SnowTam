package ensim.connesromane.snowtam;

import java.util.ArrayList;

public class AirportList {

    ArrayList<Airport> airportList;

    public AirportList(){
        airportList = new ArrayList<>();
    }

    public void addAirport(Airport a){
        airportList.add(a);
    }

    public void addAirport(String code_OACI, String code_IATA, String nom)  {
        airportList.add(new Airport(code_OACI, code_IATA, nom));
    }

    @Override
    public String toString() {
        String str = "Airport list :";
        for(Airport a : airportList){
            str += "\n"+a.toString();
        }
        return str;
    }

    public AirportList getActive(){
        AirportList airportActive = new AirportList();
        for(Airport a :airportList){
            if(a.isActive()){
                airportActive.addAirport(a);
            }
        }
        return airportActive;
    }

    public ArrayList<Airport> getList() {
        return airportList;
    }
}
