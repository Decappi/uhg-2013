<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 027a616b97fd424e923147c305e8532764f50934
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
		//buddiesNum = buddiesNum;
		String[] buddies = new String[buddiesNum];
		for (int i = 1; i <= buddiesNum; i++) {
			buddies[i - 1] = prefs.getString("buddyname_" + i, "noname");
		}
		if (buddies[0].equals("noname")){
			buddies = defaultBuddyList;
			for(int i = 0; i < defaultBuddyList.length; i++){
				prefs.edit()
				.putString("buddyname_" + (i+1), defaultBuddyList[i])
				.putInt("buddies_num", (i+1))
				.commit();
			}
			prefs = getSharedPreferences("buddies", MODE_PRIVATE);
			for (int i = 1; i <= buddiesNum; i++) {
				buddies[i - 1] = prefs.getString("buddyname_" + i, "noname");
			}
		}
			
		// add
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, buddies);

		offlineList = (ListView)findViewById(R.id.listOfflineBuddies);
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
			.putString("buddyname_"+ (buddiesNum+1), newBuddyName)
			.putInt("buddies_num", (buddiesNum+1))
			.commit();
		prefs = getSharedPreferences("buddies", MODE_PRIVATE);
		//update the listview
		offlineList = (ListView) findViewById(R.id.listOfflineBuddies);
		String[] buddies = new String[buddiesNum + 1];
		for (int i = 1; i < buddiesNum+1; i++) {
			buddies[i - 1] = prefs.getString("buddyname_" + i, "noname");
		}
		
		if (buddies[0].equals("noname"))
			for(int i = 0; i < defaultBuddyList.length; i++){
				buddies[i] = defaultBuddyList[i];
			}
		//add to buddies newBuddyName
		buddies[buddiesNum] = newBuddyName;
		
		
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, buddies);
		//adapterOffline.add(newBuddyName);
		offlineList.setAdapter(adapterOffline);
	}

	public void fillListView() {

	}

}
<<<<<<< HEAD
=======
=======
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
		//buddiesNum = buddiesNum;
		String[] buddies = new String[buddiesNum];
		for (int i = 1; i <= buddiesNum; i++) {
			buddies[i - 1] = prefs.getString("buddyname_" + i, "noname");
		}
		if (buddies[0].equals("noname")){
			buddies = defaultBuddyList;
			for(int i = 0; i < defaultBuddyList.length; i++){
				prefs.edit()
				.putString("buddyname_" + (i+1), defaultBuddyList[i])
				.putInt("buddies_num", (i+1))
				.commit();
			}
			prefs = getSharedPreferences("buddies", MODE_PRIVATE);
			for (int i = 1; i <= buddiesNum; i++) {
				buddies[i - 1] = prefs.getString("buddyname_" + i, "noname");
			}
		}
			
		// add
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, buddies);

		offlineList = (ListView)findViewById(R.id.listOfflineBuddies);
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
			.putString("buddyname_"+ (buddiesNum+1), newBuddyName)
			.putInt("buddies_num", (buddiesNum+1))
			.commit();
		prefs = getSharedPreferences("buddies", MODE_PRIVATE);
		//update the listview
		offlineList = (ListView) findViewById(R.id.listOfflineBuddies);
		String[] buddies = new String[buddiesNum + 1];
		for (int i = 1; i < buddiesNum+1; i++) {
			buddies[i - 1] = prefs.getString("buddyname_" + i, "noname");
		}
		
		if (buddies[0].equals("noname"))
			for(int i = 0; i < defaultBuddyList.length; i++){
				buddies[i] = defaultBuddyList[i];
			}
		//add to buddies newBuddyName
		buddies[buddiesNum] = newBuddyName;
		
		
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, buddies);
		//adapterOffline.add(newBuddyName);
		offlineList.setAdapter(adapterOffline);
	}

	public void fillListView() {

	}

}
>>>>>>> b3f8d714200c0c46b94d6d8ee740715123b762c1
>>>>>>> 027a616b97fd424e923147c305e8532764f50934
