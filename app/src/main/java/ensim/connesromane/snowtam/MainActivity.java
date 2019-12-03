package ensim.connesromane.snowtam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.app.SearchableInfo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        CharSequence query = searchView.getQuery(); // get the query string currently in the text field
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.w("Search submit : ", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>0) {
                    Log.w("Search tap : ", newText);
                    AirportList searchList = airportList.search(newText);
                    arrayAdapterVisible = new ArrayAdapter<Airport>(MainActivity.this, android.R.layout.simple_list_item_1 , searchList.getList());

                    listView.setAdapter(arrayAdapterVisible);
                }else{
                    Log.w("Search tap : ", "empty");
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
                    Toast.makeText(MainActivity.this, R.string.MAIN_ACTIVITY_button_empty, Toast.LENGTH_LONG).show();
                }
            }
        });


//ListView
        arrayAdapterFull = new ArrayAdapter<Airport>(this, android.R.layout.simple_list_item_1 , airportList.getList());
        arrayAdapterVisible = arrayAdapterFull;
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapterVisible);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Object o = listView.getItemAtPosition(position);
                Airport airport = (Airport) o;
                airport.changeActive();
                listView.invalidateViews();
            }
        });
    }

    private void initAirport() {


        airportList = new AirportList();
        airportList.addAirport(new Airport("TFFR", "PTP", "Pointe-à-Pitre", "16.267613", "-61.527249"));
        airportList.addAirport(new Airport("LFRS", "NTE", "Nantes Atlantique", "47.157419", "-1.606232"));
        airportList.addAirport(new Airport("LFPG", "CDG", "Président français disparu", "49.003630", "2.516978"));
        airportList.addAirport(new Airport("KJFK", "JFK", "Président américain disparu","40.641311", "-73.778139"));
        airportList.addAirport(new Airport("LPPT", "LIS", "Aéroport Humberto Delgado","38.775594", "-9.135367"));
        airportList.addAirport(new Airport("LFSB", "EAP", "EuroAirport Basel-Mulhouse-Freiburg","47.598165", "7.525486"));
        airportList.addAirport(new Airport("EGFF", "CWL", "Aéroport de Cardiff","51.398193", "-3.345457"));
        airportList.addAirport(new Airport("EGBB", "BHX", "Aéroport de Birmingham","52.452382", "-1.743507"));
        airportList.addAirport(new Airport("LPPR", "OPO", "Aéroport Francisco Sá-Carneiro","41.242119", "-8.678551"));
        airportList.addAirport(new Airport("KSFO", "SFO", "Aéroport international de San Francisco","37.621313", "-122.378955"));
        airportList.addAirport(new Airport("BGGH", "GOH", "Aéroport de Nuuk","64.191702", "-51.674193"));
        airportList.addAirport(new Airport("ESTA", "AGH", "Aéroport d'Ängelholm–Helsingborg","56.287315", "12.868031"));
        airportList.addAirport(new Airport("ENGM", "OSL", "Aéroport international d'Oslo-Gardermoen","60.197550", "11.100415"));

    }



}
