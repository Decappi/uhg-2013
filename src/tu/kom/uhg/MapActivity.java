package tu.kom.uhg;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends GenericActivity implements 
OnMarkerClickListener, android.location.LocationListener{
	
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
		map.setOnMarkerClickListener(this);
		
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
	}
	
	private void addMarkersToMap(Location myLoc) {
		Location loc1 = new Location("Marker1");
		loc1.setLatitude(MARKER1.latitude);
		loc1.setLongitude(MARKER1.longitude);
		
		Location loc2 = new Location("Marker2");
		loc2.setLatitude(MARKER2.latitude);
		loc2.setLongitude(MARKER2.longitude);
		
		Location loc3 = new Location("Marker3");
		loc3.setLatitude(MARKER3.latitude);
		loc3.setLongitude(MARKER3.longitude);
		
		Location loc4 = new Location("Marker4");
		loc4.setLatitude(MARKER4.latitude);
		loc4.setLongitude(MARKER4.longitude);
		
		Location loc5 = new Location("Marker5");
		loc5.setLatitude(MARKER5.latitude);
		loc5.setLongitude(MARKER5.longitude);
		
		//if (loc1.distanceTo(myLoc) <= 50)
			marker1 = map.addMarker(new MarkerOptions()
			.position(MARKER1)
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		if (loc2.distanceTo(myLoc) <= 50)
			marker2 = map.addMarker(new MarkerOptions()
	        .position(MARKER2)
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		if (loc3.distanceTo(myLoc) <= 50)
			marker3 = map.addMarker(new MarkerOptions()
	        .position(MARKER3)
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		if (loc4.distanceTo(myLoc) <= 50)
			marker4 = map.addMarker(new MarkerOptions()
	        .position(MARKER4)
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		if (loc5.distanceTo(myLoc) <= 50)
			marker5 = map.addMarker(new MarkerOptions()
	        .position(MARKER5)
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	}
	
	@Override
    public boolean onMarkerClick(final Marker marker) {
		Intent intent = new Intent(MapActivity.this, QuizActivity.class);
		intent.putExtra("markerId", marker.getId());
        startActivity(intent);
        
		// We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
		return false;
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
		map.clear();
	    addMarkersToMap(location);
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
