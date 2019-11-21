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
    ArrayList<Airport> airportList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lat = (EditText)findViewById(R.id.editText);
        lon = (EditText)findViewById(R.id.editText2);
        lat.setText("48.018851");
        lon.setText("0.157715");

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("lat",  lat.getText().toString());
                intent.putExtra("lon", lon.getText().toString());
                startActivity(intent);


                Toast.makeText(MainActivity.this, getListAirport(), Toast.LENGTH_LONG).show();
            }
        });









        airportList = new ArrayList<>();
        airportList.add(new Airport("PTP", "TFFR", "Pointe-à-Pitre"));
        airportList.add(new Airport("NTE", "LFRS", "Nantes Atlantique"));
        airportList.add(new Airport("CDG", "LFPG", "Président français disparu"));
        airportList.add(new Airport("JFK", "KJFK", "Président américain disparu"));


        ArrayAdapter<Airport> arrayAdapter = new ArrayAdapter<Airport>(this, android.R.layout.simple_list_item_1 , airportList);

        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Airport airport = (Airport) o;
                airport.changeActive();
                defineColor(v, airport.isActive());
                Toast.makeText(MainActivity.this, "Selected :" + " " + airport+" ("+airport.isActive()+")", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String getListAirport() {
        String str = "Airport selected :";
        for(Airport a :airportList){
            if(a.isActive()){
                str += "\n"+a;
            }
        }
        return str;
    }


    private void defineColor(View v, Boolean b){
        if(b){
            v.setBackgroundColor(Color.GREEN);
        }else{
            v.setBackgroundColor(Color.WHITE);
        }
    }

}
