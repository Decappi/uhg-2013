package tu.kom.uhg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BuddiesActivity extends GenericActivity {

	ListView onlineBuddies;
	ListView offlineBuddies;
	String[] defaultBuddies = { 
			"Gordon Freeman",
			"Agent Smith", 
			"Jackie Chan" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buddies);
		Toast.makeText(this, "buddies activity loaded", Toast.LENGTH_LONG)
				.show();
		//read buddies from the local prefs
		SharedPreferences prefs = getSharedPreferences("buddies", MODE_PRIVATE);
		int numBuddies = prefs.getInt("num_buddies", 3);
		String[] buddies = new String[numBuddies];
		for (int i = 1; i <= numBuddies; i++){
			buddies[i-1] = prefs.getString("buddy_"+i, "nobody");
		}
		if (buddies[1].equals("nobody")) buddies = defaultBuddies;
		//TODO check whether they are online
		//add buddies to the list to show
		onlineBuddies = (ListView) findViewById(R.id.listOnlineBuddies);
		offlineBuddies = (ListView) findViewById(R.id.listOfflineBuddies);
		//adapter for offline Buddies
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, buddies);
		offlineBuddies.setAdapter(adapterOffline);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addBuddy(View v) {
		
	}
}
