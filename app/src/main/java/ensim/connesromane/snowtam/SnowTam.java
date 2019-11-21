package ensim.connesromane.snowtam;

public class SnowTam {

    private String airportName;
    private String airportData;


    public SnowTam(String rawData) {
        this.parse(rawData);
    }

    private void parse(String rawData){

        airportName = rawData;
        airportData = rawData;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportData() {
        return airportData;
    }
}

