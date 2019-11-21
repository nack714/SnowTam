package ensim.connesromane.snowtam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText lat, lon;
    ListView listView;
    public static AirportList airportList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("listAirportActive", airportList.getActive().getId());
                startActivity(intent);


                //Toast.makeText(MainActivity.this, airportList.getActive().toString(), Toast.LENGTH_LONG).show();
            }
        });


        airportList = new AirportList();
        airportList.addAirport(new Airport("TFFR", "PTP", "Pointe-à-Pitre", "16.267613", "-61.527249"));
        airportList.addAirport(new Airport("LFRS", "NTE", "Nantes Atlantique", "47.157419", "-1.606232"));
        airportList.addAirport(new Airport("LFPG", "CDG", "Président français disparu", "49.003630", "2.516978"));
        airportList.addAirport(new Airport("KJFK", "JFK", "Président américain disparu","40.641311", "-73.778139"));


        ArrayAdapter<Airport> arrayAdapter = new ArrayAdapter<Airport>(this, android.R.layout.simple_list_item_1 , airportList.getList());

        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Airport airport = (Airport) o;
                airport.changeActive();
                defineColor(v, airport.isActive());
            }
        });


    }


    private void defineColor(View v, Boolean b){
        if(b){
            v.setBackgroundColor(Color.GREEN);
        }else{
            v.setBackgroundColor(Color.WHITE);
        }
    }

}
