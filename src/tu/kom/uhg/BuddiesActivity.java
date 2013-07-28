package tu.kom.uhg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BuddiesActivity extends GenericActivity {

	ListView offlineList;
	ListView onlineList;
	String[] defaultBuddyList = { "Gordon Freeman", "Agent Smith",
			"Jackie Chan" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buddies);
		Toast.makeText(this, "buddies activity loaded", Toast.LENGTH_LONG)
				.show();

		SharedPreferences prefs = getSharedPreferences("buddies", MODE_PRIVATE);
		int buddiesNum = prefs.getInt("buddies_num", 3);
		buddiesNum = buddiesNum - 1;
		String[] buddies = new String[buddiesNum];
		for (int i = 1; i < buddiesNum; i++) {
			buddies[i - 1] = prefs.getString("buddy_" + i + "_name", "noname");
		}
		if (buddies[0].equals("noname"))
			buddies = defaultBuddyList;

		// add
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, buddies);

		offlineList = (ListView) findViewById(R.id.listOfflineBuddies);
		offlineList.setAdapter(adapterOffline);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addBuddy(View v){
		Toast.makeText(this, "addBuddy Clicked", Toast.LENGTH_LONG).show();
		//get the entered buddyname
		EditText newBuddyTxt = (EditText)findViewById(R.id.txt_newBuddy);
		String newBuddyName = newBuddyTxt.getText().toString();
		//TODO search for the existence of the buddy
		//add the new buddy to preferences
		SharedPreferences prefs = getSharedPreferences("buddies", MODE_PRIVATE);
		int buddiesNum = prefs.getInt("buddies_num", 3);
		prefs.edit()
			.putString("buddy_"+ buddiesNum+1 +"_name", newBuddyName)
			.putInt("buddies_num", buddiesNum+1)
			.commit();
		//update the listview
		offlineList = (ListView) findViewById(R.id.listOfflineBuddies);
		
		String[] buddies = new String[buddiesNum];
		for (int i = 1; i < buddiesNum; i++) {
			buddies[i - 1] = prefs.getString("buddy_" + i + "_name", "noname");
		}
		if (buddies[0].equals("noname"))
			buddies = defaultBuddyList;
		
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, buddies);
		//adapterOffline.add(newBuddyName);
		offlineList.setAdapter(adapterOffline);
	}

	public void fillListView() {

	}

}
