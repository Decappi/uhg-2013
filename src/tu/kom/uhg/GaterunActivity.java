package tu.kom.uhg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

public class GaterunActivity extends GenericActivity implements 
OnMarkerClickListener, android.location.LocationListener{
	
	private LocationManager locationManager;
	private String provider;
	private GoogleMap map;
	private static final int GATEDISTANCE = 50;
	long DELAY = 10 * 1000;//180 secs
	boolean inTime = true;
	private Location myLoc = new Location("myLoc");
	private LatLng nextGate = null;
	private int score = 0;
	double rHead = 0.3;
	double rArms = 0.1;
	double rLegs = 0.6;
	private Timer timer;
	
	private static final LatLng MARKER1 = new LatLng(49.8513, 8.63194);
	private static final LatLng MARKER2 = new LatLng(49.8771, 8.65362);
	private static final LatLng MARKER3 = new LatLng(49.8777, 8.6519);
	private static final LatLng MARKER4 = new LatLng(49.8762, 8.65213);
	private static final LatLng MARKER5 = new LatLng(49.8773, 8.65615);

	private Marker marker1;
	private Marker marker2;
	private Marker marker3;
	private Marker marker4;
	private Marker marker5;
	
	//setting up the vantage points for the Herrngarten
	private ArrayList<LatLng> gateList = new ArrayList<LatLng>() {{
		add(new LatLng(49.8771, 8.65554));
		add(new LatLng(49.8758, 8.6543));
		add(new LatLng(49.8761, 8.65191));
		add(new LatLng(49.8761, 8.65169));
		add(new LatLng(49.8763, 8.65183));
		add(new LatLng(49.8767, 8.65225));
		add(new LatLng(49.8771, 8.65254));
		add(new LatLng(49.877, 8.65381));
		add(new LatLng(49.8767, 8.65418));
		add(new LatLng(49.8769, 8.65467));
		add(new LatLng(49.877, 8.65074));
		add(new LatLng(49.8775, 8.65211));
		add(new LatLng(49.8784, 8.65087));
		add(new LatLng(49.8783, 8.65185));
		add(new LatLng(49.8783, 8.65309));
		add(new LatLng(49.8783, 8.65352));
		add(new LatLng(49.8786, 8.65083));
		add(new LatLng(49.879, 8.6519));
		add(new LatLng(49.8797, 8.65104));
		add(new LatLng(49.8797, 8.65291));
		add(new LatLng(49.8797, 8.65199));
		add(new LatLng(49.8788, 8.65301));
		add(new LatLng(49.8778, 8.65325));
		add(new LatLng(49.8779, 8.65388));
		add(new LatLng(49.8773, 8.65431));
		add(new LatLng(49.8767, 8.65503));
		add(new LatLng(49.8765, 8.65342));
		add(new LatLng(49.8758, 8.6532));
	}};
	
	class notInTime extends TimerTask {
		@Override
        public void run() {
            inTime = false;
            
        }
	}
	
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
	    //showMarkersFromList();
	}
	
	public void findNextGate() {
		int stepToSearch = 0;
		
		//get a list of the nearest gates 
		ArrayList<LatLng> tmpList = findNextGate(stepToSearch);
		if(tmpList.size() == 1){
			nextGate =  tmpList.get(0);
		}else{
			//get one randomly
			Random rand = new Random();
			int index = rand.nextInt(tmpList.size() - 1);
			nextGate =  tmpList.get(index);
			//delete all unused gates from the gateList
			tmpList.remove(index);
			for(LatLng gate : tmpList) {
				gateList.remove(gate);
			}	
		}
		
		//show gate on map
		map.addMarker(new MarkerOptions()
		.position(nextGate)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
		//reset timer
		timer.cancel();
		timer = new Timer();
		timer.schedule(new notInTime(), DELAY);
		//reset the helper variable
		inTime = true;
	}
	
	public ArrayList<LatLng> findNextGate(int stepToSearch) {
		ArrayList<LatLng> tmpList = new ArrayList<LatLng>();
		for(LatLng gate : gateList) {
			Location loc = new Location("gate");
			loc.setLatitude(gate.latitude);
			loc.setLongitude(gate.longitude);
			
			if (loc.distanceTo(myLoc) <= GATEDISTANCE + stepToSearch) {
				tmpList.add(gate);
			}
		}
		if(tmpList.size() == 0) {
			return findNextGate(stepToSearch + 50);
		}
		return tmpList;
	}
	
	public void showMarkersFromList() {
		for (LatLng marker : gateList){
			map.addMarker(new MarkerOptions()
				.position(marker)
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
			);
		}
	}
	/*
	 * 
	 * if (loc2.distanceTo(myLoc) <= ACTIVATION_DISTANCE)
			marker2 = map.addMarker(new MarkerOptions()
	        .position(MARKER2)
	        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
	 * 
	 */
	
	
	@Override
    public boolean onMarkerClick(final Marker marker) {
		Toast.makeText(GaterunActivity.this, "get me", Toast.LENGTH_LONG).show();
        
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
		/*double lat = location.getLatitude();
		double lon = location.getLongitude();
		LatLng target = new LatLng(lat, lon);
		//move camera center to the current position
		map.animateCamera(CameraUpdateFactory.newLatLng(target));
		map.clear();
	    addMarkersToMap(location);*/
		
		myLoc = location;
		//find next gate
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		LatLng target = new LatLng(lat, lon);
		//move camera center to the current position
		map.animateCamera(CameraUpdateFactory.newLatLng(target));
		
		//game started nextGate == null -> findNextGate
		if(nextGate == null) {
			findNextGate();
		}else{
			//nextGate reached? -> findNextgate
			if(gateReached()) {
				score = score + 100;
				if (!inTime)
					score = score / 2;
				map.clear();
				gateList.remove(nextGate);
				findNextGate();
			}
		}
	}
	
	public boolean gateReached() {
		
		Location loc = new Location("");
		loc.setLatitude(nextGate.latitude);
		loc.setLongitude(nextGate.longitude);
		
		if (loc.distanceTo(myLoc) <= 5) {
			return true;
		}
		return false;
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
	
	@Override
	public void finish() {
		//submit score
		//convert date to format ("dd.mm.yy")
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd.mm.yy", Locale.GERMANY);
		String date = df.format(c.getTime());	
		addScore("Gate Run", date, score, rHead, rArms, rLegs);
		
		super.finish();
	}
}
