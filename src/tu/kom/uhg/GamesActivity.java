package tu.kom.uhg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GamesActivity extends GenericActivity{
	
	ListView gamesList;
	String[] defaultGameList = { "Gate Run", "Ping Pong",
	"Frisbee" };	
	
public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_games);
	    
	    // add
		ArrayAdapter<String> adapterOffline = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, defaultGameList);

		gamesList = (ListView)findViewById(R.id.listGames);
		gamesList.setAdapter(adapterOffline);
		
		gamesList.setClickable(true);
		gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 @Override
			  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			    Object o = gamesList.getItemAtPosition(position);
			    Toast.makeText(GamesActivity.this, "This game is not yet available", Toast.LENGTH_LONG)
				.show();
			    //quiz start
			    Intent intent = new Intent(GamesActivity.this, QuizActivity.class);
		        startActivity(intent);
		        //
			  }
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
