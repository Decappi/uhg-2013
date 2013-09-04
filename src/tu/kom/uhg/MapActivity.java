package tu.kom.uhg;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends GenericActivity implements 
OnMarkerClickListener, android.location.LocationListener{
	
	private LocationManager locationManager;
	private String provider;
	private GoogleMap map;
	private static final LatLng MARKER1 = new LatLng(49.8513, 8.63194);
	private static final LatLng MARKER2 = new LatLng(49.8771, 8.65362);
	private static final LatLng MARKER3 = new LatLng(49.8777, 8.6519);
	private static final LatLng MARKER4 = new LatLng(49.8762, 8.65213);
	private static final LatLng MARKER5 = new LatLng(49.8773, 8.65615);
	
	//Trimm-dich-Pfad A (11 Stages)
	private static final LatLng PARKOURA = new LatLng(49.8762, 8.65472);
	private static final LatLng STAGEA1 = new LatLng(49.8761, 8.65464);
	private static final LatLng STAGEA2 = new LatLng(49.877, 8.65383);
	private static final LatLng STAGEA3 = new LatLng(49.8795, 8.65326);
	private static final LatLng STAGEA4 = new LatLng(49.8794, 8.653);
	private static final LatLng STAGEA5 = new LatLng(49.8797, 8.65104);
	private static final LatLng STAGEA6 = new LatLng(49.8797, 8.65104);
	private static final LatLng STAGEA7 = new LatLng(49.8796, 8.65092);
	private static final LatLng STAGEA8 = new LatLng(49.8793, 8.65083);
	private static final LatLng STAGEA9 = new LatLng(49.8786, 8.65078);
	private static final LatLng STAGEA10 = new LatLng(49.8782, 8.65073);
	private static final LatLng STAGEA11 = new LatLng(49.8778, 8.65066);
	
	//Trimm-dich-Pfad B (1 Stage, multiply tasks)
	private static final LatLng PARKOURB = new LatLng(49.8768, 8.65162);
	
	//Ruhepunkt A
	private static final LatLng CHILLPOINTA = new LatLng(49.8782, 8.65371);
	//Ruhepunkt B
	private static final LatLng CHILLPOINTB = new LatLng(49.878, 8.65105);

	Context context = this;
	
	//removable markers- quiz, info etc (not finished)
	List<Marker> removableMarkersList = new ArrayList<Marker>();
	
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
	    
	    addMarkersToMapOnCreate();
	}
	
	private void addMarkersToMapOnCreate() {
		addStartingMarkersToMap();
		addHideableMarkersToMap();
	}
	
	private void addStartingMarkersToMap() {
		//Starting point of Parkour A
		map.addMarker(new MarkerOptions()
			.position(PARKOURA)
			.title("Parkour A")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_big)));
		
		//Starting point of Parkour B
		map.addMarker(new MarkerOptions()
			.position(PARKOURB)
			.title("Parkour B")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_big)));
		
		//Chillpoint A
		map.addMarker(new MarkerOptions()
			.position(CHILLPOINTA)
			.title("Chillpoint 1")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ruheplace_big)));
		//Chillpoint B
		map.addMarker(new MarkerOptions()
			.position(CHILLPOINTB)
			.title("Chillpoint 2")
	        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ruheplace_big)));
	}

	private void addHideableMarkersToMap() {
		removableMarkersList.add(
			map.addMarker(new MarkerOptions()
				.position(MARKER1)
				.title("quizquiz")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
		
		removableMarkersList.add(
			map.addMarker(new MarkerOptions()
		        .position(MARKER2)
		        .title("quizquiz")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
		
		removableMarkersList.add(
			map.addMarker(new MarkerOptions()
		        .position(MARKER3)
		        .title("quizquiz")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
		
		removableMarkersList.add(
			map.addMarker(new MarkerOptions()
		        .position(MARKER4)
		        .title("quizquiz")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));

		removableMarkersList.add(
			map.addMarker(new MarkerOptions()
		        .position(MARKER5)
		        .title("quizquiz")
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
	}
	
	private void manageHideableMarkersOnMap(Location myLoc) {
		for (Marker m : removableMarkersList){
			Location markerLoc = new Location("dummy");
			markerLoc.setLatitude(m.getPosition().latitude);
			markerLoc.setLongitude(m.getPosition().longitude);
			if (markerLoc.distanceTo(myLoc) >= ACTIVATION_DISTANCE)
				m.setVisible(false);
			else 
				m.setVisible(true);
		}
	}
	
	@Override
    public boolean onMarkerClick(final Marker marker) {
		//Toast.makeText(MapActivity.this, "Marker " + marker.getTitle() + " clicked", Toast.LENGTH_LONG).show();
		
		//check markers ID
		if(marker.getTitle().equals("Parkour A")){
			final AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(MapActivity.this).create();
			alertDialog.setTitle("Trimm-dich-Pfad");
			alertDialog.setMessage("Der Halt-Dich-Fit Parkour bietet mit seinen 11 Stationen " +
					"verteilt über den Herrngarten den idealen Ausgleich zum Büroalltag. " +
					"Mit kleinen Übungen kannst du deine Fitness täglich verbessern. \nParkour starten?");
			//ja button
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja", new DialogInterface.OnClickListener(){
					
				public void onClick(DialogInterface dialog, int which) {
					//start Trimm-dich-Pfad
					startParkourA();
				}
			});
			//nein button
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nein", new DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which){
					alertDialog.cancel();
				}
			});
			alertDialog.show();
		} else if(marker.getTitle().equals("Parkour B")){
			final AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(MapActivity.this).create();
			alertDialog.setTitle("Trimm-dich-Pfad");
			alertDialog.setMessage("Der Rückenfit Parkour bietet dir ein gezieltes Training " +
					"für den vom Bürostuhl gestressten Rücken. \nParkour starten?");
			//ja button
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja", new DialogInterface.OnClickListener(){
					
				public void onClick(DialogInterface dialog, int which) {
					//Parkour B will be played only on one place, so no additional markers needed
					Intent intent = new Intent(MapActivity.this, ParkourActivity.class);
					intent.putExtra("markerTitle", marker.getTitle());
			        startActivity(intent);
				}
			});
			//nein button
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nein", new DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which){
					alertDialog.cancel();
				}
			});
			alertDialog.show();
		} else if (marker.getTitle().subSequence(0, 7).equals("Station")) {
			Intent intent = new Intent(MapActivity.this, ParkourActivity.class);
			intent.putExtra("markerTitle", marker.getTitle());
	        startActivity(intent);
		} else if (marker.getTitle().contains("Chillpoint"))  {
			Intent intent = new Intent(MapActivity.this, ParkourActivity.class);
			intent.putExtra("markerTitle", marker.getTitle());
	        startActivity(intent);
		} else {//TOOD FIXME
			Intent intent = new Intent(MapActivity.this, QuizActivity.class);
			intent.putExtra("markerId", marker.getId());
	        startActivity(intent);
		}
        
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
		//map.clear();
	    manageHideableMarkersOnMap(location);
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
	
	private void startParkourA() {
		//clear map
		map.clear();
		//show Stages 1-11
		map.addMarker(new MarkerOptions()
		.position(STAGEA1)
		.title("Station 1")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA2)
		.title("Station 2")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA3)
		.title("Station 3")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA4)
		.title("Station 4")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA5)
		.title("Station 5")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA6)
		.title("Station 6")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA7)
		.title("Station 7")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA8)
		.title("Station 8")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
		map.addMarker(new MarkerOptions()
		.position(STAGEA9)
		.title("Station 9")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));

		map.addMarker(new MarkerOptions()
		.position(STAGEA10)
		.title("Station 10")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
	
		map.addMarker(new MarkerOptions()
		.position(STAGEA11)
		.title("Station 11")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.activities_small)));
		
	}
}
