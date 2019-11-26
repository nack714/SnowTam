package ensim.connesromane.snowtam;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class AirportInformationActivity extends AppCompatActivity {

    private Airport airport;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariport_info);

        Intent intent = this.getIntent();
        String rawAirports = intent.getStringExtra("airportSelected");
        if(rawAirports == null){
            Log.w("list received","null");
        }
        Log.w("list received",rawAirports);
        airport = MainActivity.airportList.searchById(rawAirports).getList().get(0);

        final Fragment first = new MapViewFragment(airport);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout, first);
        fragmentTransaction.commit();


    }
}