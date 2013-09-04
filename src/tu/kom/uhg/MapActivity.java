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
		OnMarkerClickListener, android.location.LocationListener {

	private static final String CHILLPOINT_2 = "Chillpoint 2";
	private static final String CHILLPOINT_1 = "Chillpoint 1";
	private static final String PARKOUR_A = "Parkour A";
	private static final String PARKOUR_B = "Parkour B";
	private LocationManager locationManager;
	private String provider;
	private GoogleMap map;

	// Trimm-dich-Pfad A (11 Stages)
	private List<MarkerOptions> parkourAMarkers = new ArrayList<MarkerOptions>() {
		{
			add(createMarker(49.8761, 8.65464,	"Station 1", 0));
			add(createMarker(49.877, 8.65383,	"Station 2", 0));
			add(createMarker(49.8795, 8.65326,	"Station 3", 0));
			add(createMarker(49.8794, 8.653,	"Station 4", 0));
			add(createMarker(49.8797, 8.65104,	"Station 5", 0));
			add(createMarker(49.8797, 8.65104,	"Station 6", 0));
			add(createMarker(49.8796, 8.65092,	"Station 7", 0));
			add(createMarker(49.8793, 8.65083,	"Station 8", 0));
			add(createMarker(49.8786, 8.65078,	"Station 9", 0));
			add(createMarker(49.8782, 8.65073,	"Station 10", 0));
			add(createMarker(49.8778, 8.65066,	"Station 11", 0));
		}
	};

	private List<MarkerOptions> startingMarkers = new ArrayList<MarkerOptions>() {
		{
			add(createMarker(49.8762, 8.65472, PARKOUR_A,
					R.drawable.activities_big));// Trimm-dich-Pfad A (11 Stages)
			add(createMarker(49.8768, 8.65162, PARKOUR_B,
					R.drawable.activities_big)); // Trimm-dich-Pfad B (1 Stage,
													// multiply tasks)
			add(createMarker(49.8782, 8.65371, CHILLPOINT_1,
					R.drawable.ruheplace_big)); // Ruhepunkt A
			add(createMarker(49.878, 8.65105, CHILLPOINT_2,
					R.drawable.ruheplace_big)); // Ruhepunkt B
		}
	};

	private List<MarkerOptions> hideableMarkers = new ArrayList<MarkerOptions>() {
		{
			add(createMarker(49.8513, 8.63194, "quizquiz",
					BitmapDescriptorFactory.HUE_AZURE));
			add(createMarker(49.8771, 8.65362, "quizquiz",
					BitmapDescriptorFactory.HUE_AZURE));
			add(createMarker(49.8777, 8.6519, "quizquiz",
					BitmapDescriptorFactory.HUE_AZURE));
			add(createMarker(49.8762, 8.65213, "quizquiz",
					BitmapDescriptorFactory.HUE_AZURE));
			add(createMarker(49.8773, 8.65615, "quizquiz",
					BitmapDescriptorFactory.HUE_AZURE));
		}

		private MarkerOptions createMarker(double latitude, double longitude,
				String title, float BDF) {
			MarkerOptions markerOptions = new MarkerOptions()
					.position(new LatLng(latitude, longitude)).title(title)
					.icon(BitmapDescriptorFactory.defaultMarker(BDF));
			return markerOptions;
		}
	};

	List<Marker> removableMarkersList = new ArrayList<Marker>();

	private MarkerOptions createMarker(double latitude, double longitude,
			String title, int resourceId) {
		MarkerOptions markerOptions = new MarkerOptions().position(
				new LatLng(latitude, longitude)).title(title);
		if (resourceId != 0)
			markerOptions
					.icon(BitmapDescriptorFactory.fromResource(resourceId));
		return markerOptions;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setUpMap();
		setUpLocationManager();
		addStartingMarkersToMap();
		addHideableMarkersToMap();
	}

	private void setUpLocationManager() {
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the location provider -> use
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

	private void setUpMap() {
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		map.setOnMarkerClickListener(this);
	}

	private void addStartingMarkersToMap() {
		for (MarkerOptions mo : startingMarkers) {
			map.addMarker(mo);
		}
	}

	private void addHideableMarkersToMap() {
		for (MarkerOptions mo : hideableMarkers) {
			mo.visible(false);// hide marker
			removableMarkersList.add(map.addMarker(mo));
		}
	}

	private void manageHideableMarkersOnMap(Location myLoc) {
		for (Marker m : removableMarkersList) {
			Location markerLoc = new Location("dummy");
			markerLoc.setLatitude(m.getPosition().latitude);
			markerLoc.setLongitude(m.getPosition().longitude);
			if (markerLoc.distanceTo(myLoc) > ACTIVATION_DISTANCE)
				m.setVisible(false);
			else
				m.setVisible(true);
		}
	}

	@Override
	public boolean onMarkerClick(final Marker marker) {
		// Toast.makeText(MapActivity.this, "Marker " + marker.getTitle() +
		// " clicked", Toast.LENGTH_LONG).show();

		// check markers ID
		if (marker.getTitle().equals(PARKOUR_A)) {
			final AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(MapActivity.this).create();
			alertDialog.setTitle("Trimm-dich-Pfad");
			alertDialog
					.setMessage("Der Halt-Dich-Fit Parkour bietet mit seinen 11 Stationen "
							+ "verteilt über den Herrngarten den idealen Ausgleich zum Büroalltag. "
							+ "Mit kleinen Übungen kannst du deine Fitness täglich verbessern. \nParkour starten?");
			// ja button
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// start Trimm-dich-Pfad
							startParkourA();
						}
					});
			// nein button
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nein",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							alertDialog.cancel();
						}
					});
			alertDialog.show();
		} else if (marker.getTitle().equals(PARKOUR_B)) {
			final AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(MapActivity.this).create();
			alertDialog.setTitle("Trimm-dich-Pfad");
			alertDialog
					.setMessage("Der Rückenfit Parkour bietet dir ein gezieltes Training "
							+ "für den vom Bürostuhl gestressten Rücken. \nParkour starten?");
			// ja button
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ja",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// Parkour B will be played only on one place, so no
							// additional markers needed
							Intent intent = new Intent(MapActivity.this,
									ParkourActivity.class);
							intent.putExtra("markerTitle", marker.getTitle());
							startActivity(intent);
						}
					});
			// nein button
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Nein",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							alertDialog.cancel();
						}
					});
			alertDialog.show();
		} else if (marker.getTitle().subSequence(0, 7).equals("Station")) {
			Intent intent = new Intent(MapActivity.this, ParkourActivity.class);
			intent.putExtra("markerTitle", marker.getTitle());
			startActivity(intent);
		} else if (marker.getTitle().contains("Chillpoint")) {
			Intent intent = new Intent(MapActivity.this, ParkourActivity.class);
			intent.putExtra("markerTitle", marker.getTitle());
			startActivity(intent);
		} else {// TOOD FIXME
			Intent intent = new Intent(MapActivity.this, QuizActivity.class);
			intent.putExtra("markerId", marker.getId());
			startActivity(intent);
		}

		// We return false to indicate that we have not consumed the event and
		// that we wish
		// for the default behavior to occur (which is for the camera to move
		// such that the
		// marker is centered and for the marker's info window to open, if it
		// has one).
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
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		LatLng target = new LatLng(lat, lon);
		// move camera center to the current position
		map.animateCamera(CameraUpdateFactory.newLatLng(target));
		// map.clear();
		manageHideableMarkersOnMap(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	private void startParkourA() {
		// clear map
		map.clear();
		// show Stages 1-11
		for (MarkerOptions mo : parkourAMarkers) {
			mo.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.activities_small));
			map.addMarker(mo);
		}
	}
}
