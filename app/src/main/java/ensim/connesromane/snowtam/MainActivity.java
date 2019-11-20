package ensim.connesromane.snowtam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


    }
}
