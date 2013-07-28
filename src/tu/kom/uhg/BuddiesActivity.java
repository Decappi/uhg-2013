package tu.kom.uhg;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class BuddiesActivity extends GenericActivity{
	
	ListView myList;
    String[] listContent = {
    		"Gordon Freeman",
    		"Agent Smith",
    		"Jackie Chan"
    };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_buddies);
	    Toast.makeText(this, "buddies activity loaded", Toast.LENGTH_LONG).show();
	
	    
	
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addBuddy(View v){
		
	}

}
