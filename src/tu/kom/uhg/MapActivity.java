package tu.kom.uhg;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends GenericActivity implements android.location.LocationListener{
	
	private LocationManager locationManager;
	private String provider;
	private GoogleMap map;
	private static final LatLng MARKER1 = new LatLng(49.8772, 8.6545);
	private static final LatLng MARKER2 = new LatLng(49.8771, 8.65362);
	private static final LatLng MARKER3 = new LatLng(49.8777, 8.6519);
	private static final LatLng MARKER4 = new LatLng(49.8762, 8.65213);
	private static final LatLng MARKER5 = new LatLng(49.8773, 8.65615);

	private Marker marker1;
	private Marker marker2;
	private Marker marker3;
	private Marker marker4;
	private Marker marker5;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
		// Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	    if (location != null) {
	        System.out.println("Provider " + provider + " has been selected.");
	        onLocationChanged(location);
	    }
	    
	    addMarkersToMap();
	}
	
	private void addMarkersToMap() {
		marker1 = map.addMarker(new MarkerOptions()
        .position(MARKER1)
        .title("Brisbane")
        .snippet("Population: 2,074,200")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		marker2 = map.addMarker(new MarkerOptions()
        .position(MARKER2)
        .title("Brisbane")
        .snippet("Population: 2,074,200")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		marker3 = map.addMarker(new MarkerOptions()
        .position(MARKER3)
        .title("Brisbane")
        .snippet("Population: 2,074,200")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		marker4 = map.addMarker(new MarkerOptions()
        .position(MARKER4)
        .title("Brisbane")
        .snippet("Population: 2,074,200")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		marker5 = map.addMarker(new MarkerOptions()
        .position(MARKER5)
        .title("Brisbane")
        .snippet("Population: 2,074,200")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}
	
	@Override
	protected void onPause() {
	  super.onPause();
	  locationManager.removeUpdates(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		LatLng target = new LatLng(lat, lon);
		//move camera center to the current position
		map.animateCamera(CameraUpdateFactory.newLatLng(target));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	  // TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}
