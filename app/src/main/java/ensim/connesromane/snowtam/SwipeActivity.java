package ensim.connesromane.snowtam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {

    AirportList airportList;
    List<RadioButton> indicators = new ArrayList<>();
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        this.initAirports();
        this.initRadios();

        final TextView airportName = this.findViewById(R.id.textAirportName);
        final TextView airportData = this.findViewById(R.id.textAirportData);

        airportName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("index", "" + index);
                Intent intent = new Intent(SwipeActivity.this, AirportInformationActivity.class);
                intent.putExtra("airportSelected", airportList.getList().get(index).getId()+"");
                Log.w("id send", airportList.getList().get(index).getId() + "");
                startActivity(intent);

            }

        });

        final View view = this.findViewById(R.id.view);

        this.updateTextViews(airportName, airportData);

        view.setOnTouchListener(new OnSwipeTouchListener(SwipeActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(SwipeActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(SwipeActivity.this, "right", Toast.LENGTH_SHORT).show();
                decrementIndex(airportName, airportData);
            }
            public void onSwipeLeft() {
                Toast.makeText(SwipeActivity.this, "left", Toast.LENGTH_SHORT).show();
                incrementIndex(airportName, airportData);

            }
            public void onSwipeBottom() {
                Toast.makeText(SwipeActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAirports() {
        Intent intent = this.getIntent();
        String rawAirports = intent.getStringExtra("listAirportActive");

        if(rawAirports != null) {
            this.airportList = MainActivity.airportList.searchById(rawAirports);

            for(Airport airport : this.airportList.getList()) {
                airport.setSnowTam(SnowTam.createSnowTamFromURL(SnowTam.PERSO$UNIV));
            }
        }
    }

    private void incrementIndex(TextView name, TextView data){
        this.index += this.index == this.airportList.getList().size()-1 ? 0 : 1;
        this.updateTextViews(name, data);
    }

    private void decrementIndex(TextView name, TextView data){
        this.index -= this.index == 0 ? 0 : 1;
        this.updateTextViews(name, data);
    }

    private void updateTextViews(TextView name, TextView data){

        Airport port = this.airportList.getList().get(index);
        SnowTam snowTam = port.getSnowTam();
        name.setText(port.getNom());
        //data.setText("Latitude : " + port.getLat() + ", Longitude : " + port.getLon());
        data.setText(snowTam.toString());

        for(RadioButton radio : this.indicators){
            radio.setChecked(false);
        }

        this.indicators.get(index).setChecked(true);
    }

    private void initRadios() {
        LinearLayout layout = this.findViewById(R.id.layoutRadio);

        for(int i = 0; i<this.airportList.getList().size(); i++){

            RadioButton radio = new RadioButton(this);
            radio.setEnabled(false);
            radio.setGravity(Gravity.CENTER);
            this.indicators.add(radio);
            layout.addView(radio);
        }
    }
}