package tu.kom.uhg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BuddiesActivity extends GenericActivity{
	
	ListView offlineList;
	ListView onlineList;
    String[] defaultBuddyList = {
    		"Gordon Freeman",
    		"Agent Smith",
    		"Jackie Chan"
    };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_buddies);
	    Toast.makeText(this, "buddies activity loaded", Toast.LENGTH_LONG).show();
	
	    SharedPreferences prefs = getSharedPreferences("buddies", MODE_PRIVATE);
	    int buddiesNum = prefs.getInt("buddies_num", 3);
	    buddiesNum = buddiesNum-1;
	    String[] buddies = new String[buddiesNum];
	    for (int i = 1; i < buddiesNum; i++){
	    	buddies[i-1] = prefs.getString("buddy_"+i+"name", "noname");
	    }
	    if (buddies[0].equals("noname"))
	    	buddies = defaultBuddyList;
	    
	    
	    //add 
	    ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
	    		android.R.layout.simple_list_item_1,
	    		buddies);
	    
	    offlineList = (ListView)findViewById(R.id.listOfflineFriends);
	    offlineList.setAdapter(adapterOffline);
	  
	    
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addBuddy(View v){
		
	}

}
