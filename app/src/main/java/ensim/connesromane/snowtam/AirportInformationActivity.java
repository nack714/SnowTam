package ensim.connesromane.snowtam;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class AirportInformationActivity extends AppCompatActivity {

    private Airport airport;


    AirportList airportList;
    SnowTamList snowTamList;
    List<RadioButton> indicators = new ArrayList<>();
    int index = 0;
    MapViewFragment map;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariport_info);

        Intent intent = this.getIntent();

        this.initAirports();
        airport = airportList.getList().get(0);

        //this.initSnowtams();
        this.snowTamList = new SnowTamList(airportList.getOACI());

        map = new MapViewFragment(airport);

        final Fragment first = map;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout, first);
        fragmentTransaction.commit();



        this.initRadios();


        final TextView airportName = this.findViewById(R.id.text_frag1);
        final TextView airportData = this.findViewById(R.id.text_frag2);

        LinearLayout rl2 = this.findViewById(R.id.rl2);

        this.updateTextViews(airportName, airportData);

        rl2.setOnTouchListener(new OnSwipeTouchListener(AirportInformationActivity.this) {
            public void onSwipeTop() {
                //Toast.makeText(AirportInformationActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(AirportInformationActivity.this, "right", Toast.LENGTH_SHORT).show();
                decrementIndex(airportName, airportData);
            }
            public void onSwipeLeft() {
                //Toast.makeText(AirportInformationActivity.this, "left", Toast.LENGTH_SHORT).show();
                incrementIndex(airportName, airportData);

            }
            public void onSwipeBottom() {
                //Toast.makeText(AirportInformationActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });









    }














    private void initAirports() {
        Intent intent = this.getIntent();
        String rawAirports = intent.getStringExtra("listAirportActive");

        if(rawAirports != null) {
            this.airportList = MainActivity.airportList.searchById(rawAirports);
        }

        Log.e("airportlist : ", this.airportList.toString());
    }

    private void initSnowtams() {

        this.snowTamList = new SnowTamList(airportList.getOACI());
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
        SnowTam snowTam = this.snowTamList.searchByOACI(port.getCode_OACI());
        name.setText(port.getNom());
        data.setText(snowTam.getDecodedInfo());

        for(RadioButton radio : this.indicators){
            radio.setChecked(false);
        }

        this.indicators.get(index).setChecked(true);
        Log.w("airport : ",port.toString());
        map.setAirport(port);
    }

    private void initRadios() {
        LinearLayout layout = this.findViewById(R.id.layoutRadio2);

        for(int i = 0; i<this.airportList.getList().size(); i++){

            RadioButton radio = new RadioButton(this);
            radio.setEnabled(false);
            radio.setGravity(Gravity.CENTER);
            this.indicators.add(radio);
            layout.addView(radio);
        }
    }

}