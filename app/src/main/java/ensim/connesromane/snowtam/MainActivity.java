package ensim.connesromane.snowtam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    public static AirportList airportList;
    ArrayAdapter<Airport> arrayAdapterVisible,arrayAdapterFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initAirport();

//SearchView
        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0) {
                    AirportList searchList = airportList.search(newText);
                    arrayAdapterVisible = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1 , searchList.getList());

                    listView.setAdapter(arrayAdapterVisible);
                }else{
                    arrayAdapterVisible = arrayAdapterFull;

                    listView.setAdapter(arrayAdapterVisible);
                }
                return false;
            }

        });


//Button
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(airportList.getActive().getList().size() > 0){
                    //Intent intent = new Intent(MainActivity.this, SwipeActivity.class);
                    Intent intent = new Intent(MainActivity.this, AirportInformationActivity.class);
                    intent.putExtra("listAirportActive", airportList.getActive().getId());
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, R.string.MAIN_ACTIVITY_button_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });


//ListView
        arrayAdapterFull = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , airportList.getList());
        arrayAdapterVisible = arrayAdapterFull;
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapterVisible);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Object o = listView.getItemAtPosition(position);
                Airport airport = (Airport) o;
                activeAirport(airport);
                listView.invalidateViews();
            }
        });
    }

    private void activeAirport(Airport airport) {
        if(airportList.nbActive()>=10 && !airport.isActive()){
            Toast.makeText(MainActivity.this, R.string.MAIN_ACTIVITY_too_much_airport, Toast.LENGTH_SHORT).show();
        }else {
            airport.changeActive();
        }

    }

    private void initAirport() {


        airportList = new AirportList();
        airportList.addAirport(new Airport("TFFR", "PTP", "Pointe-à-Pitre", "16.267613", "-61.527249"));


        airportList.addAirport(new Airport("ESSA", "ARN", "Aéroport de Stockholm-Arlanda", "59.649762", "17.923781"));
        airportList.addAirport(new Airport("ESKN", "NYO", "Aéroport de Stockholm-Skavsta", "58.789020", "16.915365"));
        airportList.addAirport(new Airport("EFHK", "HEL", "Aéroport d'Helsinki-Vantaa","60.321042", "24.952860"));
        airportList.addAirport(new Airport("LFSB", "EAP", "EuroAirport Basel-Mulhouse-Freiburg","47.598165", "7.525486"));
        airportList.addAirport(new Airport("EFOU", "OUL", "Aéroport d'Oulu","64.930875", "25.355838"));
        airportList.addAirport(new Airport("BIGR", "GRY", "Aérodrome de Grímsey","66.547875", "-18.020887"));
        airportList.addAirport(new Airport("BIRK", "RKV", "Aéroport de Reykjavik","64.132628", "-21.949453"));
        airportList.addAirport(new Airport("CYQB", "YQB", "Aéroport international Jean-Lesage de Québec","46.790772", "-71.388569"));
        airportList.addAirport(new Airport("BGGH", "GOH", "Aéroport de Nuuk","64.191702", "-51.674193"));
        airportList.addAirport(new Airport("ESTA", "AGH", "Aéroport d'Ängelholm–Helsingborg","56.287315", "12.868031"));
        airportList.addAirport(new Airport("ENGM", "OSL", "Aéroport international d'Oslo-Gardermoen","60.197550", "11.100415"));

    }



}
