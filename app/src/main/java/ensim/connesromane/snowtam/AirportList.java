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

    public void addAirport(String code_OACI, String code_IATA, String nom, String lat, String lon)  {
        airportList.add(new Airport(code_OACI, code_IATA, nom, lat, lon));
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

    public Airport searchById(int id){
        for(Airport a :airportList){
            if(a.isId(id)){
                return a;
            }
        }
        return null;
    }

    public AirportList searchById(ArrayList<Integer> idList){
        AirportList airportList = new AirportList();
        for(Integer id : idList){
            Airport a = searchById(id);
            if(a != null){
                airportList.addAirport(a);
            }
        }
        return airportList;
    }

    public AirportList searchById(String str){

        String[] listStr = str.split(";");
        ArrayList<Integer> listInt = new ArrayList<>();
        for(int i=0 ; i<listStr.length ; i++ ){
            listInt.add(Integer.parseInt(listStr[i]));
        }

        return searchById(listInt);

    }

    public ArrayList<Airport> getList() {
        return airportList;
    }

    public String getId() {
    String str = "";
        for(Airport a :airportList){
            str += a.getId()+";";
        }
        return str;
    }

    public AirportList search(String str){
        AirportList returnList = new AirportList();

        for(Airport a : airportList){
            if(a.getCode_IATA().toLowerCase().contains(str.toLowerCase()) || a.getCode_OACI().toLowerCase().contains(str.toLowerCase()) || a.getTitle().toLowerCase().contains(str.toLowerCase()) ){
                returnList.addAirport(a);
            }
        }

        return  returnList;
    }

    public int nbActive(){
        int nb=0;
        for(Airport a : airportList){
            if (a.isActive()){
                nb++;
            }
        }
        return  nb;
    }

    public String getOACI(){
        String str ="";
        for(Airport a : airportList){
            str += a.getCode_OACI()+",";
        }
        return str;

    }

    public int size(){
        return airportList.size();
    }

}
