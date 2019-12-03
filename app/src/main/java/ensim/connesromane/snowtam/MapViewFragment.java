package ensim.connesromane.snowtam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    Airport airport;

    public MapViewFragment(Airport airport){
        this.airport = airport;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                // De/commenter pour afficher la vue satellite
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


                mapInit();

            }
        });



        return rootView;
    }

    public void setAirport(Airport a){
        this.airport = a;
        mapInit();
    }

    public void mapInit() {
        try {

            LatLng sydney = new LatLng(Float.parseFloat(airport.getLat()), Float.parseFloat(airport.getLon()));
            googleMap.addMarker(new MarkerOptions().position(sydney).title(airport.getTitle()).snippet(""));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(14).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }catch (Exception e){

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}