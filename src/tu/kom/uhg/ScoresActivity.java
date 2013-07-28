package tu.kom.uhg;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class ScoresActivity extends GenericActivity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_scores);
	    Toast.makeText(this, "scores activity loaded", Toast.LENGTH_LONG).show();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
