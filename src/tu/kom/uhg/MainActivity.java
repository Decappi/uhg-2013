package tu.kom.uhg;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import tu.kom.uhg.R;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import tu.kom.uhg.GenericActivity;
 
public class MainActivity extends GenericActivity {
	
	private GoogleMap map;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scoresView(getCurrentFocus());
		finish();
		setContentView(R.layout.activity_main);
		
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);		
		
		
		Toast.makeText(this, "main activity loaded", Toast.LENGTH_SHORT).show();
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
