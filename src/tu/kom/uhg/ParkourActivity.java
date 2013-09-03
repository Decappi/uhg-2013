package tu.kom.uhg;

import android.os.Bundle;

import java.util.HashMap;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;

public class ParkourActivity extends GenericActivity {
	
	@SuppressWarnings("serial")
	HashMap<String, String[]> markers2types = new HashMap<String, String[]>(){{
		put("1", new String[] {
				"video", 
				"Steps", 
				"Spring abwechselnd mit deinen Füßen auf die Stange. Beginne mit 15 Wiederholungen pro Fuß.",
		});
		put("2", new String[] {
				"image", 
				"Wanddrücken", 
				"Drücke deinen Oberkörper gegen die Wand, sodass deine Füße im 90° Winkel zum Boden stehen. Halte diese position zu Beginn für 30 Sekunden",
		});
		put("3", new String[] {
				"video", 
				"Liegestützen", 
				"Lege deine Hände auf die Kübel und beginne mit Liegestützen. Beginne mit 10 Wiederholungen.",
		});
		put("4", new String[] {
				"video", 
				"Mountain Climbers", 
				"Lege deine Hände auf die Sitzlehne der Bank und die Füße auf den Boden. Imitiere nun einen Bergsteiger. Beginne mit 30 Wiederholungen.",
		});
		put("5", new String[] {
				"video", 
				"Cross Trainer", 
				"Benutze den Crosstrainer und nutze ihn zu beginn für 30 Sekunden.",
		});
		put("6", new String[] {
				"video", 
				"Nackenkreisen", 
				"Lege deine Hände auf die Drehscheibe und drehe zu Beginn 50 Runden.",
		});
		put("7", new String[] {
				"image", 
				"Beinheben", 
				"Setz dich auf die Tischtennisplatten und halte deinen Oberkörper gerade. Hebe nun deine Beine zu einem 90° Winkel an und halte die Position zu Beginn 30 Sekunden.",
		});
		put("8", new String[] {
				"image", 
				"Beinheben 2", 
				"Lege deine Hände auf die Lehne der Bank und strecke jeweils ein Bein zum 90° Winkel an und halte die Position. Zu Beginn 15 Sekunden pro Bein.",
		});
		put("9", new String[] {
				"video", 
				"Schwimmen", 
				"Lege dich mt den oberkörper auf die Bank. Rudere mit den Beinen zu Beginn 50 Mal.",
		});
		put("10", new String[] {
				"video", 
				"Wandschieben", 
				"Lege deine Hände auf die Mauer und verlager dein Gewicht nach vorn. Drücke dich nun 30 Mal von der Wand weg.",
		});
		put("11", new String[] {
				"video", 
				"Rückseitiger Stütz", 
				"Lege deine Händer mit dem Rücken zur Bank auf die Sitzufläche. Hebe und Senke nun deinen Oberkörper 15 Mal.",
		});
	}};
	
	String viewSuffix;
	String markerTitle;
	String stationNumber;
	String mediaType;
	String headerText;
	String hintText;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		markerTitle = extras.getString("markerTitle");
		stationNumber = markerTitle.substring(8);
		viewSuffix = markers2types.get(stationNumber)[0];
		headerText = markers2types.get(stationNumber)[1];
		hintText = markers2types.get(stationNumber)[2];
		mediaType = (viewSuffix.equals("video")) ? "mp4" : "png";
		
		int viewId = getResources().getIdentifier("layout/activity_parkour_"+viewSuffix, "xml", getPackageName());
		int mediaId = getResources().getIdentifier("raw/parkour_a_station"+stationNumber+viewSuffix, mediaType, getPackageName());
		setContentView(viewId);
		
		TextView hintTextView = (TextView)findViewById(R.id.hint);
		TextView headerTextView = (TextView)findViewById(R.id.header);
		
		hintTextView.setText(hintText);
		headerTextView.setText(headerText);
		
		if (viewSuffix.equals("image")){
			ImageView image = (ImageView)findViewById(R.id.picture1);
			image.setImageResource(mediaId);
		} else if (viewSuffix.equals("video")){
			showHint("not implemented yet");
		}else {
			showHint("not implemented yet");
		}
	}
	
	public void finisher(View view) {
		addPoints();
		finish();
	}

	private void addPoints() {
		// TODO Auto-generated method stub
		showHint("Score successfully updated");
	}
}
