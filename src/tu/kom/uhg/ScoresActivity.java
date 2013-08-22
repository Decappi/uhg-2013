package tu.kom.uhg;

import java.util.ArrayList;

import com.google.gson.Gson;
 
import android.widget.ArrayAdapter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class ScoresActivity extends GenericActivity {

	ListView highScoresList;
	ListView gameNamesList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scores);

		//read the prefs
		SharedPreferences prefs = getSharedPreferences("scores", MODE_PRIVATE);
		String scoresStr = prefs.getString("scores", "");
		
		Gson gson = new Gson();
	    Score scores = gson.fromJson(scoresStr, Score.class);
		
		if (scores == null){//no data was found, fill the array with default data
			scores = new Score();
			scores.addGame("Gate Run", 0.3, 0.1, 0.6)
			.addGame("Ping Pong", .2, .5, .3)
			.addGame("Frisbee", .2, .4, .4)
			.addGame("Quiz", 1, 0, 0)
			.append("Ping Pong", 	"28.07.13", 500)
			.append("Ping Pong", 	"29.07.13", 100)
			.append("Ping Pong", 	"30.07.13", 150)
			.append("Gate Run", 	"28.07.13", 200)
			.append("Gate Run", 	"29.07.13", 250)
			.append("Gate Run", 	"30.07.13", 150)
			.append("Frisbee", 		"28.07.13", 100)
			.append("Frisbee", 		"29.07.13", 100)
			.append("Frisbee", 		"30.07.13", 100);
			
			Editor prefsEditor = prefs.edit();
			gson = new Gson();
			String json = gson.toJson("scores");
			prefsEditor.putString("scores", json);
			prefsEditor.commit();
		}
		
		String[] scoreNamesList = new String[scores.gameNames.size()];
		String[] scoreValuesList = new String[scores.gameNames.size()];
		for(int i = 0; i < scores.gameNames.size(); i++) {
			scoreNamesList[i] = scores.gameNames.get(i);
			scoreValuesList[i] = scores.games.get(i).getTotalScore().toString();
		}
		
		ArrayAdapter<String> adapterGameNames = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, scoreNamesList);
		ArrayAdapter<String> adapterHighScores = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, scoreValuesList);
		

		gameNamesList = (ListView)findViewById(R.id.list_gameNames);
		gameNamesList.setAdapter(adapterGameNames);
		
		highScoresList = (ListView)findViewById(R.id.list_highScores);
		highScoresList.setAdapter(adapterHighScores);
	
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public class Score {
		public ArrayList<Game> games = new ArrayList<Game>();
		public ArrayList<String> gameNames = new ArrayList<String>();
		
		public Score addGame (String newGameName, double headRatio, double armsRatio, double legsRatio){
			Game game = new Game(headRatio, armsRatio, legsRatio);
			gameNames.add(newGameName);
			games.add(game);
			return this;
		}
		
		public Score append (String gameName, String date, Integer score){
			Integer gameNumber = gameNames.indexOf(gameName);
			/*
			Game game = games.get(gameNumber);
			game.addScore(date, score);
			games.add(gameNumber, game);
			*/
			games.get(gameNumber).addScore(date, score);

			return this;
		}
		
		public class Game{
			public ArrayList<String> dates = new ArrayList<String>();
			public ArrayList<Integer> scores = new ArrayList<Integer>();
			public ArrayList<Double> ratio = new ArrayList<Double>(3);//head, arms, legs
			
			public Game(double headRatio, double armsRatio, double legsRatio){
				ratio.add(headRatio);
				ratio.add(armsRatio);
				ratio.add(legsRatio);
			}
			
			public void addScore (String newDate, Integer newScore){
				dates.add(newDate);
				scores.add(newScore);
			}
			public Integer getTotalScore () {
				Integer result = 0;
				if(scores.size() > 0) {
					for(int i = 0; i < scores.size(); i++) {
						result += scores.get(i);
					}
				}				 
				return result;
			}
			
			public Double getHeadScore () {
				return getTotalScore() * ratio.get(0);
			}
			public Double getArmsScore () {
				return getTotalScore() * ratio.get(1);
			}
			public Double getLegsScore () {
				return getTotalScore() * ratio.get(2);
			}
		}
	}

}
