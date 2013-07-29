package tu.kom.uhg;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ScoresActivity extends GenericActivity {

	ListView gamesList;
	String[] defaultGamesList = { "Gate Run", "Ping Pong", "Frisbee" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);
		Toast.makeText(this, "scores activity loaded", Toast.LENGTH_LONG).show();

		//read the prefs
		SharedPreferences prefs = getSharedPreferences("scores", MODE_PRIVATE);
		String scoresStr = prefs.getString("scores", null);
		if (scoresStr == null){//no data was found, fill the array with default data
			Score scores = new Score();
			scores.addGame("Gate Run")
			.addGame("Ping Pong")
			.addGame("Frisbee")
			.append("Ping Pong", 	"28.07.13", 500)
			.append("Ping Pong", 	"29.07.13", 100)
			.append("Ping Pong", 	"30.07.13", 150)
			.append("Gate Run", 	"28.07.13", 200)
			.append("Gate Run", 	"29.07.13", 250)
			.append("Gate Run", 	"30.07.13", 150)
			.append("Frisbee", 		"28.07.13", 100)
			.append("Frisbee", 		"29.07.13", 100)
			.append("Frisbee", 		"30.07.13", 100);
		}
		
		
		
		Toast.makeText(this, "Scores loaded", Toast.LENGTH_LONG)
		.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class Score {
		public ArrayList<Game> games = new ArrayList<Game>();
		public ArrayList<String> gameNames = new ArrayList<String>();
		
		public Score addGame (String newGameName){
			Game game = new Game();
			gameNames.add(newGameName);
			games.add(game);
			return this;
		}
		
		public Score append (String gameName, String date, Integer score){
			Integer gameNumber = gameNames.indexOf(gameName);
			Game game = games.get(gameNumber);
			game.addScore(date, score);
			games.add(gameNumber, game);
			return this;
		}
		
		public class Game{
			public ArrayList<String> dates = new ArrayList<String>();
			public ArrayList<Integer> scores = new ArrayList<Integer>();
			
			public void addScore (String newDate, Integer newScore){
				dates.add(newDate);
				scores.add(newScore);
			}
		}
	}
}
