package ensim.connesromane.snowtam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText lat, lon;


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
            }
        });


        ListView listView = (ListView)findViewById(R.id.listView);

        ArrayList<Airport> airportList = new ArrayList<>();
        airportList.add(new Airport("PTP", "Pointe-à-Pitre"));
        airportList.add(new Airport("NTE", "Nantes Atlantique"));
        airportList.add(new Airport("CDG", "Président français disparu"));
        airportList.add(new Airport("JFK", "Président américain disparu"));


        // android.R.layout.simple_list_item_1 is a constant predefined layout of Android.
        // used to create a ListView with simple ListItem (Only one TextView).

        ArrayAdapter<Airport> arrayAdapter = new ArrayAdapter<Airport>(this, android.R.layout.simple_list_item_1 , airportList);


        listView.setAdapter(arrayAdapter);




    }


}
